/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.NoiseGenerator;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class VonMisesTest {

    public VonMisesTest() {
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
     * Test of getNoise method, of class WrappedGaussianNoise.
     */
    @Test
    public void testGetNoise() {
        System.out.println("getNoise");
        VonMises instance = new VonMises(Math.PI*0.5, 5);
        
        for(int i = 0; i < 50; i++){
            double result = instance.getNoise();
            assertTrue(result <= Math.PI);
            assertTrue(result >= -Math.PI);
        }
    }

}