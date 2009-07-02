/*
 * SamplingLatticeCarrierEstimator.java
 *
 * Created on 5 December 2007, 15:54
 */

package simulator.psk;

import lattices.Phin2Star.Phin2StarSampled;

/**
 *
 * @author Robby McKilliam
 */
public class SamplingLatticeCarrierEstimator extends GlueAnstarCarrierEstimator 
        implements CarrierEstimator{
    
    public SamplingLatticeCarrierEstimator() { 
        lattice = new Phin2StarSampled();
    }
    
    /**Constructor that sets the number of samples used */
    public SamplingLatticeCarrierEstimator(int samples) { 
        lattice = new Phin2StarSampled(samples);
    }
    
    
}
