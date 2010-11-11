/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.bearing;

import robbysim.bearing.AngularlLeastSquaresEstimator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import robbysim.distributions.RandomVariable;
import robbysim.distributions.processes.IIDNoise;
import robbysim.distributions.circular.WrappedGaussianNoise;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class LeastSquaresEstimatorTest {

    public LeastSquaresEstimatorTest() {
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
     * Test of estimateBearing method, of class AngularlLeastSquaresEstimator.
     */
    @Test
    public void testEstimateBearing() {
        System.out.println("estimateBearing");
        
        int n = 20;
        double mean = -Math.PI*0.2;
        
        RandomVariable noise = new WrappedGaussianNoise(mean, 0.0001);
        IIDNoise sig = new IIDNoise();
        sig.setLength(n);
        sig.setNoiseGenerator(noise);
        
        double[] y = sig.generateReceivedSignal();
        
        AngularlLeastSquaresEstimator instance = new AngularlLeastSquaresEstimator();

        double result = instance.estimateBearing(y);
        
        System.out.println(mean);
        System.out.println(result);
        
        
        assertTrue(Math.abs(result - mean)< 0.01);

    }

}