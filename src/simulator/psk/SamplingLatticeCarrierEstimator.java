/*
 * SamplingLatticeCarrierEstimator.java
 *
 * Created on 5 December 2007, 15:54
 */

package simulator.psk;

import lattices.Vn2Star.Vn2StarSampled;

/**
 *
 * @author Robby McKilliam
 */
public class SamplingLatticeCarrierEstimator extends GlueAnstarCarrierEstimator 
        implements CarrierEstimator{
    
    public SamplingLatticeCarrierEstimator() { 
        lattice = new Vn2StarSampled();
    }
    
    /**Constructor that sets the number of samples used */
    public SamplingLatticeCarrierEstimator(int samples) { 
        lattice = new Vn2StarSampled(samples);
    }
    
    
}
