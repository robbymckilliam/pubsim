/*
 * GlueAnstarEstimatorTest.java
 * JUnit based test
 *
 * Created on 5 December 2007, 14:32
 */

package simulator.qpsk;

import simulator.psk.GlueAnstarCarrierEstimator;
import simulator.psk.QPSKSignal;
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
    public void testEstimateCarrierFreq() {
        System.out.println("estimateFreq");
        
        int n = 15;
        int M = 4;
        double transF = 0.1;
        double fmin = 0.07;
        double fmax = 0.13;
        double symbF = 0.23;
        double sampF = 1;
        double phase = 0.3;
        
        QPSKSignal sig = new QPSKSignal();
        sig.setSymbolRate(symbF);
        sig.setCarrierPhase(phase);
        sig.setCarrierFrequency(transF);
        sig.setLength(n);
        sig.setM(M);
        
        GaussianNoise noise = new GaussianNoise(0, 1);
        sig.setNoiseGenerator(noise);
        
        sig.generateTransmittedQPSKSignal();
        
        sig.generateReceivedSignal();
        
        double[] rr = sig.getReal();
        double[] ri = sig.getImag();
        
        GlueAnstarCarrierEstimator instance = new GlueAnstarCarrierEstimator();
        instance.setM(M);
        instance.setSize(n);
        
        //UNDER CONSTRUCTION
        //instance.estimateCarrier(ri);
        //double result = sampF*instance.estimateCarrierFrequency(rr, ri, fmin, fmax);
        //assertEquals(true, Math.abs(transF-result)<0.01);
        //assertEquals(transF, result);
        
    }
    
}
