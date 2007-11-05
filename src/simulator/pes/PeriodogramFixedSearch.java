/*
 * PeriodogramFixedSearch.java
 *
 * Created on 6 June 2007, 21:58
 */

package simulator.pes;

import simulator.*;

/**
 * Periodogram estimator that allows selection of the search
 * frequencies.
 * @author Robby McKilliam
 */
public class PeriodogramFixedSearch extends PeriodogramEstimator implements PRIEstimator{
    
    protected double[] fsearch;
    
    /** Creates a new instance of PeriodogramFixedSearch */
    public PeriodogramFixedSearch() {
        fsearch = new double[] { 4.0/3.0, 1.0};
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length)
	    setSize(y.length);

	// Coarse search

	double maxp = 0;
	double fhat = fmin;
	for (int i = 0; i < fsearch.length; i++) {
            double f = fsearch[i];
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
    
}
