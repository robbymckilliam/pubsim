/*
 * NormalisedSamplingLLS.java
 *
 * Created on 16 July 2007, 21:57
 */

package robbysim.pes;

import robbysim.lattices.Anstar.Anstar;
import robbysim.lattices.Anstar.AnstarVaughan;

/**
 * Version of the SamplingLLS that uses the modified liklihood
 * function.
 * @author Robby McKilliam
 */
public class NormalisedSamplingLLS extends SamplingLLS implements PRIEstimator {

    public NormalisedSamplingLLS(int N){
        super(N);
    }

    public NormalisedSamplingLLS(int N, int samples){
        super(N, samples);
    }

    @Override
    public void estimate(double[] y, double Tmin, double Tmax) {

        //first compute the period estimate
        double fmin = 1/Tmax; double fmax = 1/Tmin;
	AnstarVaughan.project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    for (int i = 0; i < N; i++) fzeta[i] = f * zeta[i];
	    lattice.nearestPoint(fzeta);
            double[] v = lattice.getLatticePoint();
	    double sumvz = 0, sumzz = 0;
	    for (int i = 0; i < N; i++) {
		sumvz += v[i] * zeta[i];
		sumzz += zeta[i] * zeta[i];
	    }
	    double f0 = sumvz / sumzz;
	    double L = 0;
	    for (int i = 0; i < N; i++) {
		double diff = zeta[i] - (v[i] / f0);
                //double diff = f0*zeta[i] - v[i];
		L += diff * diff;
	    }
	    if (L < bestL) {
		bestL = L;
		fhat = f0;
	    }
	}
        That = 1/fhat;

        //now compute the phase estimate
        phat = phasestor.getPhase(y, That);

    }

    
}
