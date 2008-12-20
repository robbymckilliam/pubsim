/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import distributions.GaussianNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class KitchenEstimatorTest {

    public KitchenEstimatorTest() {
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
     * Test of setSize method, of class KitchenEstimator.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int n = 0;
        KitchenEstimator instance = null;
        instance.setSize(n);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of estimate method, of class KitchenEstimator.
     */
    @Test
    public void testEstimate() {
        System.out.println("estimate");
        double[] real = null;
        double[] imag = null;
        KitchenEstimator instance = null;
        double[] expResult = null;
        double[] result = instance.estimate(real, imag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of estimateM method, of class KitchenEstimator.
     */
    @Test
    public void testHighestOrderParameter() {
        System.out.println("testHighestOrderParameter");

        int n = 20;
        double[] params = {0.1, 0.1};
        int a = params.length;

        PolynomialPhaseSignal siggen = new PolynomialPhaseSignal();
        siggen.setLength(n);
        siggen.setParameters(params);
        siggen.setNoiseGenerator(new GaussianNoise(0, 0.001));

        siggen.generateReceivedSignal();

        KitchenEstimator inst = new KitchenEstimator(a);
        inst.setSize(n);

        double[] p = inst.estimate(siggen.getReal(), siggen.getImag());

        System.out.println(VectorFunctions.print(p));

        assertTrue(Math.abs(p[a-1] - params[a-1])< 0.00001);

    }

    /**
     * Test of error method, of class KitchenEstimator.
     */
    @Test
    public void testError() {
        System.out.println("error");
        double[] real = null;
        double[] imag = null;
        double[] truth = null;
        KitchenEstimator instance = null;
        double[] expResult = null;
        double[] result = instance.error(real, imag, truth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}