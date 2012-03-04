/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.psk.decoder;

import pubsim.psk.decoder.PSKSignal;
import pubsim.psk.decoder.Util;
import pubsim.psk.decoder.SweldensNoncoherent;
import junit.framework.TestCase;
import pubsim.distributions.ContinuousRandomVariable;
import pubsim.VectorFunctions;

/**
 *
 * @author robertm
 */
public class SweldensNoncoherentTest extends TestCase {
    
    public SweldensNoncoherentTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of decode method, of class SweldensNoncoherent.
     */
    public void testDecode() {
        System.out.println("decode");
        
        int iters = 100;
        int M = 4;
        int T = 10;
        
        PSKSignal signal = new PSKSignal();
        signal.setM(M);
        signal.setLength(T);
        signal.setChannel(1.0/Math.sqrt(2.0), 1.0/Math.sqrt(2.0));
        //signal.generateChannel();
        
        ContinuousRandomVariable noise = new pubsim.distributions.UniformNoise(0.0, 0.001);
        signal.setNoiseGenerator(noise);  
               
        signal.generateReceivedSignal();
        
        //System.out.println(" recsig = " + VectorFunctions.print(signal.getReceivedSignal()));
        
        SweldensNoncoherent instance = new SweldensNoncoherent();
        instance.setM(M);
        instance.setT(T);
        
        for(int i = 0; i < iters; i++){
            //signal.generateChannel();
            signal.generatePSKSignal();
            signal.generateReceivedSignal();
            
            double[] result = instance.decode(signal.getReceivedSignal());
            
            System.out.println(" result = " + VectorFunctions.print(result));
            System.out.println(" expect = " + VectorFunctions.print(signal.getPSKSignal()));
            double[] s = VectorFunctions.subtract(signal.getPSKSignal(), result);
            System.out.println("s = " + VectorFunctions.print(s));
        
            assertTrue(Util.differentialEncodedEqual(signal.getPSKSignal(), result, M));
            
        }
    }

}
