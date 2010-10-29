/*
 * SamplingLatticeEstimator.java
 *
 * Created on 18 August 2007, 12:47
 */

package robbysim.fes;
import robbysim.lattices.Vn2Star.Vn2StarRelVecApprox;
import robbysim.lattices.Vn2Star.Vn2StarSampled;

/**
 * Simple and fast suboptimal (but perharps can be made optimal)
 * lattice frequency estimator based on the pes.SamplingLLS 
 * period estimator.
 * @author Robby McKilliam
 */
public class SamplingLatticeEstimator extends LatticeEstimator
        implements FrequencyEstimator {

    /** Don't use this */
    protected SamplingLatticeEstimator(){
    }
    
    public SamplingLatticeEstimator(int numsamples){
        lattice = new Vn2StarSampled(0, numsamples);
        //lattice = new Vn2StarRelVecApprox();
    }
   
}