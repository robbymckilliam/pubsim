/*
 * SamplingLatticeCarrierEstimatorTest.java
 * JUnit based test
 *
 * Created on 5 December 2007, 16:09
 */

package robbysim.psk;

import robbysim.psk.PSKSignal;
import robbysim.psk.SamplingLatticeCarrierEstimator;
import junit.framework.*;
import robbysim.distributions.GaussianNoise;

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
        
        int n = 40;
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
        
        SamplingLatticeCarrierEstimator instance = new SamplingLatticeCarrierEstimator();
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
