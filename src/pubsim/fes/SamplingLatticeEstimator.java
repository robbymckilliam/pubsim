/*
 * SamplingLatticeEstimator.java
 *
 * Created on 18 August 2007, 12:47
 */

package pubsim.fes;
import pubsim.lattices.Vn2Star.Vn2StarRelVecApprox;
import pubsim.lattices.Vn2Star.Vn2StarSampled;

/**
 * Simple and fast suboptimal (but perharps can be made optimal)
 * lattice frequency estimator based on the pes.SamplingLLS 
 * period estimator.
 * @author Robby McKilliam
 */
public class SamplingLatticeEstimator extends LatticeEstimator
        implements FrequencyEstimator {

    /**
     * @param N number of observations
     * @param numsamples number of samples used in the discrete search along
     * the frequency parameter.
     */
    public SamplingLatticeEstimator(int N, int numsamples){
        super(N);
        lattice = new Vn2StarSampled(N-2, numsamples);
    }
   
}
