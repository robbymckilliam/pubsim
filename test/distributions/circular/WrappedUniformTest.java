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
import static simulator.Util.dround6;

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

        /**
     * Test of getWrappedVariance method, of class WrappedUniform.
     */
    @Test
    public void testRange() {
        System.out.println("testRange");
        double range = 0.2;
        double DELTA = 0.00001;
        WrappedUniform.Mod1 instance = new WrappedUniform.Mod1(0.0, 1.0/12.0);
        instance.setRange(0.2);
        assertEquals(0.0, instance.pdf(range/2 + DELTA), 0.0000001);
        assertEquals(0.0, instance.pdf(-range/2 - DELTA), 0.0000001);
        assertEquals(1/0.2, instance.pdf(-range/2 + DELTA), 0.0000001);
        assertEquals(1/0.2, instance.pdf(range/2 - DELTA), 0.0000001);

        assertEquals(instance.getVariance(), instance.getWrappedVariance(), 0.0000001);


    }


    
//
//    /**
//     * Test of setMean method, of class ProjectedNormalDistribution.
//     */
//    @Test
//    public void plotPdf() {
//        //System.out.println("plotPdf");
//        double mean = 0.0;
//        double var = 0.1;
//        WrappedUniform.Mod1 instance = new WrappedUniform.Mod1(mean, var);
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

}