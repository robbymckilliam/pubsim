/*
 * NonCoherentPATReceiverTest.java
 * JUnit based test
 *
 * Created on 28 November 2007, 09:44
 */

package pubsim.qam.pat;

import pubsim.qam.pat.NonCoherentPATReceiver;
import pubsim.qam.pat.PilotAssistedFadingNoisyQAM;
import junit.framework.*;
import pubsim.distributions.GaussianNoise;

/**
 *
 * @author Robby
 */
public class NonCoherentPATReceiverTest extends TestCase {
    
    public NonCoherentPATReceiverTest(String testName) {
        super(testName);
    }

    /**
     * Test of decode method, of class simulator.qam.NonCoherentPATReceiver.
     */
    public void testDecode() {
        System.out.println("decode");
        
        int M = 4;
        int T = 7;
        long seed = 12211;
        
        PilotAssistedFadingNoisyQAM siggen = new PilotAssistedFadingNoisyQAM();
        siggen.setSeed(seed);
        siggen.generateChannel();
        //siggen.setChannel(-1, 0);
        siggen.setQAMSize(M);
        siggen.setLength(T);
        siggen.setPATSymbol(3,1);
        //siggen.setChannel(1.0,0.0);
        
        GaussianNoise noise = new GaussianNoise(0.0,0.0001);
        siggen.setNoiseGenerator(noise);
        noise.setSeed(seed);
        
        NonCoherentPATReceiver instance = new NonCoherentPATReceiver();
        instance.setQAMSize(M);
        instance.setT(T);
        instance.setPATSymbol(3,1);
        
        siggen.generateQAMSignal();
        siggen.generateReceivedSignal();
        
        instance.decode(siggen.getReal(), siggen.getImag());
        
        System.out.println(" tr = " + pubsim.VectorFunctions.print(siggen.getTransmittedRealQAMSignal()));
        System.out.println(" ti = " + pubsim.VectorFunctions.print(siggen.getTransmittedImagQAMSignal()));
        System.out.println(" dr = " + pubsim.VectorFunctions.print(instance.getReal()));
        System.out.println(" di = " + pubsim.VectorFunctions.print(instance.getImag()));
        
        assertEquals(true, pubsim.VectorFunctions.distance_between(
                instance.getReal(),siggen.getTransmittedRealQAMSignal())<0.00001);
        assertEquals(true, pubsim.VectorFunctions.distance_between(
                instance.getImag(),siggen.getTransmittedImagQAMSignal())<0.00001);
    }
    
}
