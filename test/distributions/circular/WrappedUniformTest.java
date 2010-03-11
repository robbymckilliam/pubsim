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
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class WrappedUniformTest {

    public WrappedUniformTest() {
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
     * Test of pdf method, of class WrappedUniform.
     */
    @Test
    public void testPdf() {
        System.out.println("pdf");
        CircularDistribution instance = new WrappedUniform.Mod1(0.0, 1.0/11.0);
        assertTrue(instance.pdf(0) < 1.0);
        System.out.println(instance.pdf(0));
        assertTrue(instance.pdf(-0.5) > 1.0);
        System.out.println(instance.pdf(-0.5));
        assertTrue(instance.pdf(0.5) > 1.0);
        System.out.println(instance.pdf(0.5));
    }

    /**
     * Test of getWrappedVariance method, of class WrappedUniform.
     */
    @Test
    public void testGetWrappedVariance() {
        System.out.println("getWrappedVariance");
        CircularDistribution instance = new WrappedUniform.Mod1(0.0, 1.0/12.0);
        assertEquals(1.0/12.0, instance.getWrappedVariance(), 0.001);

        instance = new WrappedUniform.Mod1(0.0, 1.0/11.0);
        double result = instance.getWrappedVariance();
        System.out.println(result);
        assertTrue(1.0/12.0 < result);
        assertTrue(1.0/11.0 > result);
    }

}