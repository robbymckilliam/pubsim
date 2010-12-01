/*
 * T3OptimalTest.java
 * JUnit based test
 *
 * Created on 27 September 2007, 15:30
 */

package pubsim.qam;

import pubsim.qam.T3Optimal;
import pubsim.qam.FadingNoisyQAM;
import junit.framework.*;
import pubsim.VectorFunctions;
import pubsim.distributions.GaussianNoise;

/**
 *
 * @author Robby McKilliam
 */
public class T3OptimalTest extends TestCase {
    
    public T3OptimalTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of decode method, of class simulator.qam.T3Optimal.
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
        
        T3Optimal instance = new T3Optimal();
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
