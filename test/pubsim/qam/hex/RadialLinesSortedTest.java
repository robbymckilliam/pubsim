/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import pubsim.qam.hex.RadialLinesSorted;
import pubsim.qam.hex.FadingNoisyHex;
import pubsim.distributions.GaussianNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.VectorFunctionsTest;
import static pubsim.VectorFunctions.print;
import static pubsim.VectorFunctions.allElementsEqual;

/**
 *
 * @author Robby McKilliam
 */
public class RadialLinesSortedTest {

    public RadialLinesSortedTest() {
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
    public void decodeSymbolsCorrectly() {
        System.out.println("decodeSymbolsCorrectly");

        int N = 7;
        int M = 4;
        int iters = 10000;

        RadialLinesSorted rec = new RadialLinesSorted(N, M);
        //BruteForceHexReciever rec = new BruteForceHexReciever(N, M);

        FadingNoisyHex signal = new FadingNoisyHex(N, M);
        signal.setChannel(1.0, 0.0);
        signal.setNoiseGenerator(new GaussianNoise(0.0, 0.0001));

        for(int t = 0; t < iters; t++){

            signal.generateCodeword();
            signal.generateReceivedSignal();
            double[] yr = signal.getReal();
            double[] yi = signal.getImag();

            double[] urt = signal.getTransmittedRealCodeword();
            double[] uit = signal.getTransmittedImagCodeword();

            if( !allElementsEqual(urt) && !allElementsEqual(uit)) {

                rec.decode(yr, yi);

                double[] ur = rec.getReal();
                double[] ui = rec.getImag();

                if( !VectorFunctions.equal(urt, ur) || !VectorFunctions.equal(uit, ui) ){
                    System.out.println(print(urt));
                    System.out.println(print(uit));
                    System.out.println(print(ur));
                    System.out.println(print(ui));
                }

                VectorFunctionsTest.assertVectorsEqual(urt, ur);
                VectorFunctionsTest.assertVectorsEqual(uit, ui);
            }

        }
        
    }

}