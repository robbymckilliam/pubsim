/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions.discrete;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class GeneralDiscreteTest {

    public GeneralDiscreteTest() {
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
     * Test of getVariance method, of class GeneralDiscrete.
     */
    @Test
    public void testmeanandvar() {
        System.out.println("test mean and variance");
        Map<Integer, Double> pdf = new HashMap<Integer, Double>(2);
        pdf.put(1, 0.5); pdf.put(3, 0.5);
        GeneralDiscrete dist = new GeneralDiscrete(pdf);
        assertEquals(2.0, dist.getMean(), 0.000001);
        assertEquals(1.0, dist.getVariance(), 0.000001);
    }

    /**
     * Test of getVariance method, of class GeneralDiscrete.
     */
    @Test
    public void testpdf() {
        System.out.println("test pdf");
        Map<Integer, Double> pdf = new HashMap<Integer, Double>(2);
        pdf.put(1, 0.5); pdf.put(3, 0.5);
        GeneralDiscrete dist = new GeneralDiscrete(pdf);
        assertEquals(0.5, dist.pdf(1), 0.000001);
        assertEquals(0.5, dist.pdf(3), 0.000001);
        assertEquals(0, dist.pdf(2), 0.000001);
        assertEquals(0, dist.pdf(5), 0.000001);
        assertEquals(0, dist.pdf(4), 0.000001);
        assertEquals(0, dist.pdf(0), 0.000001);
    }
    
    /**
     * Test of getVariance method, of class GeneralDiscrete.
     */
    @Test
    public void testcdf() {
        System.out.println("test cdf");
        Map<Integer, Double> pdf = new HashMap<Integer, Double>(2);
        pdf.put(1, 0.5); pdf.put(3, 0.5);
        GeneralDiscrete dist = new GeneralDiscrete(pdf);
        assertEquals(0.5, dist.cdf(1), 0.000001);
        assertEquals(1.0, dist.cdf(3), 0.000001);
        assertEquals(0.5, dist.cdf(2), 0.000001);
        assertEquals(1.0, dist.cdf(5), 0.000001);
        assertEquals(1.0, dist.cdf(4), 0.000001);
        assertEquals(0, dist.cdf(0), 0.000001);
    }


    /**
     * Test of getVariance method, of class GeneralDiscrete.
     */
    @Test
    public void testnoise() {
        System.out.println("test noise");
        Map<Integer, Double> pdf = new HashMap<Integer, Double>(2);
        pdf.put(1, 0.7); pdf.put(3, 0.3);
        GeneralDiscrete dist = new GeneralDiscrete(pdf);

        int iters = 100000;
        double count1s = 0; double count3s = 0;
        for(int i = 0; i < iters; i++){
            int n = (int)Math.round(dist.getNoise());
            if(n == 1) count1s++;
            else if (n == 3) count3s++;
            else fail("noise returned a value of zero probability!");
        }
        assertEquals(0.7, count1s/iters, 0.01);
        assertEquals(0.3, count3s/iters, 0.01);
    }

}