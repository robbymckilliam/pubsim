/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions;

import pubsim.distributions.GaussianNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rngpack.RandomSeedable;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class GaussianNoiseTest {

    public GaussianNoiseTest() {
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


//    /**
//     * Test of setMean method, of class ProjectedNormalDistribution.
//     */
//    @Test
//    public void testplotPdf() {
//        //System.out.println("plotPdf");
//        double mean = 0.0;
//        double var = 1;
//        GaussianNoise instance = new GaussianNoise(mean, var);
//
//        double step = 0.001;
//        double intsum = 0.0;
//        int count = 0;
//        for (double x = -5; x <= 5; x += step) {
//            double pdf = instance.pdf(x);
//            System.out.println(x + "\t" + pdf);
//            intsum += pdf;
//        }
//        System.out.println(intsum*step);
//
//    }

    /**
     * Test of setMean method, of class ProjectedNormalDistribution.
     */
    @Test
    public void testcdf() {
        //System.out.println("plotPdf");
        double mean = 0.0;
        double var = 1;
        GaussianNoise instance = new GaussianNoise(mean, var);

        System.out.println(instance.cdf(0));
        System.out.println(instance.cdf(100000));
        System.out.println(instance.cdf(-100000));
        System.out.println(instance.cdf(1));
        System.out.println(instance.cdf(-1));

        assertEquals(instance.cdf(0), 0.5, 0.00001);     
        assertEquals(instance.cdf(100000), 1.0, 0.00001);       
        assertEquals(instance.cdf(-100000), 0.0, 0.00001);
        assertEquals(instance.cdf(1), 0.5 + 0.341, 0.001);
        assertEquals(instance.cdf(-1), 0.5 - 0.341, 0.001);

    }

}