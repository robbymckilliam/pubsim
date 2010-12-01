/*
 * DummyCarrierEstimatorTest.java
 * JUnit based test
 *
 * Created on 13 December 2007, 14:04
 */

package pubsim.psk;

import pubsim.psk.DummyCarrierEstimator;
import junit.framework.*;

/**
 *
 * @author Robby
 */
public class DummyCarrierEstimatorTest extends TestCase {
    
    public DummyCarrierEstimatorTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateCarrier method, of class simulator.qpsk.DummyCarrierEstimator.
     */
    public void testEstimateCarrier() {
        System.out.println("estimateCarrier");
        
        double[] y = null;
        DummyCarrierEstimator instance = new DummyCarrierEstimator();
        instance.setPhase(0);
        instance.setFreqency(0.1);
        instance.setSize(6);
        
//        instance.estimateCarrier(y);   
//        assertEquals(Math.abs((instance.getPhase() - -0.4))<0.000001, true);
//        
//        instance.estimateCarrier(y);
//        assertEquals(Math.abs((instance.getPhase() - 0.2))<0.000001, true);
//        
//        instance.estimateCarrier(y);
//        assertEquals(Math.abs((instance.getPhase() - -0.2))<0.000001, true);
    }
    
}
