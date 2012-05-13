/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import pubsim.qam.hex.FadingNoiseHexNoAmbiguity;
import pubsim.qam.hex.RadialLinesNoAmbiguity;
import pubsim.qam.hex.FadingNoisyHex;
import pubsim.qam.hex.RadialLinesReciever;
import pubsim.distributions.GaussianNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.Point2;
import pubsim.VectorFunctionsTest;
import static pubsim.VectorFunctions.print;

/**
 *
 * @author harprobey
 */
public class RadialLinesNoAmbiguityTest {

    public RadialLinesNoAmbiguityTest() {
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
     * Test of getReal method, of class RadialLinesNoAmbiguity.
     */
    @Test
    public void testAmbiguityResolve() {
        System.out.println("Ambiguity Resolve");
        int N = 3;
        int r = 4;
        RadialLinesNoAmbiguity instance = new RadialLinesNoAmbiguity(N, r);

        double[] yr = {1.0,1.0,1.0};
        double[] yi = {2.0,2.0,2.0};
        instance.decode(yr, yi);

        double[] exp = {0,0,0};
        VectorFunctionsTest.assertVectorsEqual(exp, instance.getReal());
        VectorFunctionsTest.assertVectorsEqual(exp, instance.getImag());
    }

        /**
     * Test of nextHexangonalNearPoint method, of class RadialLinesReciever.
     */
    @Test
    public void decodeSymbolsCorrectly() {
        System.out.println("decodeSymbolsCorrectly");

        int N = 3;
        int M = 4;
        RadialLinesNoAmbiguity rec = new RadialLinesNoAmbiguity(N, M);

        FadingNoiseHexNoAmbiguity signal = new FadingNoiseHexNoAmbiguity(N, M);
        signal.setChannel(1.0, 0.0);
        signal.setNoiseGenerator(new GaussianNoise(0.0, 0.0001));

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