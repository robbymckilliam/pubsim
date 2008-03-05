/*
 * ModifiedSamplingLLS.java
 *
 * Created on 16 July 2007, 21:57
 */

package simulator.pes;

import lattices.AnstarVaughan;
import simulator.*;

/**
 * Version of the SamplingEstimator that uses the modified liklihood
 * function.
 * @author Robby McKilliam
 */
public class ModifiedSamplingLLS extends SamplingEstimator implements PRIEstimator {
    
    /** Creates a new instance of ModifiedSamplingLLS */
    public ModifiedSamplingLLS() {
    }
    
    /** Creates a new instance of ModifiedSamplingLLS */
    public ModifiedSamplingLLS(int samples) {
        NUM_SAMPLES = samples;
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	AnstarVaughan.project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    for (int i = 0; i < n; i++)
		fzeta[i] = f * zeta[i];
	    lattice.nearestPoint(fzeta);
            double[] v = lattice.getLatticePoint();
	    double sumv2 = 0, sumvz = 0;
	    for (int i = 0; i < n; i++) {
		//sumv2 += v[i] * v[i];
		//sumvz += v[i] * zeta[i];
                sumv2 += v[i] * zeta[i];
		sumvz += zeta[i] * zeta[i];
	    }
	    double f0 = sumv2 / sumvz;
	    double L = 0;
	    for (int i = 0; i < n; i++) {
		//double diff = zeta[i] - (v[i] / f0);
                double diff = f0*zeta[i] - v[i];
		L += diff * diff;
	    }
	    if (L < bestL) {
		bestL = L;
		fhat = f0;
	    }
	}
	return fhat;
    }
    
}
