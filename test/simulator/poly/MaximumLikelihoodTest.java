/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simulator.VectorFunctions;

/**
 *
 * @author harprobey
 */
public class MaximumLikelihoodTest {

    public MaximumLikelihoodTest() {
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
     * Test of setSize method, of class MaximumLikelihood.
     */
    @Test
    public void testPolyFunctionValue() {
        System.out.println("testPolyFunctionValue");
        double[] yr = {0.1, 0.1, 0.1};
        double[] yi = {0.1, 0.1, 0.1};
        double[] p = {0.1, 0.1, 0.1};
        Matrix P = VectorFunctions.columnMatrix(p);
        MaximumLikelihood.PolynomialPhaseLikelihood func
                = new MaximumLikelihood.PolynomialPhaseLikelihood(yr, yi);
        double expr = 3.055198893365938;
        double res = func.value(P);
        assertEquals(res, expr, 0.000001);
        
    }

}