/*
 * AnLLSTest.java
 * JUnit based test
 *
 * Created on 20 June 2007, 14:03
 */

package simulator.pes;

import junit.framework.*;
import simulator.*;

/**
 *
 * @author robertm
 */
public class AnLLSTest extends TestCase {
    
    public AnLLSTest(String testName) {
        super(testName);
    }

    /**
     * Test of glueVector method, of class simulator.AnLLS.
     */
    public void testGlueVector() {
        System.out.println("glueVector");
        
        AnLLS instance = new AnLLS();
        
        double n = 3;
        instance.setSize((int)n+1);
        
        double i = 0;
        instance.glueVector(i);
        double[] result = instance.getg();
        double[] expResult0 = {0.0,0.0,0.0,0.0};
        for(int k = 0; k <= n; k++)
            assertEquals(result[k], expResult0[k]);
        
        i = 1;
        instance.glueVector(i);
        result = instance.getg();
        double[] expResult1 = {1/(n+1),1/(n+1),1/(n+1),-3/(n+1)};
        for(int k = 0; k <= n; k++)
            assertEquals(result[k], expResult1[k]);
        
        i = 2;
        instance.glueVector(i);
        result = instance.getg();
        double[] expResult2 = {2/(n+1),2/(n+1),-2/(n+1),-2/(n+1)};
        for(int k = 0; k <= n; k++)
            assertEquals(result[k], expResult2[k]);
        
        i = 3;
        instance.glueVector(i);
        result = instance.getg();
        double[] expResult3 = {3/(n+1),-1/(n+1),-1/(n+1),-1/(n+1)};
        for(int k = 0; k <= n; k++)
            assertEquals(result[k], expResult3[k]);
        
    }

    /**
     * Test of round method, of class simulator.AnLLS.
     */
    public void testRound() {
        System.out.println("round");
        
        int N = 0;
        AnLLS instance = new AnLLS();
        assertEquals(-2.0, instance.round(-1.5));
        assertEquals(-2.0, instance.round(-1.6));
        assertEquals(-1.0, instance.round(-1.3));
        assertEquals(2.0, instance.round(1.5));
        assertEquals(2.0, instance.round(1.6));
        assertEquals(1.0, instance.round(1.3));
    }
    
    
    /**
     * Test of nround method, of class simulator.AnLLS.
     */
    public void testNround() {
        System.out.println("round");
        
        int N = 0;
        AnLLS instance = new AnLLS();
        assertEquals(-2.0, instance.nround(-1.5));
        assertEquals(-2.0, instance.nround(-1.6));
        assertEquals(-1.0, instance.nround(-1.3));
        assertEquals(1.0, instance.nround(1.5));
        assertEquals(2.0, instance.nround(1.6));
        assertEquals(1.0, instance.nround(1.3));
    }
    
        /**
     * Test of pround method, of class simulator.AnLLS.
     */
    public void testPround() {
        System.out.println("round");
        
        int N = 0;
        AnLLS instance = new AnLLS();
        assertEquals(-1.0, instance.pround(-1.5));
        assertEquals(-2.0, instance.pround(-1.6));
        assertEquals(-1.0, instance.pround(-1.3));
        assertEquals(2.0, instance.pround(1.5));
        assertEquals(2.0, instance.pround(1.6));
        assertEquals(1.0, instance.pround(1.3));
    }

    /**
     * Test of estimateFreq method, of class simulator.AnLLS.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double[] y = {1.0,2.0,3.0,4.0};
        double fmin = 0.5;
        double fmax = 1.5;
        int n = 30;
        double T = 1.0;
        AnLLS instance = new AnLLS();
        
        GaussianNoise noise = new simulator.GaussianNoise(0.0,0.01*0.01);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoiseGenerator(noise);
          
        //noise.setSeed(200);
        double[] trans = sig.generateSparseSignal(n);
        y = sig.generateReceivedSignal();
        
        //System.out.println(VectorFunctions.print(trans));
        
        double expResult = T;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(expResult, result);

    }

    /**
     * Test of varianceBound method, of class simulator.AnLLS.
     */
    public void testVarianceBound() {
        System.out.println("varianceBound");
        
        double sigma = 0.0;
        double[] k = null;
        AnLLS instance = new AnLLS();
        
        double expResult = 0.0;
        double result = instance.varianceBound(sigma, k);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
