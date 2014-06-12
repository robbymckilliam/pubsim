/*
 * @author Robby McKilliam
 */

package pubsim.distributions.circular;

import flanagan.integration.Integration;
import flanagan.integration.IntegralFunction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
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
            double result = instance.noise();
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
        double v = instance.intrinsicVariance();
        assertEquals(1.0/12.0, v, 0.0001);

    }

    /**
     * Test of getNoise method, of class WrappedGaussianNoise.
     */
    @Test
    public void testCircularVariance() {
        System.out.println("testCircularVariance");
        VonMises instance = new VonMises(0.0,1);
        double circvar = instance.circularVariance();
        double calc = new CircularMeanVariance(instance).circularVariance();
        System.out.println(calc + ", " + circvar);
        assertEquals(calc, circvar, 0.00001);
    }
    
        /**
     * Test of getNoise method, of class WrappedGaussianNoise.
     */
    @Test
    public void testIndefininateIntegral() {
        System.out.println("test indefinite integral");
        final VonMises rv = new VonMises(0.2,2);
        
        assertEquals(1.0, rv.indefinteIntegralPDF(0.5) - rv.indefinteIntegralPDF(-0.5), 1e-10);
        
        System.out.println(rv.indefinteIntegralPDF(0.25));
        
        double min = -0.25, max = 0.25;
        double intval = new Integration(new IntegralFunction() {
            public double function(double x) {
                return rv.pdf(x);
            }
        }, min, max).gaussQuad(500);
        assertEquals(intval, rv.indefinteIntegralPDF(max) - rv.indefinteIntegralPDF(min), 1e-10);
        
        min = -0.1; max = 0.4;
        intval = new Integration(new IntegralFunction() {
            public double function(double x) {
                return rv.pdf(x);
            }
        }, min, max).gaussQuad(500);
        assertEquals(intval, rv.indefinteIntegralPDF(max) - rv.indefinteIntegralPDF(min), 1e-10);
        
    }

}