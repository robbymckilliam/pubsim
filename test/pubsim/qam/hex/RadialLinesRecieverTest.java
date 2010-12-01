/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

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
 * @author Robby McKilliam
 */
public class RadialLinesRecieverTest {

    public RadialLinesRecieverTest() {
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
     * Test of nextHexangonalNearPoint method, of class RadialLinesReciever.
     */
    @Test
    public void correctlyReturnCodewordNearestOrigin() {
        System.out.println("correctlyReturnCodewordNearestOrigin");
        
        int N = 5;
        RadialLinesReciever rec = new RadialLinesReciever(N,2);

        double[] yr = new double[N];
        double[] yi = new double[N];

        rec.decode(yr, yi);
        
        double[] ur = rec.getReal();
        double[] ui = rec.getImag();
        //System.out.println(print(rec.getReal()));
        //System.out.println(print(rec.getImag()));

        for(int n = 0; n < N; n++){
            assertEquals(0.0, ur[n], 0.0000001);
            assertEquals(0.0, ui[n], 0.0000001);
        }

    }

    /**
     * Test of nextHexangonalNearPoint method, of class RadialLinesReciever.
     */
    @Test
    public void decodeSymbolsCorrectly() {
        System.out.println("decodeSymbolsCorrectly");

        int N = 3;
        int M = 4;
        RadialLinesReciever rec = new RadialLinesReciever(N, M);

        FadingNoisyHex signal = new FadingNoisyHex(N, M);
        signal.setChannel(1.0, 0.0);
        signal.setNoiseGenerator(new GaussianNoise(0.0, 0.0));

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