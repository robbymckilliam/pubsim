/*
 * SamplingLatticeCarrierEstimatorTest.java
 * JUnit based test
 *
 * Created on 5 December 2007, 16:09
 */

package simulator.psk;

import simulator.psk.PSKSignal;
import simulator.psk.SamplingLatticeCarrierEstimator;
import junit.framework.*;
import simulator.GaussianNoise;

/**
 *
 * @author Robby
 */
public class SamplingLatticeCarrierEstimatorTest extends TestCase {
    
    public SamplingLatticeCarrierEstimatorTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateFreq method, of class simulator.qpsk.SamplingLatticeCarrierEstimator.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        int n = 11;
        int M = 4;
        double transF = 1.0;
        double symbF = 4;
        double sampF = 20;
        double phase = 0.3;
        
        PSKSignal sig = new PSKSignal();
        sig.setSymbolRate(symbF);
        sig.setCarrierPhase(phase);
        sig.setCarrierFrequency(transF);
        sig.setLength(n);
        sig.setM(M);
        
        GaussianNoise noise = new GaussianNoise(0, 0.0001);
        sig.setNoiseGenerator(noise);
        
        sig.generateTransmittedQPSKSignal();
        
        sig.generateReceivedSignal();
        
        double[] rr = sig.getReal();
        double[] ri = sig.getImag();
        
        SamplingLatticeCarrierEstimator instance = new SamplingLatticeCarrierEstimator(100);
        instance.setM(M);
        instance.setSize(n);
        
        double result = sampF*instance.estimateFreq(rr, ri);
        assertEquals(transF,result);
    }
    
}
