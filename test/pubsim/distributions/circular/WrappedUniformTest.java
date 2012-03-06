/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import static org.junit.Assert.*;
import org.junit.*;
import static pubsim.Util.fracpart;

/**
 *
 * @author Robby McKilliam
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
        CircularRandomVariable instance = new WrappedUniform(0.0, 1.0/11.0);
        assertTrue(instance.pdf(0) < 1.0);
        assertTrue(instance.pdf(-0.5) > 1.0);
        assertTrue(instance.pdf(0.5) > 1.0);
    }

    /**
     * Test of getWrappedVariance method, of class WrappedUniform.
     */
    @Test
    public void testGetWrappedVariance() {
        System.out.println("getWrappedVariance");
        CircularRandomVariable instance = new WrappedUniform(0.0, 1.0/12.0);
        assertEquals(1.0/12.0, instance.unwrappedVariance(), 0.0001);
        instance = new WrappedUniform(0.0, 1.0/11.0);
        double result = instance.unwrappedVariance();
        assertTrue(1.0/12.0 > result);
    }
    
    @Test
    public void testNoise() {
        System.out.println("test noise generator");
        
         int iters = 100000;
        double mean = 0.2;
        double var = 1.0/11.0;
        CircularRandomVariable instance = new WrappedUniform(mean, var);
        
        double umean = instance.unwrappedMean();
        assertEquals(-0.3, umean, 0.000001);
        
        double sum = 0; double sum2 = 0;
        for( int i = 0; i <= iters; i++)
        {
            double noise = instance.getNoise();
            boolean result = noise >= -0.5 && noise < 0.5; 
            sum2 += fracpart(noise - umean)*fracpart(noise - umean);
            assertEquals(true, result);
        }
        
        //test the unwrapped variance
        assertTrue( Math.abs(sum2/iters - instance.unwrappedVariance()) < 0.01 );
        
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