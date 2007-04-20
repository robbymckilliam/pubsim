/*
 * SamplingEstimatorTest.java
 * JUnit based test
 *
 * Created on 20 April 2007, 16:07
 */

package simulator;

import junit.framework.*;

/**
 *
 * @author robertm
 */
public class SamplingEstimatorTest extends TestCase {
    
    public SamplingEstimatorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of setSize method, of class simulator.SamplingEstimator.
     */
    public void testSetSize() {
        System.out.println("setSize");
        
        int N = 0;
        SamplingEstimator instance = new SamplingEstimator();
        
        instance.setSize(N);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateObjective method, of class simulator.SamplingEstimator.
     */
    public void testCalculateObjective() {
        System.out.println("calculateObjective");
        
        double[] y = null;
        double f = 0.0;
        SamplingEstimator instance = new SamplingEstimator();
        
        double expResult = 0.0;
        double result = instance.calculateObjective(y, f);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of estimateFreq method, of class simulator.SamplingEstimator.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double[] y = {0.0,1.0,2.0,3.0,4.0,5.0, 6.0, 7.0, 8.0};
        double fmin = 0.0;
        double fmax = 0.0;
        SamplingEstimator instance = new SamplingEstimator();
        
        double expResult = 1.0;
        instance.setSize(y.length);
        double result = instance.estimateFreq(y, 0.8, 1.2);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("This never works, the estimator doesn't get it exactly right");
    }

    /**
     * Test of varianceBound method, of class simulator.SamplingEstimator.
     */
    public void testVarianceBound() {
        System.out.println("varianceBound");
        
        double sigma = 0.0;
        double[] k = null;
        SamplingEstimator instance = new SamplingEstimator();
        
        double expResult = 0.0;
        double result = instance.varianceBound(sigma, k);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
