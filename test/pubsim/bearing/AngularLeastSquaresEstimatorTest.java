/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.bearing;

import pubsim.distributions.circular.CircularRandomVariable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.distributions.RandomVariable;
import pubsim.distributions.processes.IIDNoiseVector;
import pubsim.distributions.circular.WrappedGaussian;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class AngularLeastSquaresEstimatorTest {

    public AngularLeastSquaresEstimatorTest() {
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
     * Test of estimateBearing method, of class AngularLeastSquaresEstimator.
     */
    @Test
    public void testEstimateBearing() {
        System.out.println("estimateBearing");
        
        int n = 20;
        double mean = -0.2;
        
        RandomVariable noise = new WrappedGaussian(mean, 0.0001);
        IIDNoiseVector sig = new IIDNoiseVector(n);
        sig.setNoiseGenerator(noise);
        
        double[] y = sig.generateReceivedSignal();
        
        AngularLeastSquaresEstimator instance = new AngularLeastSquaresEstimator(n);

        double result = instance.estimateBearing(y);
        
        System.out.println(mean);
        System.out.println(result);
        
        
        assertTrue(Math.abs(result - mean)< 0.01);

    }

    /**
     * Test of estimateBearing method, of class LeastSquaresEstimator.
     */
    @Test
    public void testConfidenceInterval() {
        System.out.println("estimate confidence interval");

        int n = 5000;
        double mean = 0.0;

        CircularRandomVariable noise = new WrappedGaussian(mean, 0.01);
        IIDNoiseVector sig = new IIDNoiseVector(n);
        sig.setNoiseGenerator(noise);

        double[] y = sig.generateReceivedSignal();

        BearingEstimator instance = new AngularLeastSquaresEstimator(n);

        double[] res = instance.confidenceInterval(y);

        System.out.println(mean + ", " + res[0]);
        assertEquals(mean, res[0], 0.01 );

        double var = instance.asymptoticVariance(noise, n);

        System.out.println(n*var);
        System.out.println(n*res[1]);
        assertEquals(n*var, n*res[1], 0.01 );

    }

}