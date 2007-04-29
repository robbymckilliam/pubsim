/*
 * BasisScrambleEstimatorTest.java
 * JUnit based test
 *
 * Created on 29 April 2007, 21:54
 */

package simulator;

import junit.framework.*;

/**
 *
 * @author Robby
 */
public class BasisScrambleEstimatorTest extends TestCase {
    
    public BasisScrambleEstimatorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of setSize method, of class simulator.BasisScrambleEstimator.
     */
    public void testSetSize() {
        System.out.println("setSize");
        
        int n = 0;
        BasisScrambleEstimator instance = new BasisScrambleEstimator();
        
        instance.setSize(n);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of estimateFreq method, of class simulator.BasisScrambleEstimator.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double[] y = {0.0,1.0,2.0,3.0,4.0};
        double fmin = 0.55;
        double fmax = 1.95;
        BasisScrambleEstimator instance = new BasisScrambleEstimator();
        instance.setSize(y.length);
        
        double expResult = 1.0;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of varianceBound method, of class simulator.BasisScrambleEstimator.
     */
    public void testVarianceBound() {
        System.out.println("varianceBound");
        
        double sigma = 0.0;
        double[] s = null;
        BasisScrambleEstimator instance = new BasisScrambleEstimator();
        
        double expResult = 0.0;
        double result = instance.varianceBound(sigma, s);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
