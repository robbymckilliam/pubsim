/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions.discrete;

import pubsim.distributions.discrete.GeometricRandomVariable;
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
     * Test of getNoise method, of class GeometricRandomVariable.
     */
    @Test
    public void testGetNoise() {
        //System.out.println("getNoise");
        double p = 0.6;
        int n = 1000000;
        GeometricRandomVariable instance = new GeometricRandomVariable(p);
        double var = 0;
        double mean = 0;
        for(int i = 0; i < n; i++){
            double s  = instance.getNoise();
            //System.out.println(instance.getNoise());
            mean += s;
            double d = s - 1/p;
            var += d*d;
        }
        var /= n;
        mean /= n;
        assertEquals((1 - p)/(p*p), var, 0.05);
        assertEquals(1/p, mean, 0.01);
    }

}