/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import distributions.GaussianNoise;
import distributions.circular.WrappedGaussianNoise;
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
public class WrappedGaussianNoiseTest {

    public WrappedGaussianNoiseTest() {
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
        WrappedGaussianNoise instance = new WrappedGaussianNoise(Math.PI*0.5, 5);
        
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
        WrappedGaussianNoise instance = new WrappedGaussianNoise(0.0, 1);
        System.out.println(instance.pdf(-1.0));

    }

    /**
     * Test of getNoise method, of class WrappedGaussianNoise.
     */
    @Test
    public void testWrappedVarianceSmall() {
        System.out.println("testWrappedVarianceSmall");
        double invar = 0.01;
        WrappedGaussianNoise instance = new WrappedGaussianNoise(0.0,invar);
        double v = instance.getWrappedVariance();
        assertEquals(invar, v, 0.0001);

    }
}