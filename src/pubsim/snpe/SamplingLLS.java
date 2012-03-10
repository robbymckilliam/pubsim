package pubsim.snpe;

import pubsim.VectorFunctions;
import pubsim.lattices.Anstar.AnstarLinear;
import pubsim.lattices.Anstar.AnstarVaughan;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;

/**
 * A modification of the Bresenham estimator which samples the line segment
 * rather than tracing through all the Voronoi cell boundaries.
 *
 * @author Vaughan Clarkson, 16-Jun-05. Add calculateObjective method,
 * 13-Jan-07.
 */
public class SamplingLLS implements PRIEstimator {

    final protected int NUM_SAMPLES;
    final protected int N;
    final protected PhaseEstimator phasestor;
        
    final double[] zeta, fzeta;
    /**
     * Period and phase estimates
     */
    protected double That, phat;
    protected final LatticeAndNearestPointAlgorithm lattice;


    public SamplingLLS(int N) {
        NUM_SAMPLES = 4*N; //default is to oversample by 4
        lattice = new AnstarLinear(N-1);
        phasestor = new PhaseEstimator(N);
        zeta = new double[N];
        fzeta = new double[N];
        this.N = N;
    }

    public SamplingLLS(int N, int samples) {
        NUM_SAMPLES = samples;
        lattice = new AnstarLinear(N-1);
        phasestor = new PhaseEstimator(N);
        zeta = new double[N];
        fzeta = new double[N];
        this.N = N;
    }


    @Override
    public void estimate(Double[] y, double Tmin, double Tmax) {

        //first compute the period estimate
        double fmin = 1 / Tmax;
        double fmax = 1 / Tmin;
        AnstarVaughan.project(y, zeta);
        double ztz = VectorFunctions.sum2(zeta);
        double bestL = Double.POSITIVE_INFINITY;
        double fhat = fmin;
        double fstep = (fmax - fmin) / NUM_SAMPLES;
        for (double f = fmin; f <= fmax; f += fstep) {
            for (int i = 0; i < N; i++) {
                fzeta[i] = f * zeta[i];
            }
            lattice.nearestPoint(fzeta);
            double[] v = lattice.getLatticePoint();
            double vtv = 0, vtz = 0;
            for (int i = 0; i < N; i++) {
                vtv += v[i] * v[i];
                vtz += v[i] * zeta[i];
            }
            double f0 = vtv / vtz;
            double L = ztz - 2 * vtz / f0 + vtv / (f0 * f0);
            if (L < bestL) {
                bestL = L;
                fhat = f0;
            }
        }
        That = 1 / fhat;

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
