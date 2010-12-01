/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.optimisation;

import pubsim.optimisation.AutoDerivativeFunction;
import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class AutoDerivativeFunctionTest {

    public AutoDerivativeFunctionTest() {
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
     * Test of gradient method with a 1D quadratic function.
     */
    @Test
    public void testGradientWith1DQuadratic() {
        System.out.println("testGradientWith1DQuadratic");
        double val = 1.9;
        Matrix x = new Matrix(1,1);
        x.set(0,0, val);
        AutoDerivativeFunction instance = new AutoDerivativeFunction() {
            public double value(Matrix x) {
                double v = x.get(0,0);
                return -v*v;
            }
        };
        double expResult = -2.0*val;
        Matrix resultM = instance.gradient(x);
        double result = resultM.get(0,0);
        assertEquals(expResult, result, 0.0000001);
    }

    /**
     * Test of gradient method with a 2D quadratic function.
     */
    @Test
    public void testGradientWith2DQuadratic() {
        System.out.println("testGradientWith2DQuadratic");
        Matrix x = new Matrix(2,1);
        x.set(0,0, -2.0);
        x.set(1,0, -2.0);
        AutoDerivativeFunction instance = new AutoDerivativeFunction() {
            public double value(Matrix x) {
                double x1 = x.get(0,0);
                double x2 = x.get(1,0);
                return -x1*x1 - x2*x2;
            }
        };
        Matrix expr = new Matrix(2,1);
        expr.set(0,0, 4.0);
        expr.set(1,0, 4.0);
        Matrix result = instance.gradient(x);
        assertEquals(0.0, expr.minus(result).normF(), 0.0000001);
    }

        /**
     * Test of gradient method with a 1D quadratic function.
     */
    @Test
    public void testHessianWith1DQuadratic() {
        System.out.println("testHessianWith1DQuadratic");
        Matrix x = new Matrix(1,1);
        x.set(0,0, -2.0);
        AutoDerivativeFunction instance = new AutoDerivativeFunction() {
            public double value(Matrix x) {
                double v = x.get(0,0);
                return -v*v;
            }
        };
        double expResult = -2.0;
        Matrix resultM = instance.hessian(x);
        double result = resultM.get(0,0);
        assertEquals(expResult, result, 0.00001);
    }


            /**
     * Test of gradient method with a 1D quadratic function.
     */
    @Test
    public void testHessianWith2DQuadratic() {
        System.out.println("testHessianWith2DQuadratic");
        Matrix x = new Matrix(2,1);
        x.set(0,0, -2.0);
        x.set(1,0, -2.0);
        AutoDerivativeFunction instance = new AutoDerivativeFunction() {
            public double value(Matrix x) {
                double x1 = x.get(0,0);
                double x2 = x.get(1,0);
                return -x1*x1 - x2*x2;
            }
        };
        Matrix expr = new Matrix(2,2);
        expr.set(0,0, -2.0); expr.set(0,1, 0.0);
        expr.set(1,0, 0.0); expr.set(1,1, -2.0);
        Matrix result = instance.hessian(x);
        assertEquals(0.0, expr.minus(result).normF(), 0.0001);
    }

}