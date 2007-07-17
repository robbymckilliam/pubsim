package simulator;

// Implementation of Fogel & Gavish's periodogram estimator.

// Written by Vaughan Clarkson, 05-Jan-07.
// New method calculatePeriodogram and fixed the Newton iteration
// steps, 08-Jan-07.

public class PeriodogramEstimator implements PRIEstimator {

    protected int NUM_SAMPLES = 100;
    static final int MAX_ITER = 10;
    static final double EPSILON = 1e-10;

    int n;
    double[] kappa;

    public PeriodogramEstimator(){}
    
    public PeriodogramEstimator(int samples){
        NUM_SAMPLES = samples;
    }
    
    public void setSize(int n) {
	this.n = n;
	kappa = new double[n];
    }

    static double calculatePeriodogram(double[] y, double f) {
	double sumur = 0, sumui = 0;
	for (int i = 0; i < y.length; i++) {
	    sumur += Math.cos(2 * Math.PI * f * y[i]);
	    sumui += Math.sin(2 * Math.PI * f * y[i]);
	}
	return sumur * sumur + sumui * sumui;
    }

    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length)
	    setSize(y.length);

	// Coarse search

	double maxp = 0;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    double p = calculatePeriodogram(y, f);
	    if (p > maxp) {
		maxp = p;
		fhat = f;
	    }
	}

	// Modified Newton step
	
	int numIter = 0;
	double f = fhat, lastf = f - 2 * EPSILON, lastp = 0;
	while (Math.abs(f - lastf) > EPSILON && numIter <= MAX_ITER
	       && f >= fmin && f <= fmax) {
	    double p = 0, pd = 0, pdd = 0;
	    double sumur = 0, sumui = 0, sumvr = 0, sumvi = 0,
	    sumwr = 0, sumwi = 0;
	    for (int i = 0; i < n; i++) {
		double ur = Math.cos(2 * Math.PI * f * y[i]);
		double ui = Math.sin(2 * Math.PI * f * y[i]);
		double vr = -2 * Math.PI * y[i] * ui;
		double vi = 2 * Math.PI * y[i] * ur;
		double wr = -2 * Math.PI * y[i] * vi;
		double wi = 2 * Math.PI * y[i] * vr;
		sumur += ur;
		sumui += ui;
		sumvr += vr;
		sumvi += vi;
		sumwr += wr;
		sumwi += wi;
	    }
	    p = sumur * sumur + sumui * sumui;
	    // System.err.println("f = " + f + " p = " + p);
	    if (p < lastp)
		f = (f + lastf) / 2;
	    else {
		lastf = f;
		lastp = p;
		if (p > maxp) {
		    maxp = p;
		    fhat = f;
		}
		pd = 2 * (sumvr * sumur + sumvi * sumui);
		pdd = 2 * (sumvr * sumvr + sumwr * sumur
			   + sumvi * sumvi + sumwi * sumui);
		f += pd / Math.abs(pdd);
	    }
	    numIter++;
	}

	return fhat;
    }

    // This bound is just the 'clairvoyant' CRLB.  There is no reason
    // to believe that the periodogram will achieve this bound.

    public double varianceBound(double sigma, double[] k) {
	Anstar.project(k, kappa);
	double sk = 0;
	for (int i = 0; i < k.length; i++)
	    sk += kappa[i] * kappa[i];
	return sigma * sigma / sk;
    }
}
