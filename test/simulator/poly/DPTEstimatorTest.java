/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import javax.vecmath.Vector2d;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simulator.Complex;
import simulator.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class DPTEstimatorTest {

    public DPTEstimatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of estimate method, of class DPTEstimator.
     */
    @Test
    public void testEstimate() {
        System.out.println("estimate");
        double[] real = null;
        double[] imag = null;
        DPTEstimator instance = null;
        double[] expResult = null;
        double[] result = instance.estimate(real, imag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of PPT method, of class DPTEstimator.
     */
    @Test
    public void PPTHasFirstMElementsZero() {
        int m = 4;
        int n = 10;
        Complex[] y = VectorFunctions.randomComplex(n);
        DPTEstimator instance = new DPTEstimator(m);
        instance.setSize(n);
        Complex[] result = instance.PPT(m, y);
        //System.out.print(VectorFunctions.print(result));
        for(int i = 0; i <= 2; i++){
            assertTrue(result[i].re() == 0.0);
            assertTrue(result[i].im() == 0.0);
        }
    }

    /**
     * Test of PPT2 method, of class DPTEstimator.
     */
    @Test
    public void PPT2HasFirstElementZeroAndLastElementCorrect() {
        System.out.println("PPT2");
        int m = 3;
        int n = 10;
        Complex[] y = VectorFunctions.randomComplex(n);
        DPTEstimator instance = new DPTEstimator(m);
        instance.setSize(n);
        Complex[] result = instance.PPT2(y);
        //System.out.print(VectorFunctions.print(result));

        //test first element is zero
        assertTrue(result[0].re() == 0.0);
        assertTrue(result[0].im() == 0.0);

        //test last element
        Complex last = y[n-1].times(y[n - 1 - (int)Math.round((double)n)/m].conjugate());
        assertTrue(result[n-1].im() == last.im());
        assertTrue(result[n-1].re() == last.re());
    }

}