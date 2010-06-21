/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly.dptspecialm2;

import distributions.GaussianNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import simulator.poly.PolynomialPhaseSignal;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class AngularLeastSquaresTest {

    public AngularLeastSquaresTest() {
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
     * Test of estimate method, of class MaximumLikelihood.
     */
    @Test
    public void testEstimate() {
        int n = 10;
        double[] params = {0.4, 0.49, 0.5/n };
        int m = params.length-1;

        PolynomialPhaseSignal siggen = new PolynomialPhaseSignal();
        siggen.setLength(n);
        siggen.setParameters(params);
        siggen.setNoiseGenerator(new GaussianNoise(0, 0.0000001));

        siggen.generateReceivedSignal();

        AngularLeastSquares inst = new AngularLeastSquares();
        inst.setSize(n);

        double[] p = inst.estimate(siggen.getReal(), siggen.getImag());

        System.out.println(VectorFunctions.print(p));

        assertTrue(VectorFunctions.distance_between(p, params) < 0.001);
    }

    /**
     * Test of estimate method, of class MaximumLikelihood.
     */
    @Test
    public void testError() {
        int n = 10;
        double[] params = {0.4, 0.49, 0.5/n };
        int m = params.length-1;

        PolynomialPhaseSignal siggen = new PolynomialPhaseSignal();
        siggen.setLength(n);
        siggen.setParameters(params);
        siggen.setNoiseGenerator(new GaussianNoise(0, 0.0000001));

        siggen.generateReceivedSignal();

        AngularLeastSquares inst = new AngularLeastSquares();
        inst.setSize(n);

        double[] p = inst.error(siggen.getReal(), siggen.getImag(), params);

        System.out.println(VectorFunctions.print(p));

        assertTrue(p.length == 3);
    }

}