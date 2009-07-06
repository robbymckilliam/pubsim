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
import simulator.Point2;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class BruteLocationEstimatorTest {

    public BruteLocationEstimatorTest() {
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
     * Test of estimateLocation method, of class BruteLocationEstimator.
     */
    @Test
    public void testComputeLocation() {
        System.out.println("computeLocation");
        UniformNoise pnoise = new UniformNoise(0, 4);
        UniformNoise fnoise = new UniformNoise(1, 0.0);
        fnoise.setRange(0.6);
        Point2 loc = new Point2(0,0);
        int N = 4;

        NoisyPhaseSignals sig = new NoisyPhaseSignals(loc, 
                Transmitter.getRandomArray(N, pnoise, fnoise));
        sig.setNoiseGenerator(new GaussianNoise(0,0.000001));
        Transmitter[] trans = sig.getTransmitters();

        double[] phi = sig.generateReceivedSignal();

        BruteLocationEstimator instance = new BruteLocationEstimator(trans);
        Point2 result = instance.estimateLocation(phi);
        System.out.print("result = " + VectorFunctions.print(result));

        phi = sig.generateReceivedSignal();

        result = instance.estimateLocation(phi);
        System.out.print("result = " + VectorFunctions.print(result));

        assertTrue(loc.equals(result, 0.01));
    }


}