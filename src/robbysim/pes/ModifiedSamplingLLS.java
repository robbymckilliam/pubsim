/*
 * ModifiedSamplingLLS.java
 *
 * Created on 16 July 2007, 21:57
 */

package robbysim.pes;

import robbysim.lattices.Anstar.Anstar;
import robbysim.lattices.Anstar.AnstarVaughan;

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

    /**
     * Modify the estimate method so that it returns the modifed estimate
     * rather than the least squares estimate.
     */
    @Override
    public void estimate(double[] y, double fmin, double fmax){
        int N = y.length;
        if( d == null || d.length != N ){
            lattice.setDimension(N-1);
            d = new double[N];
            fy = new double[N];
        }

        System.arraycopy(y, 0, d, 0, N);
        double f = estimateFreq(d, fmin, fmax);
        period = 1.0/f;

        for (int i = 0; i < n; i++) fy[i] = f * y[i];
        Anstar.project(fy, d);

        lattice.nearestPoint(d);
        double[] s = lattice.getIndex();

        double sum = 0;
        for(int n = 0; n < N; n++){
            sum += f*y[n] - s[n];
        }

        double unwp = (sum/N)/period;

        phase = robbysim.Util.fracpart(unwp)*period;

    }
    
    @Override
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	AnstarVaughan.project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    for (int i = 0; i < n; i++) fzeta[i] = f * zeta[i];
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
