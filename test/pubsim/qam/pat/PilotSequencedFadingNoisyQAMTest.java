/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.qam.pat.PilotSequencedFadingNoisyQAM;
import pubsim.qam.pat.PilotSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.Complex;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class PilotSequencedFadingNoisyQAMTest {

    public PilotSequencedFadingNoisyQAMTest() {
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
     * Test of generateQAMSignal method, of class PilotSequencedFadingNoisyQAM.
     */
    @Test
    public void generateQAMSignal() {
        System.out.println("generateQAMSignal");
        
        Complex[] ca = { new Complex(1,2), new Complex(3,4), new Complex(5,6) };
        PilotSequence pseq = new PilotSequence(ca);
        
        PilotSequencedFadingNoisyQAM instance = new PilotSequencedFadingNoisyQAM();
        instance.setPilotSequence(pseq);
        instance.setQAMSize(8);
        instance.setLength(200);
        
        instance.generateQAMSignal();
        
        double[] xr = instance.getTransmittedRealQAMSignal();
        double[] xi = instance.getTransmittedImagQAMSignal();
            
        pseq.setPosition(0);
        Complex pat = pseq.current();
        for(int i = 0; i < xr.length; i++){
            assertEquals(true, xr[i]<=7+pat.re() && xr[i]>=-7+pat.re());
            assertEquals(true, xi[i]<=7+pat.im() && xi[i]>=-7+pat.im());
            pat = pseq.next();
        }
    }

}