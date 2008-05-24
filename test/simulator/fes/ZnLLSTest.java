/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes;

import junit.framework.*;

/**
 *
 * @author Tim
 */
public class ZnLLSTest extends TestCase {
    
    public ZnLLSTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateFreq method, of class simulator.fes.GlueAnstarEstimator.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        int iters = 100;
        double f = 0.2;
        int n = 20;
        ZnLLS instance = new ZnLLS();
        
        for (int i = 0; i < iters/2; i++) {
            NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
            signal.setSize(n);
            signal.setFrequency(f);
            signal.setPhase(0.3);
            simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.001);
            signal.setNoiseGenerator(noise);

            signal.generateReceivedSignal();
            double result = instance.estimateFreq(signal.getReal(), signal.getImag());
            //System.out.println("f = " + result);
            assertEquals(true, Math.abs(result - f)<0.001);
            System.out.println(i);
        }
        
        n = 21;
        
        for (int i = iters/2; i < iters; i++) {
            NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
            signal.setSize(n);
            signal.setFrequency(f);
            signal.setPhase(0.3);
            simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.001);
            signal.setNoiseGenerator(noise);

            signal.generateReceivedSignal();
            double result = instance.estimateFreq(signal.getReal(), signal.getImag());
            //System.out.println("f = " + result);
            assertEquals(true, Math.abs(result - f)<0.001);
            System.out.println(i);
        }
    }
    
}
