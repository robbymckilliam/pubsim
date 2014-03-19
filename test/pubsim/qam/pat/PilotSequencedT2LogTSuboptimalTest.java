/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.qam.pat.PilotSequencedFadingNoisyQAM;
import pubsim.qam.pat.PilotSequence;
import pubsim.qam.pat.PilotSequencedT2LogTSuboptimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.Complex;
import pubsim.distributions.GaussianNoise;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class PilotSequencedT2LogTSuboptimalTest {

    public PilotSequencedT2LogTSuboptimalTest() {
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
     * Test of NN method, of class PilotSequencedT2LogTSuboptimal.
     */
    @Test
    public void NN() {
        System.out.println("NN");
        
        int M = 4;
        
        Complex[] ca = { new Complex(0.1,0.2), new Complex(0.2,0.1) };
        //Complex[] ca = { new Complex(0.01,0.2) };
        PilotSequence pseq = new PilotSequence(ca);
        
        double[] x = {0.11, 0.09, 4.3, -1.91};
        double[] y = new double[x.length];
        
        
        PilotSequencedT2LogTSuboptimal instance = new PilotSequencedT2LogTSuboptimal(40);
        instance.setQAMSize(M);
        instance.setPilotSequence(pseq);
        
        instance.NN(x, y);
        
        System.out.println(" y = " + VectorFunctions.print(y));
        
        double[] expr = {1.1, -0.8, 3.2, -2.9};
        assertEquals(true, VectorFunctions.distance_between(expr,y)<0.000001);
    }

    /**
     * Test of decode method, of class PilotSequencedT2LogTSuboptimal.
     */
    @Test
    public void decode() {
                System.out.println("decode");
        
        int M = 8;
        int T = 10;
        long seed = 11111;
        
        Complex[] ca = { new Complex(0.01,0.2), new Complex(0.2,0.01), new Complex(-0.1,-0.1) };
        //Complex[] ca = { new Complex(0.01,0.2) };
        PilotSequence pseq = new PilotSequence(ca);
        
        PilotSequencedFadingNoisyQAM siggen = new PilotSequencedFadingNoisyQAM(M);
        siggen.setChannel(1.0,0.0);
        siggen.setPilotSequence(pseq);
        
        GaussianNoise noise = new GaussianNoise(0.0,0.00000001);
        siggen.setNoiseGenerator(noise);
        
        PilotSequencedT2LogTSuboptimal instance = new PilotSequencedT2LogTSuboptimal(40);
        instance.setQAMSize(M);
        instance.setT(T);
        instance.setPilotSequence(pseq);
        
        //siggen.setSeed(seed);
        //noise.setSeed(seed);
        siggen.generateChannel();
        
        siggen.generateQAMSignal(T);
        siggen.generateReceivedSignal();
        instance.decode(siggen.getInphase(), siggen.getQuadrature());
        
        System.out.println("treal = " + VectorFunctions.print(siggen.getTransmittedRealQAMSignal()));
        System.out.println("rreal = " + VectorFunctions.print(instance.getReal()));
        System.out.println("timag = " + VectorFunctions.print(siggen.getTransmittedImagQAMSignal()));
        System.out.println("rimag = " + VectorFunctions.print(instance.getImag()));
        
        assertEquals(0.0, siggen.symbolErrorRate(
                instance.getReal(),
                instance.getImag(),
                siggen.getTransmittedRealQAMSignal(),
                siggen.getTransmittedImagQAMSignal()), 0.00001);
    }


}