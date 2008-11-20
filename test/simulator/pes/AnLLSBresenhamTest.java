/*
 * AnLLSBresenhamTest.java
 * JUnit based test
 *
 * Created on 21 June 2007, 12:41
 */

package simulator.pes;

import distributions.GaussianNoise;
import junit.framework.*;
import simulator.*;

/**
 *
 * @author Robby McKilliam
 */
public class AnLLSBresenhamTest extends TestCase {
    
    public AnLLSBresenhamTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateFreq method, of class simulator.AnLLSBresenham.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double[] y = {1.0,2.0,3.0,4.0,5.0};
        double fmin = 0.7;
        double fmax = 1.3;
        int n = 30;
        double f = 1.0;
        double T = 1/f;
        AnLLSBresenham instance = new AnLLSBresenham();
        
        double noisestd = Math.pow(10, (0.0 + 0.05*5));
        GaussianNoise noise = new distributions.GaussianNoise(0.0,noisestd*noisestd);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoiseGenerator(noise);
          
        long seed = 133;
        noise.setSeed(seed);
        sig.generateSparseSignal(n, seed);
        double[] trans = sig.generateSparseSignal(n);
        y = sig.generateReceivedSignal();
        
        //System.out.println("trans = " + VectorFunctions.print(trans));
        
        double expResult = f;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(expResult, result);

    }
    
}
