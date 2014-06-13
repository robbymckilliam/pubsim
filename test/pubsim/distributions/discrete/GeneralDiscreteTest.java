/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.discrete;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;

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
        GeneralDiscreteRandomVariable dist = new GeneralDiscreteRandomVariable(pdf);
        assertEquals(2.0, dist.mean(), 0.000001);
        assertEquals(1.0, dist.variance(), 0.000001);
    }

    /**
     * Test of getVariance method, of class GeneralDiscrete.
     */
    @Test
    public void testpdf() {
        System.out.println("test pdf");
        Map<Integer, Double> pdf = new HashMap<Integer, Double>(2);
        pdf.put(1, 0.5); pdf.put(3, 0.5);
        GeneralDiscreteRandomVariable dist = new GeneralDiscreteRandomVariable(pdf);
        assertEquals(0.5, dist.pmf(1), 0.000001);
        assertEquals(0.5, dist.pmf(3), 0.000001);
        assertEquals(0, dist.pmf(2), 0.000001);
        assertEquals(0, dist.pmf(5), 0.000001);
        assertEquals(0, dist.pmf(4), 0.000001);
        assertEquals(0, dist.pmf(0), 0.000001);
    }
    
    /**
     * Test of getVariance method, of class GeneralDiscrete.
     */
    @Test
    public void testcdf() {
        System.out.println("test cdf");
        Map<Integer, Double> pdf = new HashMap<Integer, Double>(2);
        pdf.put(1, 0.5); pdf.put(3, 0.5);
        GeneralDiscreteRandomVariable dist = new GeneralDiscreteRandomVariable(pdf);
        assertEquals(0.5, dist.cmf(1), 0.000001);
        assertEquals(1.0, dist.cmf(3), 0.000001);
        assertEquals(0.5, dist.cmf(2), 0.000001);
        assertEquals(1.0, dist.cmf(5), 0.000001);
        assertEquals(1.0, dist.cmf(4), 0.000001);
        assertEquals(0, dist.cmf(0), 0.000001);
    }


    /**
     * Test of getVariance method, of class GeneralDiscrete.
     */
    @Test
    public void testnoise() {
        System.out.println("test noise");
        Map<Integer, Double> pdf = new HashMap<Integer, Double>(2);
        pdf.put(1, 0.7); pdf.put(3, 0.3);
        GeneralDiscreteRandomVariable dist = new GeneralDiscreteRandomVariable(pdf);

        int iters = 100000;
        double count1s = 0; double count3s = 0;
        for(int i = 0; i < iters; i++){
            int n = (int)Math.round(dist.noise());
            if(n == 1) count1s++;
            else if (n == 3) count3s++;
            else fail("noise returned a value of zero probability!");
        }
        assertEquals(0.7, count1s/iters, 0.01);
        assertEquals(0.3, count3s/iters, 0.01);
    }

}