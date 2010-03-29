/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.discrete;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class GeometricDistributionTest {

    public GeometricDistributionTest() {
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
     * Test of getNoise method, of class GeometricDistribution.
     */
    @Test
    public void testGetNoise() {
        //System.out.println("getNoise");
        double p = 0.8;
        int n = 1000000;
        GeometricDistribution instance = new GeometricDistribution(p);
        double var = 0;
        for(int i = 0; i < n; i++){
            double s  = instance.getNoise();
            //System.out.println(instance.getNoise());
            double d = s - 1/p;
            var += d*d;
        }
        var /= n;
        double expvar = (1 - p)/(p*p);
        assertEquals(expvar, var, 0.01);
    }

}