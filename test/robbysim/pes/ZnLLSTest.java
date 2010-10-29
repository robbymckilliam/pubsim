/*
 * ZnLLSTest.java
 * JUnit based test
 *
 * Created on 9 July 2007, 12:24
 */

package robbysim.pes;

import robbysim.pes.SparseNoisyPeriodicSignal;
import robbysim.pes.ModifiedSamplingLLS;
import robbysim.pes.ZnLLS;
import robbysim.distributions.GaussianNoise;
import robbysim.distributions.UniformNoise;
import junit.framework.*;
import java.util.TreeMap;
import simulator.*;

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
        
        double fmin = 0.7;
        double fmax = 1.3;
        int n = 30;
        double f = 1.0;
        double T = 1/f;
        ZnLLS instance = new ZnLLS();
        
        double noisestd = 0.01;
        GaussianNoise noise = new robbysim.distributions.GaussianNoise(0.0,noisestd*noisestd);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoiseGenerator(noise);
          
        long seed = 1331;
        noise.setSeed(seed);
        sig.generateSparseSignal(n, seed);
        double[] trans = sig.generateSparseSignal(n);
        double[] y = sig.generateReceivedSignal();
        
        double expResult = f;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(true, Math.abs(result-expResult)<0.001);

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
        
        ZnLLS instance = new ZnLLS();

        double noisestd = 0.001;
        GaussianNoise noise = new robbysim.distributions.GaussianNoise(0.0,noisestd*noisestd);

        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setPhase(phase);
        sig.setNoiseGenerator(noise);

        long seed = 1331;
        noise.setSeed(seed);
        sig.generateSparseSignal(n, seed);
        double[] trans = sig.generateSparseSignal(n);
        double[] y = sig.generateReceivedSignal();

        instance.estimate(y, fmin, fmax);
        double hatT = instance.getPeriod();
        double hatp = instance.getPhase();

        System.out.println(hatT + ", " + hatp);

        assertEquals(hatT, T, 0.001);
        assertEquals(hatp, phase, 0.001);

    }

    /**
     * Test of estimateFreq method, of class simulator.ZnLLS.
     */
    public void testPhaseInUniformNoise() {
        System.out.println("Estimate With Phase");

        double fmin = 0.4;
        double fmax = 0.6;
        int n = 6000;
        double T = 2.0;
        double f = 1.0/T;
        double phase = 0.0;

        ModifiedSamplingLLS instance = new ModifiedSamplingLLS(4*n);

        double noisestd = 0.001;
        UniformNoise noise = new robbysim.distributions.UniformNoise(0.0, T + 0.2, 0);

        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setPhase(phase);
        sig.setNoiseGenerator(noise);

        long seed = 1331;
        noise.setSeed(seed);
        sig.generateSparseSignal(n, seed);
        double[] trans = sig.generateSparseSignal(n);
        double[] y = sig.generateReceivedSignal();

        instance.estimate(y, fmin, fmax);
        double hatT = instance.getPeriod();
        double hatp = instance.getPhase();

        System.out.println(hatT + ", " + hatp);

        assertEquals(hatT, T, 0.01);
        assertEquals(hatp, phase, 0.01);

    }
    
}
