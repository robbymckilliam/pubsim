/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions.circular;

import pubsim.distributions.GaussianNoise;
import pubsim.distributions.circular.WrappedGaussian;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static pubsim.Util.dround6;

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
     * Test of getNoise method, of class WrappedGaussian.
     */
    @Test
    public void testGetNoise() {
        System.out.println("getNoise");
        WrappedGaussian instance = new WrappedGaussian(Math.PI*0.5, 5);

        for(int i = 0; i < 50; i++){
            double result = instance.getNoise();
            assertTrue(result <= Math.PI);
            assertTrue(result >= -Math.PI);
        }

    }

    /**
     * Test of getpdf method, of class WrappedGaussian.
     */
    @Test
    public void testGetPdf() {
        System.out.println("testGetPdf");
        WrappedGaussian instance = new WrappedGaussian(0.0, 1);
        System.out.println(instance.pdf(-1.0));

    }

    /**
     * Test of wrapped variance method, of class WrappedGaussian.
     */
    @Test
    public void testWrappedVarianceSmall() {
        System.out.println("testWrappedVarianceSmall");
        double invar = 0.01;
        WrappedGaussian instance = new WrappedGaussian(0.0,invar);
        double v = instance.unwrappedVariance();
        assertEquals(invar, v, 0.0001);

    }

//    /**
//     * Test of setMean method, of class ProjectedNormalDistribution.
//     */
//    @Test
//    public void plotPdf() {
//        //System.out.println("plotPdf");
//        double mean = 0.0;
//        double var = 0.4;
//        WrappedGaussian.Mod1 instance = new WrappedGaussian.Mod1(mean, var);
//
//        double step = 0.01;
//        double intsum = 0.0;
//        int count = 0;
//        for (double x = -0.5; x <= 0.5; x += step) {
//            double pdf = instance.pdf(x);
//            System.out.println(dround6(x).toString().replace('E', 'e') + " " + dround6(pdf).toString().replace('E', 'e'));
//            intsum += pdf;
//            count++;
//        }
//
//        System.out.println(intsum*step);
//        System.out.println(instance.getWrappedVariance());
//
//    }

//    /**
//     * Test of setMean method, of class ProjectedNormalDistribution.
//     */
//    @Test
//    public void plotVariance() {
//        //System.out.println("plotPdf");
//        for (double var = 10 ; var >= 0.00001; var *= 0.8 ) {
//            WrappedGaussian instance = new WrappedGaussian(0.0,var);
//            double v = instance.getWrappedVariance();
//            System.out.println(var + ", " + v + ";");
//        }
//
//    }

}