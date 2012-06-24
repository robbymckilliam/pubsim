/*
 * SamplingLatticeCarrierEstimator.java
 *
 * Created on 5 December 2007, 15:54
 */

package pubsim.psk;

import pubsim.lattices.Vn1Star.Vn1StarSampled;

/**
 *
 * @author Robby McKilliam
 */
public class SamplingLatticeCarrierEstimator extends GlueAnstarCarrierEstimator 
        implements CarrierEstimator{
    
    public SamplingLatticeCarrierEstimator(int n, int samples) { 
        super(n);
        lattice = new Vn1StarSampled(n-2, samples);
    }
    
    
    
}
