package pubsim.snpe;

import pubsim.VectorFunctions;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarVaughan;
import pubsim.lattices.Anstar.AnstarBucket;
import pubsim.lattices.Anstar.AnstarBucketVaughan;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;

/**
 * A modification of the Bresenham estimator which samples the line segment
 * rather than tracing through all the Voronoi cell boundaries.
 *
 * @author Vaughan Clarkson, 16-Jun-05. Add calculateObjective method,
 * 13-Jan-07.
 */
public class SamplingLLS implements PRIEstimator {

    protected int NUM_SAMPLES;
    protected int N;
    protected PhaseEstimator phasestor;
    /**
     * Period and phase estimates
     */
    protected double That, phat;
    protected final LatticeAndNearestPointAlgorithm lattice = new AnstarBucketVaughan();

    protected SamplingLLS() {
    }

    public SamplingLLS(int N) {
        setSize(N);
        NUM_SAMPLES = 100;
    }

    public SamplingLLS(int N, int samples) {
        setSize(N);
        NUM_SAMPLES = samples;
        //System.out.println("using " + NUM_SAMPLES +  " samples");
    }
    double[] zeta, fzeta;

    private void setSize(int N) {
        phasestor = new PhaseEstimator(N);
        lattice.setDimension(N - 1); // => N = N-1
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
