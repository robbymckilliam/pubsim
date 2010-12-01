/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.bearing;

import pubsim.bearing.BearingEstimator;
import pubsim.bearing.VectorMeanEstimator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.distributions.RandomVariable;
import pubsim.distributions.processes.IIDNoise;
import pubsim.distributions.circular.WrappedGaussian;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class VectorMeanEstimatorTest {

    public VectorMeanEstimatorTest() {
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
     * Test of estimateBearing method, of class LeastSquaresEstimator.
     */
    @Test
    public void testEstimateBearing() {
        System.out.println("estimateBearing");
        
        int n = 20;
        double mean = 0.2;
        
        RandomVariable noise = new WrappedGaussian(mean, 0.0001);
        IIDNoise sig = new IIDNoise(n);
        sig.setNoiseGenerator(noise);
        
        double[] y = sig.generateReceivedSignal();
        
        BearingEstimator instance = new VectorMeanEstimator();

        double result = instance.estimateBearing(y);
        
        System.out.println(mean);
        System.out.println(result);
        
        
        assertTrue(Math.abs(result - mean)< 0.01);

    }

}