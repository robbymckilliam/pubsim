/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.location.twod;

import pubsim.location.twod.Transmitter;
import pubsim.location.twod.NoisyPhaseSignals;
import pubsim.location.twod.NearSetLocationEstimatorMaxDistance;
import pubsim.distributions.GaussianNoise;
import pubsim.distributions.UniformNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.IndexedDouble;
import pubsim.Point2;
import pubsim.VectorFunctions;

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
        UniformNoise fnoise = new UniformNoise(1, 0.6, 0);
        Point2 loc = new Point2(0,0);
        int N = 4;
        double maxdist = 4;

        NoisyPhaseSignals sig = new NoisyPhaseSignals(loc,
                Transmitter.getRandomArray(N, pnoise, fnoise));
        sig.setNoiseGenerator(new GaussianNoise(0,0.0000001));
        Transmitter[] trans = sig.getTransmitters();

        Double[] phi = sig.generateReceivedSignal();

        NearSetLocationEstimatorMaxDistance instance = new NearSetLocationEstimatorMaxDistance(trans, maxdist);
        Point2 result = instance.estimateLocation(phi);
        System.out.print("result = \n" + VectorFunctions.print(result));
        assertTrue(loc.equals(result, 0.01));

    }


}
