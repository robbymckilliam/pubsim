/*
 * SparseNoisyPeriodicSignalTest.java
 * JUnit based test
 *
 * Created on 7 May 2007, 12:35
 */

package simulator.pes;

import junit.framework.*;
import simulator.*;

/**
 *
 * @author Robby McKilliam
 */
public class SparseNoisyPeriodicSignalTest extends TestCase {
    
    public SparseNoisyPeriodicSignalTest(String testName) {
        super(testName);
    }

    /**
     * Test of generateReceivedSignal method, of class simulator.SparseNoisyPeriodicSignal.
     */
    public void testGenerateTransmittedSignal() {
        System.out.println("generateReceivedSignal");
        
        SparseNoisyPeriodicSignal instance = new SparseNoisyPeriodicSignal();
        
        double[] sig = instance.generateSparseSignal(20);
        boolean result = false;
        result = VectorFunctions.increasing(sig);
        for(int i = 0; i < sig.length; i++){
            result &= (((long)sig[i]) == Math.round(sig[i]));
        }
        assertEquals(true, result);
        
    }
    
    /**
     * Test of generateSparseSignal method, of class simulator.SparseNoisyPeriodicSignal.
     */
    public void testGenerateRecievedSignal() {
        System.out.println("generateTransmittedSignal");
        
        int length = 20;
        double T = 2.0;
        SparseNoisyPeriodicSignal instance = new SparseNoisyPeriodicSignal();
        
        simulator.NoiseGenerator noise = new simulator.UniformNoise(0.0, 1.0/3.0);
        instance.setNoiseGenerator(noise);   
        double[] rec_sig = instance.generateSparseSignal(length);
        instance.setSparseSignal(rec_sig);
        instance.setPeriod(T);
       
        double[] sig = instance.generateReceivedSignal();
        boolean result;
        for (int i = 0; i < length; i++){
            result = (sig[i] <= (T*rec_sig[i] + 1.0)) && (sig[i] >= (T*rec_sig[i] - 1.0));
            assertEquals(true, result);
        }
        
    }
    
}
