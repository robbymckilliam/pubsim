/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import distributions.GaussianNoise;
import distributions.UniformNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simulator.IndexedDouble;
import simulator.Point2;
import simulator.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class NearSetLocationEstimatorTest {

    public NearSetLocationEstimatorTest() {
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
     * Test of estimateLocation method, of class NearSetLocationEstimator.
     */
    @Test
    public void testComputeLocation() {
        System.out.println("computeLocation");

        UniformNoise pnoise = new UniformNoise(0, 2);
        UniformNoise fnoise = new UniformNoise(1, 0.0);
        fnoise.setRange(0.6);
        Point2 loc = new Point2(0,0);
        int N = 4;
        double maxdist = 4;

        NoisyPhaseSignals sig = new NoisyPhaseSignals(loc,
                Transmitter.getRandomArray(N, pnoise, fnoise));
        sig.setNoiseGenerator(new GaussianNoise(0,0.000001));
        Transmitter[] trans = sig.getTransmitters();

        double[] phi = sig.generateReceivedSignal();

        NearSetLocationEstimator instance = new NearSetLocationEstimator(trans, maxdist);
        Point2 result = instance.estimateLocation(phi);
        System.out.print("result = \n" + VectorFunctions.print(result));
        assertTrue(loc.equals(result, 0.01));

    }

    /**
     * Test of computeUnwrapping method, of class NearSetLocationEstimator.
     */
    @Test
    public void testComputeUnwrapping() {
        System.out.println("computeUnwrapping");
        Point2 x = null;
        NearSetLocationEstimator instance = null;
        double[] expResult = null;
        double[] result = instance.computeUnwrapping(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeSortedTransitions method, of class NearSetLocationEstimator.
     */
    @Test
    public void testComputeSortedTransitions() {
        System.out.println("computeSortedTransitions");
        Transmitter tran = null;
        double rad = 0.0;
        NearSetLocationEstimator instance = null;
        IndexedDouble[] expResult = null;
        IndexedDouble[] result = instance.computeSortedTransitions(tran, rad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}