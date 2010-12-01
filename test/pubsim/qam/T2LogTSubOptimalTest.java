/*
 * T2LogTSubOptimalTest.java
 * JUnit based test
 *
 * Created on 1 October 2007, 10:04
 */

package pubsim.qam;

import pubsim.qam.FadingNoisyQAM;
import pubsim.qam.T2LogTSubOptimal;
import junit.framework.*;
import java.util.TreeMap;
import pubsim.VectorFunctions;
import pubsim.distributions.GaussianNoise;

/**
 *
 * @author Robby McKilliam
 */
public class T2LogTSubOptimalTest extends TestCase {
    
    public T2LogTSubOptimalTest(String testName) {
        super(testName);
    }

    /**
     * Test of decode method, of class simulator.qam.T2LogTSubOptimal.
     */
    public void testDecode() {
        System.out.println("decode");
        
        int M = 16;
        int T = 5;
        long seed = 11111;
        
        FadingNoisyQAM siggen = new FadingNoisyQAM(M);
        //siggen.setChannel(1.0,0.0);
        
        GaussianNoise noise = new GaussianNoise(0.0,0.00001);
        siggen.setNoiseGenerator(noise);
        
        T2LogTSubOptimal instance = new T2LogTSubOptimal(20);
        instance.setQAMSize(M);
        instance.setT(T);
        
        siggen.setSeed(seed);
        noise.setSeed(seed);
        siggen.generateChannel();
        
        siggen.generateQAMSignal(T);
        siggen.generateReceivedSignal();
        instance.decode(siggen.getInphase(), siggen.getQuadrature());
        
        System.out.println("treal = " + VectorFunctions.print(siggen.getTransmittedRealQAMSignal()));
        System.out.println("rreal = " + VectorFunctions.print(instance.getReal()));
        System.out.println("timag = " + VectorFunctions.print(siggen.getTransmittedImagQAMSignal()));
        System.out.println("rimag = " + VectorFunctions.print(instance.getImag()));
        
        assertEquals(true, instance.ambiguityEqual(siggen.getTransmittedRealQAMSignal(), 
                siggen.getTransmittedImagQAMSignal(), 
                instance.getReal(), instance.getImag()));
    }
    
}
