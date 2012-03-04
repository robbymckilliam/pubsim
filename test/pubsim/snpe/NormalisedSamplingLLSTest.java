/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.snpe;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.distributions.GaussianNoise;
import pubsim.distributions.discrete.PoissonRandomVariable;

/**
 *
 * @author harprobey
 */
public class NormalisedSamplingLLSTest {

    public NormalisedSamplingLLSTest() {
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
     * Test of estimate method, of class NormalisedSamplingLLS.
     */
    @Test
    public void testEstimate() {
        System.out.println("estimate");

        double Tmin = 0.7;
        double Tmax = 1.3;
        int n = 30;
        double T = 1.1;
        double phase = 0.4;
        NormalisedSamplingLLS instance = new NormalisedSamplingLLS(n, 2*n);

        double noisestd = 0.001;
        GaussianNoise noise = new pubsim.distributions.GaussianNoise(0.0,noisestd*noisestd);

        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal(n);
        sig.setPeriod(T);
        sig.setPhase(phase);
        sig.setNoiseGenerator(noise);
        sig.setSparseGenerator(new PoissonRandomVariable(2));
        Integer[] trans = sig.generateSparseSignal();
        Double[] y = sig.generateReceivedSignal();

        instance.estimate(y, Tmin, Tmax);
        System.out.println(instance.getPeriod() + "\t" + instance.getPhase());
        assertEquals(T, instance.getPeriod(), 0.001);
        assertEquals(phase, instance.getPhase(), 0.001);
    }

}