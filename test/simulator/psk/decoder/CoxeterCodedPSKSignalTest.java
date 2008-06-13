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
import simulator.Complex;
import simulator.NoiseGenerator;
import simulator.VectorFunctions;
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
        
        CoxeterCodedPSKSignal instance = new CoxeterCodedPSKSignal();
        instance.setM(4);
        instance.setChannel(-0.4326, -1.6656);
        
        NoiseGenerator noise = new simulator.UniformNoise(0.0, 0.0);
        instance.setNoiseGenerator(noise); 
               
        instance.generatePSKSignal(7);
        
        double[] res = instance.getPSKSignal();
        
        System.out.println(VectorFunctions.print(res));
       
        int sum = (int)VectorFunctions.sum(res);
        
        System.out.println(sum);
        
        assertEquals(0, Util.mod(sum, M));

    }

}