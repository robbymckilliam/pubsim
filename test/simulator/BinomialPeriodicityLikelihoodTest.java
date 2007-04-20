/*
 * BinomialPeriodicityLikelihoodTest.java
 * JUnit based test
 *
 * Created on 20 April 2007, 15:52
 */

package simulator;

import junit.framework.*;

/**
 *
 * @author robertm
 */
public class BinomialPeriodicityLikelihoodTest extends TestCase {
    
    public BinomialPeriodicityLikelihoodTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of slowFT method, of class simulator.BinomialPeriodicityLikelihood.
     */
    public void testSlowFT() {
        System.out.println("slowFT");
        
        double[] x = null;
        double[] Xi = null;
        double[] Xr = null;
        BinomialPeriodicityLikelihood instance = new BinomialPeriodicityLikelihood();
        
        instance.slowFT(x, Xi, Xr);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abs2FT method, of class simulator.BinomialPeriodicityLikelihood.
     */
    public void testAbs2FT() {
        System.out.println("abs2FT");
        
        double[] x = new double[] {-2.0, 3.0, 1.0, 0.0};
        BinomialPeriodicityLikelihood instance = new BinomialPeriodicityLikelihood();
        
        double[] expResult = new double[] {4.0, 18.0, 16.0, 18.0};
        double[] result = instance.abs2FT(x);
        
        for(int i = 0; i < x.length; i++)
            assertEquals(expResult[i], result[i]);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of mean method, of class simulator.BinomialPeriodicityLikelihood.
     */
    public void testMean() {
        System.out.println("mean");
        
        double[] x = {2.0, 2.0, 1.0, 1.0};
        BinomialPeriodicityLikelihood instance = new BinomialPeriodicityLikelihood();
        
        double expResult = (2.0 + 2.0 + 1.0 + 1.0)/4.0;
        double result = instance.mean(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of max method, of class simulator.BinomialPeriodicityLikelihood.
     */
    public void testMax() {
        System.out.println("max");
        
        double[] x = {2.0, 2.0, 1.0, 1.0, 10.0};
        BinomialPeriodicityLikelihood instance = new BinomialPeriodicityLikelihood();
        
        double expResult = 10.0;
        double result = instance.max(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of estimateFreq method, of class simulator.BinomialPeriodicityLikelihood.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double[] y = {0.0,1.0,2.0,3.0,4.0,5.0};
        double fmin = 0.0;
        double fmax = 0.0;
        BinomialPeriodicityLikelihood instance = new BinomialPeriodicityLikelihood();
        
        double expResult = 1.0;
        instance.setSize(y.length);
        double result = instance.estimateFreq(y, 0.8, 1.2);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
