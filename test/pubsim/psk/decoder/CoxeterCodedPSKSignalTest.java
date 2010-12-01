/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.psk.decoder;

import pubsim.psk.decoder.Util;
import pubsim.psk.decoder.CoxeterCodedPSKSignal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.Complex;
import pubsim.distributions.RandomVariable;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class CoxeterCodedPSKSignalTest {

    public CoxeterCodedPSKSignalTest() {
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
     * Test of generatePSKSignal method, of class CoxeterCodedPSKSignal.
     */
    @Test
    public void generatePSKSignal() {
        System.out.println("generatePSKSignal");
        
        int M = 4;
        int k = 5;
        
        CoxeterCodedPSKSignal instance = new CoxeterCodedPSKSignal(M,k);
        instance.setM(4);
        instance.setLength(32);
        instance.setChannel(-0.4326, -1.6656);
        
        RandomVariable noise = new pubsim.distributions.UniformNoise(0.0, 0.0);
        instance.setNoiseGenerator(noise); 
               
        instance.generatePSKSignal();
        
        double[] res = instance.getPSKSignal();
        
        System.out.println(VectorFunctions.print(res));
       
        int sum = (int)VectorFunctions.sum(res);
        
        System.out.println(sum);
        
        assertEquals(0, Util.mod(sum, k*M - k + 1));

    }

}