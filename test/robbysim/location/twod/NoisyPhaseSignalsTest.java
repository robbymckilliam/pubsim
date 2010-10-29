/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.location.twod;

import robbysim.location.twod.Transmitter;
import robbysim.location.twod.NoisyPhaseSignals;
import robbysim.distributions.GaussianNoise;
import robbysim.distributions.RandomVariable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import robbysim.Point2;

/**
 *
 * @author robertm
 */
public class NoisyPhaseSignalsTest {

    public NoisyPhaseSignalsTest() {
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
     * Test of generateReceivedSignal method, of class NoisyPhaseSignals.
     */
    @Test
    public void testGenerateReceivedSignal_0args() {
        System.out.println("generateReceivedSignal");
        Transmitter[] trans = new Transmitter[1];
        trans[0] = new Transmitter(new Point2(1.0,0.0), 0.6);
        NoisyPhaseSignals instance = new NoisyPhaseSignals(new Point2(0,0),
                trans);
        instance.setNoiseGenerator(new GaussianNoise(0,0));
        double[] phi = instance.generateReceivedSignal();
        assertEquals(-0.2, phi[0], 0.0000001);

        trans[0] = new Transmitter(new Point2(1.0,0.0), 2.1);
        phi = instance.generateReceivedSignal();
        assertEquals(1.0, phi[0], 0.0000001);
        
    }

    /**
     * Test of generateReceivedSignal method, of class NoisyPhaseSignals.
     */
    @Test
    public void testGenerateReceivedSignal_Point2() {
        System.out.println("generateReceivedSignal");
        Transmitter[] trans = new Transmitter[1];
        trans[0] = new Transmitter(new Point2(1.0,0.0), 0.6);
        NoisyPhaseSignals instance = new NoisyPhaseSignals(new Point2(1,1),
                trans);
        instance.setNoiseGenerator(new GaussianNoise(0,0));
        double[] phi = instance.generateReceivedSignal(new Point2(0,0));
        assertEquals(-0.2, phi[0], 0.0000001);

        trans[0] = new Transmitter(new Point2(1.0,0.0), 2.1);
        phi = instance.generateReceivedSignal(new Point2(-1,0));
        assertEquals(-0.1, phi[0], 0.0000001);
    }

}