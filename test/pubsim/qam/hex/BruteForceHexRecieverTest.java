/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import pubsim.qam.hex.FadingNoisyHex;
import pubsim.qam.hex.BruteForceHexReciever;
import pubsim.distributions.GaussianNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctionsTest;
import static pubsim.VectorFunctions.print;

/**
 *
 * @author robertm
 */
public class BruteForceHexRecieverTest {

    public BruteForceHexRecieverTest() {
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
     * Test of decode method, of class BruteForceHexReciever.
     */
    @Test
    public void testDecode() {
        System.out.println("testDecode");

        int N = 3;
        int M = 4;
        BruteForceHexReciever rec = new BruteForceHexReciever(N, M);

        FadingNoisyHex signal = new FadingNoisyHex(N, M);
        signal.setChannel(1.0, 0.0);
        signal.setNoiseGenerator(new GaussianNoise(0.0, 0.000001));

        signal.generateCodeword();
        signal.generateReceivedSignal();
        double[] yr = signal.getReal();
        double[] yi = signal.getImag();

        rec.decode(yr, yi);

        double[] urt = signal.getTransmittedRealCodeword();
        double[] uit = signal.getTransmittedImagCodeword();
        double[] ur = rec.getReal();
        double[] ui = rec.getImag();

        System.out.println(print(yr));
        System.out.println(print(yi));

        System.out.println(print(urt));
        System.out.println(print(uit));
        System.out.println(print(ur));
        System.out.println(print(ui));

        VectorFunctionsTest.assertVectorsEqual(urt, ur);
        VectorFunctionsTest.assertVectorsEqual(uit, ui);
    }


}