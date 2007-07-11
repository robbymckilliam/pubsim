/*
 * AnLLSBresenhamTest.java
 * JUnit based test
 *
 * Created on 21 June 2007, 12:41
 */

package simulator;

import junit.framework.*;

/**
 *
 * @author Robby
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
        int n = 10;
        double f = 1.29;
        double T = 1/f;
        AnLLSBresenham instance = new AnLLSBresenham();
        
        
        GaussianNoise noise = new simulator.GaussianNoise(0.0,0.03*0.03*0.03*0.03);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoise(noise);
          
        long seed = 106;
        noise.setSeed(seed);
        sig.generateTransmittedSignal(n, seed);
        double[] trans = sig.generateTransmittedSignal(n);
        y = sig.generateReceivedSignal();
        
        System.out.println("trans = " + VectorFunctions.print(trans));
        
        double expResult = f;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(expResult, result);

    }
    
}
