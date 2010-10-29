/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes;

import robbysim.fes.NoisyComplexSinusoid;
import robbysim.fes.KaysEstimator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class KaysEstimatorTest {

    public KaysEstimatorTest() {
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
     * Test of estimateFreq method, of class KaysEstimator.
     */
    @Test
    public void testEstimateFreq() {
        System.out.println("estimateFreq");

        int iters = 1;
        double f = 0.2;
        int n = 20;
        KaysEstimator instance = new KaysEstimator();

        NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
        signal.setSize(n);
        signal.setFrequency(f);
        signal.setPhase(0.3);
        robbysim.distributions.GaussianNoise noise = new robbysim.distributions.GaussianNoise(0.0,0.00001);
        signal.setNoiseGenerator(noise);

        signal.generateReceivedSignal();
        double result = instance.estimateFreq(signal.getReal(), signal.getImag());
        System.out.println("f = " + result);
        assertEquals(true, Math.abs(result - f)<0.0001);
    }

}