/*
 * ZnLLSTest.java
 * JUnit based test
 *
 * Created on 9 July 2007, 12:24
 */

package pubsim.snpe;

import pubsim.snpe.ZnLLS;
import pubsim.snpe.SparseNoisyPeriodicSignal;
import pubsim.distributions.GaussianNoise;
import junit.framework.*;
import pubsim.distributions.discrete.PoissonRandomVariable;

/**
 *
 * @author Robby McKilliam
 */
public class ZnLLSTest extends TestCase {
    
    public ZnLLSTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateFreq method, of class simulator.ZnLLS.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double Tmin = 0.7;
        double Tmax = 1.3;
        int n = 30;
        double f = 0.9;
        double T = 1/f;
        ZnLLS instance = new ZnLLS(n);
        
        double noisestd = 0.0001;
        GaussianNoise noise = new pubsim.distributions.GaussianNoise(0.0,noisestd*noisestd);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal(n);
        sig.setPeriod(T);
        sig.setNoiseGenerator(noise);
        sig.setSparseGenerator(new PoissonRandomVariable(2));
          
        long seed = 1331;
        noise.setSeed(seed);
        sig.generateSparseSignal(n);
        double[] trans = sig.generateSparseSignal(n);
        Double[] y = sig.generateReceivedSignal();
        
        double expResult = T;
        instance.estimate(y, Tmin, Tmax);
        assertEquals(expResult, instance.getPeriod(), 0.001);

    }

   /**
     * Test of estimateFreq method, of class simulator.ZnLLS.
     */
    public void testEstimateWithPhase() {
        System.out.println("Estimate With Phase");

        double fmin = 0.7;
        double fmax = 1.3;
        int n = 30;
        double T = 1.0;
        double f = 1.0/T;
        double phase = 0.15;
        
        ZnLLS instance = new ZnLLS(n);

        double noisestd = 0.00001;
        GaussianNoise noise = new pubsim.distributions.GaussianNoise(0.0,noisestd*noisestd);

        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal(n);
        sig.setPeriod(T);
        sig.setPhase(phase);
        sig.setNoiseGenerator(noise);
        sig.setSparseGenerator(new PoissonRandomVariable(2));

        long seed = 1331;
        noise.setSeed(seed);
        sig.generateSparseSignal(n);
        double[] trans = sig.generateSparseSignal(n);
        Double[] y = sig.generateReceivedSignal();

        instance.estimate(y, fmin, fmax);
        double hatT = instance.getPeriod();
        double hatp = instance.getPhase();

        System.out.println(hatT + ", " + hatp);

        assertEquals(hatT, T, 0.001);
        assertEquals(hatp, phase, 0.001);

    }

//    /**
//     * Test of estimateFreq method, of class simulator.ZnLLS.
//     */
//    public void testPhaseInUniformNoise() {
//        System.out.println("Estimate With Phase");
//
//        double fmin = 0.4;
//        double fmax = 0.6;
//        int n = 1000;
//        double T = 2.0;
//        double f = 1.0/T;
//        double phase = 0.0;
//
//        NormalisedSamplingLLS instance = new NormalisedSamplingLLS(n, 4*n);
//
//        double noisestd = 0.001;
//        UniformNoise noise = new pubsim.distributions.UniformNoise(0.0, T + 0.2, 0);
//
//        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
//        sig.setPeriod(T);
//        sig.setPhase(phase);
//        sig.setNoiseGenerator(noise);
//
//        long seed = 1331;
//        noise.setSeed(seed);
//        sig.generateSparseSignal(n, seed);
//        double[] trans = sig.generateSparseSignal(n);
//        Double[] y = sig.generateReceivedSignal();
//
//        instance.estimate(y, fmin, fmax);
//        double hatT = instance.getPeriod();
//        double hatp = instance.getPhase();
//
//        System.out.println(hatT + ", " + hatp);
//
//        assertEquals(hatT, T, 0.01);
//        assertEquals(hatp, phase, 0.01);
//
//    }
    
}
