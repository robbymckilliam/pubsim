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
        double f = 0.14;
        double p = -0.2;
        int n = 20;
        ZnLLS instance = new ZnLLS();
        
        for (int i = 0; i < iters/2; i++) {
            NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
            signal.setSize(n);
            signal.setFrequency(f);
            signal.setPhase(p * 2*Math.PI);
            simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.001);
            signal.setNoiseGenerator(noise);

            signal.generateReceivedSignal();
            
            // Check real and imag inputs
            double result = instance.estimateFreq(signal.getReal(), signal.getImag());
            //System.out.println("freq = " + result);
            assertEquals(true, Math.abs(result - f)<0.001);
            
            // Check phase inputs
            double[] y = new double[n];
            for(int j = 0; j < signal.getReal().length; j++) {
                y[j] = Math.atan2(signal.getImag()[j],signal.getReal()[j])/(2*Math.PI);
            }
            result = instance.estimateFreq(signal.getReal(), signal.getImag());
            //System.out.println("freq = " + result);
            assertEquals(true, Math.abs(result - f)<0.001);
        }
        
        n = 21;
        
        for (int i = iters/2; i < iters; i++) {
            NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
            signal.setSize(n);
            signal.setFrequency(f);
            signal.setPhase(p * 2*Math.PI);
            simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.001);
            signal.setNoiseGenerator(noise);

            signal.generateReceivedSignal();
            
            // Check real and imag inputs
            double result = instance.estimateFreq(signal.getReal(), signal.getImag());
            //System.out.println("freq = " + result);
            assertEquals(true, Math.abs(result - f)<0.001);
            
            // Check phase inputs
            double[] y = new double[n];
            for(int j = 0; j < signal.getReal().length; j++) {
                y[j] = Math.atan2(signal.getImag()[j],signal.getReal()[j])/(2*Math.PI);
            }
            result = instance.estimateFreq(signal.getReal(), signal.getImag());
            //System.out.println("freq = " + result);
            assertEquals(true, Math.abs(result - f)<0.001);
        }
    }
    
 
    /**
     * Test of setMinFreq and setMaxFreq methods, of class
     * simulator.fes.GlueAnstarEstimator.
     */
    public void testMinFreqMaxFreq() {
        System.out.println("setMinFreq, setMaxFreq");
        
        double p = -0.2;
        int n = 20;
        ZnLLS instance = new ZnLLS();
        
        for (int i = -49; i < 50; i++) {
            NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
            signal.setSize(n);
            signal.setFrequency(i/(double)100);
            signal.setPhase(p * 2*Math.PI);
            simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.001);
            signal.setNoiseGenerator(noise);

            signal.generateReceivedSignal();
            
            // Check real and imag inputs
            instance.setMinFreq((i-1)/(double)100);
            instance.setMaxFreq((i+1)/(double)100);
            double result = instance.estimateFreq(signal.getReal(), signal.getImag());
            //System.out.println("freq = " + result);
            assertEquals(true, Math.abs(result - (i/(double)100))<0.001);
        }
        
        n = 21;
        
        for (int i = -49; i < 50; i++) {
            NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
            signal.setSize(n);
            signal.setFrequency(i/(double)100);
            signal.setPhase(p * 2*Math.PI);
            simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.001);
            signal.setNoiseGenerator(noise);

            signal.generateReceivedSignal();
            
            // Check real and imag inputs
            instance.setMinFreq((i-1)/(double)100);
            instance.setMaxFreq((i+1)/(double)100);
            double result = instance.estimateFreq(signal.getReal(), signal.getImag());
            //System.out.println("freq = " + result);
            assertEquals(true, Math.abs(result - (i/(double)100))<0.001);
        }
    }

    
    /**
     * Test of estimatePhase method, of class simulator.fes.GlueAnstarEstimator.
     */
    public void testEstimatePhase() {
        System.out.println("estimatePhase");
        
        int iters = 100;
        double f = 0.14;
        double p = -0.2;
        int n = 20;
        ZnLLS instance = new ZnLLS();
        
        for (int i = 0; i < iters/2; i++) {
            NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
            signal.setSize(n);
            signal.setFrequency(f);
            signal.setPhase(p * 2*Math.PI);
            simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.001);
            signal.setNoiseGenerator(noise);

            signal.generateReceivedSignal();
            double result = instance.estimatePhase(signal.getReal(), signal.getImag());
            //System.out.println("p = " + result);
            assertEquals(true, Math.abs(result - p) < 0.1);
        }
        
        n = 21;
        
        for (int i = iters/2; i < iters; i++) {
            NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
            signal.setSize(n);
            signal.setFrequency(f);
            signal.setPhase(p * 2*Math.PI);
            simulator.GaussianNoise noise = new simulator.GaussianNoise(0.0,0.001);
            signal.setNoiseGenerator(noise);

            signal.generateReceivedSignal();
            double result = instance.estimatePhase(signal.getReal(), signal.getImag());
            //System.out.println("p = " + result);
            assertEquals(true, Math.abs(result - p) < 0.1);
        }
    }
    
}
