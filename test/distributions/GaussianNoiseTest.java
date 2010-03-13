/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions;

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


    /**
     * Test of setMean method, of class ProjectedNormalDistribution.
     */
    @Test
    public void testplotPdf() {
        //System.out.println("plotPdf");
        double mean = 0.0;
        double var = 1;
        GaussianNoise instance = new GaussianNoise(mean, var);

        double step = 0.001;
        double intsum = 0.0;
        int count = 0;
        for (double x = -5; x <= 5; x += step) {
            double pdf = instance.pdf(x);
            System.out.println(x + "\t" + pdf);
            intsum += pdf;
        }
        System.out.println(intsum*step);

    }

}