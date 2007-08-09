package simulator.pes;

import simulator.*;

// A modifed periodogram estimator, designed to behave more like the MLE.

// Written by Vaughan Clarkson, 11-Jan-07 (fork from PeriodogramEstimator).

public class ModifiedPeriodogram implements PRIEstimator {

    static final int NUM_SAMPLES = 100;
    static final int MAX_ITER = 10;
    static final double EPSILON = 1e-10;

    int n;
    double[] kappa;

    public void setSize(int n) {
	this.n = n;
	kappa = new double[n];
    }

    static double calculateObjective(double[] y, double f) {
	double sumur = 0, sumui = 0;
	int N = y.length;
	for (int i = 0; i < y.length; i++) {
	    sumur += Math.cos(2 * Math.PI * f * y[i]);
	    sumui += Math.sin(2 * Math.PI * f * y[i]);
	}
	return (N * N - sumur * sumur - sumui * sumui) / (f * f);
    }

    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length)
	    setSize(y.length);

	// Coarse search

	double minp = Double.POSITIVE_INFINITY;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    double p = calculateObjective(y, f);
	    if (p < minp) {
		minp = p;
		fhat = f;
	    }
	}

	// Modified Newton step
	
	int numIter = 0;
	double f = fhat, lastf = f - 2 * EPSILON, 
	    lastp = Double.POSITIVE_INFINITY;
	while (Math.abs(f - lastf) > EPSILON && numIter++ < MAX_ITER
	       && f >= fmin && f <= fmax) {
	    double p = 0, pd = 0, pdd = 0, pd2 = 0, pdd2 = 0;
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
	    p = (n * n - sumur * sumur - sumui * sumui) / (f * f);
	    // System.err.println("f = " + f + " p = " + p);
	    if (p > lastp)
		f = (f + lastf) / 2;
	    else {
		lastf = f;
		lastp = p;
		if (p < minp) {
		    minp = p;
		    fhat = f;
		}
		pd = 2 * (sumvr * sumur + sumvi * sumui) / (f * f);
		pdd = 2 * (sumvr * sumvr + sumwr * sumur
			   + sumvi * sumvi + sumwi * sumui) / (f * f);
		pd2 = -pd - (2 * p / f);
		pdd2 = -pdd + (4 * pd / f) + (6 * p / (f*f));
		f -= pd2 / Math.abs(pdd2);
	    }
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
