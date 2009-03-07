/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import distributions.NoiseGenerator;
import simulator.NoiseVector;
import distributions.circular.WrappedGaussianNoise;
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
     * Test of estimateBearing method, of class LeastSquaresEstimator.
     */
    @Test
    public void testEstimateBearing() {
        System.out.println("estimateBearing");
        
        int n = 20;
        double mean = -Math.PI*0.2;
        
        NoiseGenerator noise = new WrappedGaussianNoise(mean, 0.0001);
        NoiseVector sig = new NoiseVector();
        sig.setLength(n);
        sig.setNoiseGenerator(noise);
        
        double[] y = sig.generateReceivedSignal();
        
        LeastSquaresEstimator instance = new LeastSquaresEstimator();

        double result = instance.estimateBearing(y);
        
        System.out.println(mean);
        System.out.println(result);
        
        
        assertTrue(Math.abs(result - mean)< 0.01);

    }

}