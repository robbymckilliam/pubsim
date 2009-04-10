/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimisation;

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
public class NewtonRaphsonTest {

    public NewtonRaphsonTest() {
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
     * Test of Newton Raphson on a 1D quadratic function
     */
    @Test
    public void test1DQuadratic() {
        System.out.println("test1DQuadratic");
        Matrix x = new Matrix(1,1);
        x.set(0,0, -5);
        //simple quadratic function y = -x^2
        NewtonRaphson instance = new NewtonRaphson(
                new NewtonRaphsonFunction() {

            public double value(Matrix x) {
                double v = x.get(0, 0);
                return -v*v;
            }

            public Matrix hessian(Matrix x) {
                Matrix H = new Matrix(1,1);
                H.set(0,0, -2);
                return H;
            }

            public Matrix gradient(Matrix x) {
                double v = x.get(0, 0);
                return x.times(-2);
            }
        });
        double expResult = 0.0;
        Matrix resultM = instance.optimise(x);
        double result = resultM.get(0,0);
        assertEquals(expResult, result, 0.00000001);

    }

    /**
     * Test of Newton Raphson on a 2D quadratic function
     */
    @Test
    public void test2DQuadratic() {
        System.out.println("test2DQuadratic");
        Matrix x = new Matrix(2,1);
        x.set(0,0, -5);
        x.set(1,0, 20);
        //simple quadratic function z = -x1*x1 - x2*x2
        NewtonRaphson instance = new NewtonRaphson(
                new NewtonRaphsonFunction() {

            public double value(Matrix x) {
                double x1 = x.get(0, 0);
                double x2 = x.get(1, 0);
                return -x1*x1 - x2*x2;
            }

            public Matrix hessian(Matrix x) {
                Matrix H = new Matrix(2,2);
                H.set(0,0, -2); H.set(0,1, 0);
                H.set(1,0, 0); H.set(1,1, -2);
                return H;
            }

            public Matrix gradient(Matrix x) {
                double v = x.get(0, 0);
                return x.times(-2);
            }
        });
        Matrix exp = new Matrix(2,1);
        x.set(0,0, 0.0);
        x.set(1,0, 0.0);
        Matrix res = instance.optimise(x);
        assertEquals(exp.minus(res).normF(), 0.0, 0.00000001);

    }

}