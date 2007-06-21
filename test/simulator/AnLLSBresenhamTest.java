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
        double fmin = 0.5;
        double fmax = 1.5;
        int n = 7;
        double T = 1.0;
        AnLLSBresenham instance = new AnLLSBresenham();
        
        
        GaussianNoise noise = new simulator.GaussianNoise(0.0,0.03*0.03);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoise(noise);
          
        //noise.setSeed(200);
        double[] trans = sig.generateTransmittedSignal(n);
        y = sig.generateReceivedSignal();
        
        System.out.println(VectorFunctions.print(trans));
        
        double expResult = 1.0;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(expResult, result);

    }
    
}
