package robbysim.snpe;

import java.io.Serializable;
import robbysim.lattices.Anstar.AnstarBucketVaughan;
import robbysim.lattices.LatticeAndNearestPointAlgorithm;
import static robbysim.Util.fracpart;


/**
 * Interface that describes the functions of a PRI estimator.
 * @author Vaughan Clarkson, 15-Jun-05.
 */
public interface PRIEstimator extends Serializable{

    /**
     * Runs the estimator and return the estimated frequency.
     */
    public void estimate(double[] y, double Tmin, double Tmax);

    /**
     * @return the period estimate
     */
    public double getPeriod();

    /**
     *
     * @return the phase estimate
     */
    public double getPhase();


    /**
     * Given the period, you only need to solved the nearest point problem
     * in Anstar to get the phase estimate. This class does that.
     */
    public static class PhaseEstimator {

        protected final LatticeAndNearestPointAlgorithm lattice;
        protected final double[] z, fz;
        protected final int N;

        public PhaseEstimator(int N){
            this.N = N;
            lattice = new AnstarBucketVaughan(N-1);
            z = new double[N];
            fz = new double[N];
        }

        public double getPhase(double[] y, double That){
            AnstarBucketVaughan.project(y, z);
            for(int i = 0; i < N; i++) fz[i] =  z[i] / That;
            lattice.nearestPoint(fz);
            double[] s = lattice.getIndex();
            double sumyTs = 0;
            for(int i = 0; i < N; i++) sumyTs += y[i] - That*s[i];
            double phase = sumyTs/N;
            //return phase;
            return fracpart(phase/That)*That;
        }

    }


}
