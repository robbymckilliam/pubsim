package simulator.pes;

import lattices.Anstar;
import simulator.*;

/**
 * Implementation of Sidiropoulos et al.'s SLS2-ALL algorithm for PRI
 * estimation.
 * @author Vaughan Clarkson, 16-Jun-05.
 * Stupid bug fix, 17-Jun-05.
 */
public class SLS2all implements PRIEstimator {

    protected int NUM_SAMPLES = 100;

    int n = 0, m;
    double[] d, kappa;
    int[] u;
    
    public SLS2all(){
    }
    
    public SLS2all(int samples){
        NUM_SAMPLES = samples;
    }

    public void setSize(int n) {
	this.n = n;
	m = n * (n-1) / 2;
	d = new double[m];
	u = new int[m];
	kappa = new double[n];
    }

    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length)
	    setSize(y.length);
	int k = 0;
	for (int i = 0; i < n-1; i++)
	    for (int j = i+1; j < n; j++)
		d[k++] = y[j] - y[i];
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    double sumu2 = 0;
	    double sumud = 0;
	    for (int i = 0; i < m; i++) {
		u[i] = (int) Math.round(f * d[i]);
		sumu2 += u[i] * u[i];
		sumud += u[i] * d[i];
	    }
	    double f0 = sumu2 / sumud;
	    double L = 0;
	    for (int i = 0; i < m; i++) {
		double diff = d[i] - (u[i] / f0);
		L += diff * diff;
	    }
	    if (L < bestL) {
		bestL = L;
		fhat = f0;
	    }
	}
	return fhat;
    }

    // This bound is just the 'clairvoyant' CRLB.  There is no reason
    // to assume that SLS2-ALL will achieve this bound, although it is
    // reported that it does in simulations by Sidiropoulos et al.

    public double varianceBound(double sigma, double[] k) {
	Anstar.project(k, kappa);
	double sk = 0;
	for (int i = 0; i < k.length; i++)
	    sk += kappa[i] * kappa[i];
	return sigma * sigma / sk;
    }
}
