/*
 * QPSKSignalTest.java
 * JUnit based test
 *
 * Created on 5 December 2007, 12:10
 */

package robbysim.psk;

import pubsim.psk.PSKSignal;
import junit.framework.*;
import java.util.Random;
import pubsim.distributions.GaussianNoise;
import pubsim.distributions.RandomVariable;
import pubsim.SignalGenerator;

/**
 *
 * @author Robby
 */
public class QPSKSignalTest extends TestCase {
    
    public QPSKSignalTest(String testName) {
        super(testName);
    }

    /**
     * Test of generateReceivedSignal method, of class simulator.qpsk.PSKSignal.
     */
    public void testGenerateReceivedSignal() {
        System.out.println("generateReceivedSignal");
        
        int n = 11;
        int M = 4;
        double transF = 0;
        double symbF = 4;
        
        PSKSignal instance = new PSKSignal();
        instance.setSymbolRate(symbF);
        instance.setCarrierFrequency(transF);
        instance.setLength(n);
        instance.setM(M);
        
        GaussianNoise noise = new GaussianNoise(0, 0);
        instance.setNoiseGenerator(noise);
        
        instance.generateTransmittedQPSKSignal();
        
        instance.generateReceivedSignal();
        
        double[] rr = instance.getReal();
        double[] ri = instance.getImag();
        double[] trans = instance.getTransmittedQPSKSignal();
        
        for(int i = 0; i < n; i++){
            double t = Math.atan2(ri[i],rr[i]);
            if(t < 0.0) t += 2*Math.PI;
            assertEquals(trans[(int)Math.floor(i*symbF)], 
                    M*t/(2*Math.PI) - 0.5);
        }
        
    }

    /**
     * Test of generateTransmittedQPSKSignal method, of class simulator.qpsk.PSKSignal.
     */
    public void testGenerateTransmittedQPSKSignal() {
        System.out.println("generateTransmittedQPSKSignal");
        
        int n = 11;
        int M = 4;
        double transF = 1;
        double symbF = 4;
        double sampF = 10;
        
        PSKSignal instance = new PSKSignal();
        instance.setSymbolRate(symbF);
        instance.setCarrierFrequency(transF);
        instance.setLength(n);
        instance.setM(M);
        
        instance.generateTransmittedQPSKSignal();
        
        double[] trans = instance.getTransmittedQPSKSignal();
        for(int i = 0; i < trans.length; i++){
            System.out.println("trans = " + trans[i]);
            assertEquals(true, trans[i] >= 0 && trans[i] < M);
        }
        
    }
    
    
}
