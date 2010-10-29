package robbysim.pes;

import robbysim.lattices.Anstar.Anstar;
import robbysim.lattices.Anstar.AnstarVaughan;
import robbysim.lattices.Anstar.AnstarBucket;
import robbysim.lattices.Anstar.AnstarBucketVaughan;
import robbysim.lattices.LatticeAndNearestPointAlgorithm;

/**
 * A modification of the Bresenham estimator which samples the line
 * segment rather than tracing through all the Voronoi cell boundaries.
 * @author Vaughan Clarkson, 16-Jun-05.
 * Add calculateObjective method, 13-Jan-07.
 */
public class SamplingLLS implements PRIEstimator {

    protected final int NUM_SAMPLES;
    protected int N;

    protected PhaseEstimator phasestor;

    /** Period and phase estimates */
    protected double That, phat;

    protected final LatticeAndNearestPointAlgorithm lattice 
            = new AnstarBucketVaughan();

    protected SamplingLLS() { NUM_SAMPLES = 100; }
    
    public SamplingLLS(int N){
        setSize(N);
        NUM_SAMPLES = 100;
    }
    
    public SamplingLLS(int N, int samples){
        setSize(N);
        NUM_SAMPLES = samples;
    }

    double[] zeta, fzeta;

    private void setSize(int N) {
        phasestor = new PhaseEstimator(N);
	lattice.setDimension(N-1); // => N = N-1
	zeta = new double[N];
	fzeta = new double[N];
        this.N = N;
    }

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
	    double sumv2 = 0, sumvz = 0;
	    for (int i = 0; i < N; i++) {
		sumv2 += v[i] * v[i];
		sumvz += v[i] * zeta[i];
	    }
	    double f0 = sumv2 / sumvz;
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

    public double getPeriod() {
        return That;
    }

    public double getPhase() {
        return phat;
    }

}
