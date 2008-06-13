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
 * @author robertm
 */
public class CoxeterNoncoherentRecieverTest {

    public CoxeterNoncoherentRecieverTest() {
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
     * Test of decode method, of class CoxeterNoncoherentReciever.
     */
    @Test
    public void decode() {
        System.out.println("decode");
        int iters = 100;
        int M = 4;
        int T = 11;
        
        CoxeterCodedPSKSignal signal = new CoxeterCodedPSKSignal();
        signal.setM(M);
        signal.setLength(T);
        signal.setChannel(1.0/Math.sqrt(2.0), 1.0/Math.sqrt(2.0));
        //signal.generateChannel();
        
        NoiseGenerator noise = new simulator.UniformNoise(0.0, 0.000001);
        signal.setNoiseGenerator(noise);  
               
        signal.generateReceivedSignal();
        
        //System.out.println(" recsig = " + VectorFunctions.print(signal.getReceivedSignal()));
        
        CoxeterNoncoherentReciever instance = new CoxeterNoncoherentReciever();
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
        
            assertTrue(!instance.codewordError(signal.getPSKSignal()));
            
        }
    }

    /**
     * Test of bitErrors method, of class CoxeterNoncoherentReciever.
     */
    @Test
    public void bitErrors() {
        System.out.println("bitErrors");
        double[] x = null;
        CoxeterNoncoherentReciever instance = new CoxeterNoncoherentReciever();
        int expResult = 0;
        int result = instance.bitErrors(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of symbolErrors method, of class CoxeterNoncoherentReciever.
     */
    @Test
    public void symbolErrors() {
        System.out.println("symbolErrors");
        double[] x = null;
        CoxeterNoncoherentReciever instance = new CoxeterNoncoherentReciever();
        int expResult = 0;
        int result = instance.symbolErrors(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of codewordError method, of class CoxeterNoncoherentReciever.
     */
    @Test
    public void codewordError() {
        System.out.println("codewordError");
        double[] x = null;
        CoxeterNoncoherentReciever instance = new CoxeterNoncoherentReciever();
        boolean expResult = false;
        boolean result = instance.codewordError(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}