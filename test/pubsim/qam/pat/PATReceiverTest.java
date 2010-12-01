/*
 * PATReceiverTest.java
 * JUnit based test
 *
 * Created on 27 November 2007, 12:50
 */

package pubsim.qam.pat;

import pubsim.qam.pat.PilotAssistedFadingNoisyQAM;
import pubsim.qam.pat.PATReceiver;
import pubsim.distributions.GaussianNoise;

import junit.framework.*;

/**
 *
 * @author Robby
 */
public class PATReceiverTest extends TestCase {
    
    public PATReceiverTest(String testName) {
        super(testName);
    }

    /**
     * Test of decode method, of class simulator.qam.PATReceiver.
     */
    public void testDecode() {
        System.out.println("decode");
        
        int M = 16;
        int T = 10;
        long seed = 11311;
        
        PilotAssistedFadingNoisyQAM siggen = new PilotAssistedFadingNoisyQAM();
        siggen.generateChannel();
        siggen.setQAMSize(M);
        siggen.setLength(T);
        siggen.setPATSymbol(3,3);
        //siggen.setChannel(1.0,0.0);
        
        GaussianNoise noise = new GaussianNoise(0.0,0.0001);
        siggen.setNoiseGenerator(noise);
        
        PATReceiver instance = new PATReceiver();
        instance.setQAMSize(M);
        instance.setT(T);
        instance.setPATSymbol(3,3);
        
        siggen.generateQAMSignal();
        siggen.generateReceivedSignal();
        
        instance.decode(siggen.getReal(), siggen.getImag());
        
        assertEquals(true, pubsim.VectorFunctions.distance_between(
                instance.getReal(),siggen.getTransmittedRealQAMSignal())<0.00001);
        assertEquals(true, pubsim.VectorFunctions.distance_between(
                instance.getImag(),siggen.getTransmittedImagQAMSignal())<0.00001);
    }
    
}
