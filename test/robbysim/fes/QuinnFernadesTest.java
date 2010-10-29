/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes;

import robbysim.fes.NoisyComplexSinusoid;
import robbysim.fes.QuinnFernades;
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
public class QuinnFernadesTest {

    public QuinnFernadesTest() {
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
     * Test of estimateFreq method, of class QuinnFernades.
     */
    @Test
    public void testEstimateFreq() {
        System.out.println("estimateFreq");

        int iters = 10;
        double f = 0.1;
        QuinnFernades instance = new QuinnFernades();

        NoisyComplexSinusoid signal = new NoisyComplexSinusoid();
        signal.setFrequency(f);
        signal.setLength(64);
        robbysim.distributions.GaussianNoise noise = new robbysim.distributions.GaussianNoise(0.0,0.0001);
        signal.setNoiseGenerator(noise);

        for(int i=0; i < iters; i++ ){
            signal.generateReceivedSignal();
            double result = instance.estimateFreq(signal.getReal(), signal.getImag());
            System.out.println(result);
            assertEquals(result, f, 0.001);
        }

    }

}