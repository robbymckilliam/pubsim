/*
 * GlueAnstarEstimatorTest.java
 * JUnit based test
 *
 * Created on 5 December 2007, 14:32
 */

package robbysim.psk;

import pubsim.psk.GlueAnstarCarrierEstimator;
import pubsim.psk.PSKSignal;
import junit.framework.*;
import pubsim.distributions.GaussianNoise;

/**
 *
 * @author Robby
 */
public class GlueAnstarEstimatorTest extends TestCase {
    
    public GlueAnstarEstimatorTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateFreq method, of class simulator.qpsk.GlueAnstarCarrierEstimator.
     */
    public void testEstimateCarrierFreq() {
        System.out.println("estimateFreq");
        
        int n = 30;
        int M = 4;
        double transF = 0.02;
        double symbF = 0.18;
        double phase = 0.3;
        
        PSKSignal sig = new PSKSignal();
        sig.setSymbolRate(symbF);
        sig.setCarrierPhase(phase);
        sig.setCarrierFrequency(transF);
        sig.setLength(n);
        sig.setM(M);
        
        GaussianNoise noise = new GaussianNoise(0, 0.00001);
        sig.setNoiseGenerator(noise);
        
        sig.generateTransmittedQPSKSignal();
        
        sig.generateReceivedSignal();
        
        double[] rr = sig.getReal();
        double[] ri = sig.getImag();
        
        GlueAnstarCarrierEstimator instance = new GlueAnstarCarrierEstimator();
        instance.setM(M);
        instance.setSize(n);
        
        //UNDER CONSTRUCTION
        instance.estimateCarrier(rr, ri);
        double result = instance.getFreqency();
        System.out.println("res = " + result);
        assertTrue(Math.abs(transF-result)<0.01);
        //assertEquals(transF, result);
        
    }
    
}
