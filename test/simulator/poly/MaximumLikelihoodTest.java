/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import distributions.GaussianNoise;
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

    @Test
    public void testPolyFunctionValue() {
        System.out.println("testPolyFunctionValue");
        double[] yr = {0.1, 0.1, 0.1};
        double[] yi = {0.1, 0.1, 0.1};
        double[] p = {0.1, 0.1, 0.1};
        Matrix P = VectorFunctions.columnMatrix(p);
        MaximumLikelihood.PolynomialPhaseLikelihood func
                = new MaximumLikelihood.PolynomialPhaseLikelihood(yr, yi);
        //calculated in Matlab
        double expr = 3.055198893365938;
        double res = func.value(P);
        assertEquals(res, expr, 0.000001);
        
    }

    /**
     * Test of estimate method, of class MaximumLikelihood.
     */
    @Test
    public void testEstimate() {
        int n = 15;
        double[] params = {0.1, 0.1};
        int a = params.length;

        PolynomialPhaseSignal siggen = new PolynomialPhaseSignal();
        siggen.setLength(n);
        siggen.setParameters(params);
        siggen.setNoiseGenerator(new GaussianNoise(0, 0.000001));

        siggen.generateReceivedSignal();

        MaximumLikelihood inst = new MaximumLikelihood(params.length, 40);
        inst.setSize(n);

        double[] p = inst.estimate(siggen.getReal(), siggen.getImag());

        System.out.println(VectorFunctions.print(p));

        assertTrue(VectorFunctions.distance_between(p, params) < 0.0001);
    }

}