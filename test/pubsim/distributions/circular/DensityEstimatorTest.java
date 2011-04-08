/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import pubsim.distributions.RandomVariable;
import pubsim.distributions.UniformNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class DensityEstimatorTest {

    public DensityEstimatorTest() {
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
     * Test of pdf method, of class DensityEstimator.
     */
    @Test
    public void testPdf() {
        System.out.println("pdf");
        double[] data = {0.4, -0.4};
        RandomVariable ker = new UniformNoise(0, 0.3, 0);
        DensityEstimator dest = new DensityEstimator(data, ker);
        assertEquals(10.0/3.0, dest.pdf(0.5), 0.0000001);
        assertEquals(5.0/3.0, dest.pdf(0.39), 0.0000001);
        assertEquals(5.0/3.0, dest.pdf(-0.39), 0.0000001);
        assertEquals(0.0, dest.pdf(0), 0.0000001);
    }

}