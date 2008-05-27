/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import junit.framework.TestCase;
import simulator.NoiseGenerator;
import simulator.VectorFunctions;
import simulator.psk.decoder.ZnLLS;
import lattices.*;

/**
 *
 * @author Tim
 */
public class ZnLLSTest extends TestCase {
    
    public ZnLLSTest(String testName) {
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
        
        int iters = 1000;
        int M = 4;
        int T = 9;
        
        PSKSignal signal = new PSKSignal();
        signal.setM(M);
        signal.setLength(T);
        //signal.setChannel(-1.0,-1.0);
        signal.generateChannel();
        
        NoiseGenerator noise = new simulator.UniformNoise(0.0, 0.0000001);
        signal.setNoiseGenerator(noise);  
               
        signal.generateReceivedSignal();
        
        //System.out.println(" recsig = " + VectorFunctions.print(signal.getReceivedSignal()));
        
        simulator.psk.decoder.ZnLLS instance = new simulator.psk.decoder.ZnLLS();
        instance.setM(M);
        instance.setT(T);
        
        for(int i = 0; i < iters; i++){
            signal.generateChannel();
            signal.generatePSKSignal();
            signal.generateReceivedSignal();
            
            double[] result = instance.decode(signal.getReceivedSignal());
            
            //System.out.println(" recsig = " + VectorFunctions.print(signal.getReceivedSignal()));
            //System.out.println(" actual = " + VectorFunctions.print(signal.getPSKSignal()));
            //System.out.println(" decoded = "+ VectorFunctions.print(result));
        
            assertEquals(true, Util.differentialEncodedEqual(signal.getPSKSignal(), result, M));
            
        }
    }

}
