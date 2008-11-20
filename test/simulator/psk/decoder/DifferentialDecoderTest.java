/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.NoiseGenerator;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class DifferentialDecoderTest {

    public DifferentialDecoderTest() {
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
     * Test of decode method, of class DifferentialDecoder.
     */
    @Test
    public void decode() {
        System.out.println("decode");
        int iters = 100;
        int M = 8;
        int T = 10;
        
        PSKSignal signal = new PSKSignal();
        signal.setM(M);
        signal.setLength(T);
        //signal.setChannel(-1.0,-1.0);
        signal.generateChannel();
        
        NoiseGenerator noise = new distributions.UniformNoise(0.0, 0.00001);
        signal.setNoiseGenerator(noise);  
               
        signal.generateReceivedSignal();
        
        //System.out.println(" recsig = " + VectorFunctions.print(signal.getReceivedSignal()));
        
        DifferentialDecoder instance = new DifferentialDecoder();
        instance.setM(M);
        instance.setT(T);
        
        for(int i = 0; i < iters; i++){
            signal.generateChannel();
            signal.generatePSKSignal();
            signal.generateReceivedSignal();
            
            double[] result = instance.decode(signal.getReceivedSignal());
            
            System.out.println(" result = " + VectorFunctions.print(result));
            System.out.println(" expect = " + VectorFunctions.print(signal.getPSKSignal()));
        
            assertTrue(Util.differentialEncodedEqual(signal.getPSKSignal(), result, M));
            
        }
    }

}