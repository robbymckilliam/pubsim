/*
 * SamplingLatticeCarrierEstimator.java
 *
 * Created on 5 December 2007, 15:54
 */

package pubsim.psk;

import pubsim.lattices.Vn2Star.Vn2StarSampled;

/**
 *
 * @author Robby McKilliam
 */
public class SamplingLatticeCarrierEstimator extends GlueAnstarCarrierEstimator 
        implements CarrierEstimator{
    
    public SamplingLatticeCarrierEstimator(int n, int samples) { 
        super(n);
        lattice = new Vn2StarSampled(n-2, samples);
    }
    
    
    
}
