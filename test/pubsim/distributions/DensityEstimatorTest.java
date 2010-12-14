/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
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
     * Tests using the rectangular kernel.
     */
    @Test
    public void testPdf() {
        System.out.println("pdf");
        double[] data = {1, -1};
        RandomVariable ker = new UniformNoise(0, 3, 0);
        DensityEstimator dest = new DensityEstimator(data, ker);
        assertEquals(1/3.0, dest.pdf(0), 0.0000001);
        assertEquals(1/6.0, dest.pdf(1), 0.0000001);
        assertEquals(1/6.0, dest.pdf(-1), 0.0000001);
        assertEquals(1/3.0, dest.pdf(0.4), 0.0000001);
        assertEquals(1/3.0, dest.pdf(-0.4), 0.0000001);
        assertEquals(1/6.0, dest.pdf(-2.4), 0.0000001);
        assertEquals(0.0, dest.pdf(-2.6), 0.0000001);
        assertEquals(0.0, dest.pdf(2.6), 0.0000001);
        assertEquals(0.0, dest.pdf(100), 0.0000001);
    }

//    /**
//     * Test of getMean method, of class DensityEstimator.
//     */
//    @Test
//    public void testGetMean() {
//        System.out.println("getMean");
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getVariance method, of class DensityEstimator.
//     */
//    @Test
//    public void testGetVariance() {
//        System.out.println("getVariance");
//        fail("The test case is a prototype.");
//    }

}