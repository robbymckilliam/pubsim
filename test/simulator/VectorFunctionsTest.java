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
 * @author Robby McKilliam
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
        
        double[] x = {2, 3, 1, 4};
        double[] Xi = new double[x.length];
        double[] Xr = new double[x.length];
        double[] expXi = {0, 1, 0, -1};
        double[] expXr = {10,1,-4 ,1};
        
        VectorFunctions.slowFT(x, Xi, Xr);
        
                
        System.out.println("Xi = " + VectorFunctions.print(Xi));
        System.out.println("Xr = " + VectorFunctions.print(Xr));
        
        for(int i = 0; i < x.length; i++){
            assertEquals(true, Math.abs(expXi[i] - Xi[i]) < 0.00001);
            assertEquals(true, Math.abs(expXr[i] - Xr[i]) < 0.00001);
        }
       
    }

    /**
     * Test of abs2FT method, of class simulator.VectorFunctions.
     */
    public void testAbs2FT() {
        System.out.println("abs2FT");
        
        double[] x = {2, 3, 1, 4};
        
        double[] expResult = {100.0000, 2.0000, 16.0000, 2.0000};
        double[] result = VectorFunctions.abs2FT(x);
        assertEquals(true, VectorFunctions.distance_between(expResult, result) < 0.00001);
       
    }

    /**
     * Test of distance_between method, of class simulator.VectorFunctions.
     */
    public void testDistance_between() {
        System.out.println("distance_between");
        
        double[] x = {0, 1, 3};
        double[] s = {0, 4, 1};
        
        double expResult = Math.sqrt(13.0);
        double result = VectorFunctions.distance_between(x, s);
        assertEquals(expResult,result);
  
    }

    /**
     * Test of angle_between method, of class simulator.VectorFunctions.
     */
    public void testAngle_between() {
        System.out.println("angle_between");
        
        double[] x = {0,1};
        double[] y = {1,1};
        
        double expResult = Math.PI/4;
        double result = VectorFunctions.angle_between(x, y);
        assertEquals(true, Math.abs(expResult - result) < 0.000001);

    }

    /**
     * Test of subtract method, of class simulator.VectorFunctions.
     */
    public void testSubtract() {
        System.out.println("subtract");
        
        double[] x = {1,2,3};
        double[] y = {1,-1,-1};
        
        double[] expResult = {0,3,4};
        double[] result = VectorFunctions.subtract(x, y);
        assertEquals(0.0, VectorFunctions.distance_between(expResult,result));
    }

    /**
     * Test of add method, of class simulator.VectorFunctions.
     */
    public void testAdd() {
        System.out.println("add");
        
        double[] x = {1,2,3};
        double[] y = {-1,1,1};
        
        double[] expResult = {0,3,4};
        double[] result = VectorFunctions.add(x, y);
        assertEquals(0.0, VectorFunctions.distance_between(expResult,result));
       
    }

    /**
     * Test of sum method, of class simulator.VectorFunctions.
     */
    public void testSum() {
        System.out.println("sum");
        
        double[] x = {1,1,4};
        
        double expResult = 6;
        double result = VectorFunctions.sum(x);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of sum2 method, of class simulator.VectorFunctions.
     */
    public void testSum2() {
        System.out.println("sum2");
        
        double[] x = {1,2,3};;
        
        double expResult = 14;
        double result = VectorFunctions.sum2(x);
        assertEquals(expResult, result);

    }

    /**
     * Test of mean method, of class simulator.VectorFunctions.
     */
    public void testMean() {
        System.out.println("mean");
        
        double[] x = {1,2,3};
        
        double expResult = 2;
        double result = VectorFunctions.mean(x);
        assertEquals(expResult, result);

    }

    /**
     * Test of magnitude method, of class simulator.VectorFunctions.
     */
    public void testMagnitude() {
        System.out.println("magnitude");
        
        double[] x = {1,2,3};
        
        double expResult = Math.sqrt(14.0);
        double result = VectorFunctions.magnitude(x);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of max method, of class simulator.VectorFunctions.
     */
    public void testMax() {
        System.out.println("max");
        
        double[] x = {1,4,2,3,-5};
        
        double expResult = 4.0;
        double result = VectorFunctions.max(x);
        assertEquals(expResult, result);
    }

    /**
     * Test of increasing method, of class simulator.VectorFunctions.
     */
    public void testIncreasing() {
        System.out.println("increasing");
        
        double[] x1 = {1,4,2,3,-5};
        double[] x2 = {1,2,4,7};
        
        assertEquals(false, VectorFunctions.increasing(x1));
        assertEquals(true, VectorFunctions.increasing(x2));
       
    }

    /**
     * Test of dot method, of class simulator.VectorFunctions.
     */
    public void testDot() {
        System.out.println("dot");
        
        double[] x = {1,2,3};
        double[] y = {1,-1,4};
        
        double expResult = 11.0;
        double result = VectorFunctions.dot(x, y);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testMin() {
        System.out.println("min");
        
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        
        double expResult = -11.0;
        double result = VectorFunctions.min(x);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of maxDistance method, of class simulator.VectorFunctions.
     */
    public void testMax_distance() {
        System.out.println("max_distance");
        
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        
        double expResult = 20.0;
        double result = VectorFunctions.maxDistance(x);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of maxDistance method, of class simulator.VectorFunctions.
     */
    public void testMatrixMultVector() {
        System.out.println("matrixMultVector");
        
        double[] x = {1, 2, 3};
        double[][] M = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        double[] y = new double[M.length];
        
        double[] expResult =  {14, 32, 50, 68};
        VectorFunctions.matrixMultVector(M, x, y);
        assertEquals(true, 
                VectorFunctions.distance_between(expResult,y)<0.00001);
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
