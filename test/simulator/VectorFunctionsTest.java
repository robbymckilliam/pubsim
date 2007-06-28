/*
 * VectorFunctionsTest.java
 * JUnit based test
 *
 * Created on 28 June 2007, 14:41
 */

package simulator;

import junit.framework.*;
import javax.vecmath.GVector;
import java.util.Random;

/**
 *
 * @author robertm
 */
public class VectorFunctionsTest extends TestCase {
    
    public VectorFunctionsTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of slowFT method, of class simulator.VectorFunctions.
     */
    public void testSlowFT() {
        System.out.println("slowFT");
        
        double[] x = null;
        double[] Xi = null;
        double[] Xr = null;
        
        VectorFunctions.slowFT(x, Xi, Xr);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abs2FT method, of class simulator.VectorFunctions.
     */
    public void testAbs2FT() {
        System.out.println("abs2FT");
        
        double[] x = null;
        
        double[] expResult = null;
        double[] result = VectorFunctions.abs2FT(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of distance_between method, of class simulator.VectorFunctions.
     */
    public void testDistance_between() {
        System.out.println("distance_between");
        
        double[] x = null;
        double[] s = null;
        
        double expResult = 0.0;
        double result = VectorFunctions.distance_between(x, s);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of angle_between method, of class simulator.VectorFunctions.
     */
    public void testAngle_between() {
        System.out.println("angle_between");
        
        double[] x = null;
        double[] y = null;
        
        double expResult = 0.0;
        double result = VectorFunctions.angle_between(x, y);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subtract method, of class simulator.VectorFunctions.
     */
    public void testSubtract() {
        System.out.println("subtract");
        
        double[] x = null;
        double[] y = null;
        
        double[] expResult = null;
        double[] result = VectorFunctions.subtract(x, y);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class simulator.VectorFunctions.
     */
    public void testAdd() {
        System.out.println("add");
        
        double[] x = null;
        double[] y = null;
        
        double[] expResult = null;
        double[] result = VectorFunctions.add(x, y);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sum method, of class simulator.VectorFunctions.
     */
    public void testSum() {
        System.out.println("sum");
        
        double[] x = null;
        
        double expResult = 0.0;
        double result = VectorFunctions.sum(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sum2 method, of class simulator.VectorFunctions.
     */
    public void testSum2() {
        System.out.println("sum2");
        
        double[] x = null;
        
        double expResult = 0.0;
        double result = VectorFunctions.sum2(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mean method, of class simulator.VectorFunctions.
     */
    public void testMean() {
        System.out.println("mean");
        
        double[] x = null;
        
        double expResult = 0.0;
        double result = VectorFunctions.mean(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of magnitude method, of class simulator.VectorFunctions.
     */
    public void testMagnitude() {
        System.out.println("magnitude");
        
        double[] x = null;
        
        double expResult = 0.0;
        double result = VectorFunctions.magnitude(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of max method, of class simulator.VectorFunctions.
     */
    public void testMax() {
        System.out.println("max");
        
        double[] x = null;
        
        double expResult = 0.0;
        double result = VectorFunctions.max(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increasing method, of class simulator.VectorFunctions.
     */
    public void testIncreasing() {
        System.out.println("increasing");
        
        double[] x = null;
        
        boolean expResult = true;
        boolean result = VectorFunctions.increasing(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of print method, of class simulator.VectorFunctions.
     */
    public void testPrint() {
        System.out.println("print");
        
        double[] x = null;
        
        String expResult = "";
        String result = VectorFunctions.print(x);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dot method, of class simulator.VectorFunctions.
     */
    public void testDot() {
        System.out.println("dot");
        
        double[] x = null;
        double[] y = null;
        
        double expResult = 0.0;
        double result = VectorFunctions.dot(x, y);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testMin() {
        System.out.println("min");
        
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};;
        
        double expResult = -11.0;
        double result = VectorFunctions.min(x);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of max_distance method, of class simulator.VectorFunctions.
     */
    public void testMax_distance() {
        System.out.println("max_distance");
        
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        
        double expResult = 20.0;
        double result = VectorFunctions.max_distance(x);
        assertEquals(expResult, result);
    }
    
    /**
     * This tests some of the Gvectorfunctions.
     */
    public void testGVector() {
        System.out.println("testGVector");
        
        Random rand = new Random();
        
        double[] x = new double[1000];
        double[] y = new double[1000];
        
        for (int i = 0; i < x.length; i++){
            x[i] = rand.nextGaussian();
            y[i] = rand.nextGaussian();
        }
        
        GVector gx = new GVector(x);
	GVector gy = new GVector(y);
        
        assertEquals(VectorFunctions.sum2(x), gx.normSquared());
        assertEquals(VectorFunctions.sum2(y), gy.normSquared());
        assertEquals(VectorFunctions.dot(x,y), gx.dot(gy));
        
    }
    
}
