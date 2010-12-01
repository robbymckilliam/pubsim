/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import pubsim.distributions.circular.VonMises;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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

        /**
     * Test of getNoise method, of class WrappedGaussianNoise.
     */
    @Test
    public void testGetPdf() {
        System.out.println("testGetPdf");
        VonMises instance = new VonMises(0.0, 0.0001);
        System.out.println(instance.pdf(0.0));

    }

    /**
     * Test of getNoise method, of class WrappedGaussianNoise.
     */
    @Test
    public void testWrappedVarianceSmall() {
        System.out.println("testWrappedVarianceSmall");
        double invar = 0.0001;
        VonMises instance = new VonMises(0.0,invar);
        double v = instance.unwrappedVariance();
        assertEquals(1.0/12.0, v, 0.0001);

    }

}