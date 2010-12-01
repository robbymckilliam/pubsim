/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes;

import pubsim.fes.NoisyComplexSinusoid;
import pubsim.fes.SamplingLatticeEstimator;
import junit.framework.TestCase;

/**
 *
 * @author Robby McKilliam
 */
public class SamplingLatticeEstimatorTest extends TestCase {
    
    public SamplingLatticeEstimatorTest(String testName) {
        super(testName);
    }            
    /**
     * Test of estimateFreq method, of class SamplingLatticeEstimator.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        int iters = 100;
        double f = 0.2;
        int N = 5;
        SamplingLatticeEstimator instance = new SamplingLatticeEstimator(N,100);
        
        NoisyComplexSinusoid signal = new NoisyComplexSinusoid(N);
        signal.setFrequency(f);
        pubsim.distributions.GaussianNoise noise = new pubsim.distributions.GaussianNoise(0.0,0.0001);
        signal.setNoiseGenerator(noise);
        
        signal.generateReceivedSignal();
        double result = instance.estimateFreq(signal.getReal(), signal.getImag());
        System.out.println("f = " + result);
        assertEquals(true, Math.abs(result - f)<0.001);
    }

}
