/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;
import static simulator.Util.binom;
import static simulator.Util.factorial;

/**
 *
 * @author robertm
 */
public class VnmStarGluedTest {

    public VnmStarGluedTest() {
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
     * Test of discreteLegendrePolynomialVector method, of class VnmStarGlued.
     */
    @Test
    public void testDiscreteLegendrePolynomial() {
        System.out.println("discreteLegendrePolynomial");

        int n = 10;
        int m = 3;

        for(int i = 0; i <= m; i++){
            for(int j = 0; j <= m; j++){
                double[] pi = VnmStarGlued.discreteLegendrePolynomialVector(n, i);
                double[] pj = VnmStarGlued.discreteLegendrePolynomialVector(n, j);
                if(i!=j)
                    assertEquals(0.0, VectorFunctions.dot(pi, pj), 0.00001);
                if(i==j)
                    assertEquals(factorial(i)/binom(2*i, i)*binom(n+i, 2*i+1),
                                        VectorFunctions.dot(pi, pj), 0.00001);
            }
        }

    }

}