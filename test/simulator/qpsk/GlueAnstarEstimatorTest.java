/*
 * GlueAnstarEstimatorTest.java
 * JUnit based test
 *
 * Created on 5 December 2007, 14:32
 */

package simulator.qpsk;

import junit.framework.*;
import simulator.GaussianNoise;

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
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        int n = 15;
        int M = 4;
        double transF = 1.0;
        double symbF = 4;
        double sampF = 20;
        double phase = 0.3;
        
        QPSKSignal sig = new QPSKSignal();
        sig.setSampleRate(sampF);
        sig.setSymbolRate(symbF);
        sig.setCarrierPhase(phase);
        sig.setCarrierFrequency(transF);
        sig.setLength(n);
        sig.setM(M);
        
        GaussianNoise noise = new GaussianNoise(0, 0.001);
        sig.setNoiseGenerator(noise);
        
        sig.generateTransmittedQPSKSignal();
        
        sig.generateReceivedSignal();
        
        double[] rr = sig.getReal();
        double[] ri = sig.getImag();
        
        GlueAnstarCarrierEstimator instance = new GlueAnstarCarrierEstimator();
        instance.setM(M);
        instance.setSize(n);
        
        double result = sampF*instance.estimateFreq(rr, ri);
        assertEquals(true, Math.abs(transF-result)<0.01);
        
    }
    
}
