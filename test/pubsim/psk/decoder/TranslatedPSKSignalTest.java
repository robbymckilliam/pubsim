/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.psk.decoder;

import pubsim.psk.decoder.TranslatedPSKSignal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.Complex;
import pubsim.distributions.RealRandomVariable;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class TranslatedPSKSignalTest {

    public TranslatedPSKSignalTest() {
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
     * Test of generateReceivedSignal method, of class TranslatedPSKSignal.
     */
    @Test
    public void generateReceivedSignal() {
        System.out.println("generateReceivedSignal");
        double[] xr ={1.0, 2.0, 3.0};
        int M = 4;
        
        TranslatedPSKSignal instance = new TranslatedPSKSignal();
        instance.setM(4);
        instance.setChannel(-0.4326, -1.6656);
        instance.setPATSymbol(0.1,0.1);
        
        RealRandomVariable noise = new pubsim.distributions.UniformNoise(0.0, 0.0);
        instance.setNoiseGenerator(noise);  
               
        instance.setPSKSignal(xr);
        
        //Output taken from Matlab
        Complex[] expr = {  new Complex(1.6656 + 0.1, -0.4326 + 0.1), 
                            new Complex(0.4326 + 0.1, 1.6656 + 0.1), 
                            new Complex(-1.6656 + 0.1, 0.4326 + 0.1)};
        instance.generateReceivedSignal();
        
        System.out.println(VectorFunctions.print(instance.getReceivedSignal()));
        
        assertEquals(true, VectorFunctions.distance_between2(expr,instance.getReceivedSignal())<0.0001);
    }

}