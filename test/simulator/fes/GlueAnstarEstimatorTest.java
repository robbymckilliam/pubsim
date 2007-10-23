/*
 * GlueAnstarEstimatorTest.java
 * JUnit based test
 *
 * Created on 16 August 2007, 15:31
 */

package simulator.fes;

import junit.framework.*;
import simulator.Pn2Glued;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
 */
public class GlueAnstarEstimatorTest extends TestCase {
    
    public GlueAnstarEstimatorTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateFreq method, of class simulator.fes.GlueAnstarEstimator.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        int iters = 100;
        double f = 0.2;
        int n = 5;
        GlueAnstarEstimator instance = new GlueAnstarEstimator();
        
        NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
        signal.setSize(n);
        signal.setFrequency(f);
        simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.0);
        signal.setNoiseGenerator(noise);
        
        signal.generateReceivedSignal();
        double result = instance.estimateFreq(signal.getReal(), signal.getImag());
        System.out.println("f = " + result);
        assertEquals(true, Math.abs(result - f)<0.001);
        
    }
    
}
