package pubsim.snpe;

import pubsim.lattices.Anstar.AnstarVaughan;

// Implementation of Fogel & Gavish's periodogram estimator.

// Written by Vaughan Clarkson, 05-Jan-07.
// New method calculatePeriodogram and fixed the Newton iteration
// steps, 08-Jan-07.

public class PeriodogramEstimator implements PRIEstimator {

    protected int NUM_SAMPLES = 100;
    protected static final int MAX_ITER = 10;
    protected static final double EPSILON = 1e-10;

    /** Period and phase estimates */
    protected double That, phat;

    protected PhaseEstimator phasestor;

    int N;
    double[] kappa;

    protected PeriodogramEstimator() {}

    public PeriodogramEstimator(int N){
        setSize(N);
    }
    
    public PeriodogramEstimator(int N, int samples){
        setSize(N);
        NUM_SAMPLES = samples;
    }
    
    private void setSize(int N) {
	this.N = N;
        phasestor = new PhaseEstimator(N);
	kappa = new double[N];
    }

    private static double calculatePeriodogram(Double[] y, double f) {
	double sumur = 0, sumui = 0;
	for (int i = 0; i < y.length; i++) {
	    sumur += Math.cos(2 * Math.PI * f * y[i]);
	    sumui += Math.sin(2 * Math.PI * f * y[i]);
	}
	return sumur * sumur + sumui * sumui;
    }

    @Override
    public void estimate(Double[] y, double Tmin, double Tmax) {
        double maxp = 0;
        double fmin = 1/Tmax; double fmax = 1/Tmin;
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
	    double p, pd, pdd;
	    double sumur = 0, sumui = 0, sumvr = 0, sumvi = 0,
	    sumwr = 0, sumwi = 0;
	    for (int i = 0; i < N; i++) {
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
	That = 1.0 / fhat;
        //now compute the phase estimate
        phat = phasestor.getPhase(y, That);
    }

    @Override
    public double getPeriod() {
        return That;
    }

    @Override
    public double getPhase() {
        return phat;
    }
}
