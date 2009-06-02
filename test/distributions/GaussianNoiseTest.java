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
     * Test of getNoise method, of class GaussianNoise.
     */
    @Test
    public void testGetNoise() {
        System.out.println("getNoise");
        GaussianNoise instance = new GaussianNoise();
        instance.setSeed(RandomSeedable.ClockSeed());

        int iters = 1000000;
        double rvar = 0.0;
        double rmean = 0.0;
        for(int i = 1; i < iters; i++){
            double n = instance.getNoise();
            rvar += n*n;
            rmean += n;
        }
        rvar /= iters;
        rmean /= iters;
        double expvar = 1.0;
        double expmean = 0.0;
        assertEquals(expvar, rvar, 2*1.0/Math.sqrt(iters));
        assertEquals(expmean, rmean, Math.sqrt(2*1.0/Math.sqrt(iters)));
    }

}