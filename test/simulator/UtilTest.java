/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static simulator.Util.solveQuadratic;
import static simulator.Util.logGamma;

/**
 *
 * @author Robby
 */
public class UtilTest {

    public UtilTest() {
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
     * Test of mod method, of class Util.
     */
    @Test
    public void testMod() {
        System.out.println("mod");
        assertTrue(Util.mod(3, 2) == 1 );
        assertTrue(Util.mod(4, 2) == 0 );
        assertTrue(Util.mod(-1, 2) == 1 );
        assertTrue(Util.mod(-5, 2) == 1 );
        
        assertTrue(Util.mod(-1, 5) == 4 );

    }
    
    /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testFactorial() {
        System.out.println("factorial");
        assertTrue(Util.factorial(0) == 1 );
        assertTrue(Util.factorial(1) == 1 );
        assertTrue(Util.factorial(2) == 2 );
        assertTrue(Util.factorial(3) == 6 );
        assertTrue(Util.factorial(4) == 24 );


    }

    /**
     * Test of erf method, of class Util.
     */
    @Test
    public void testErf() {
        System.out.println("erf");
        double[] matlabErfResults = { 0.000011283791671, 0.112474087523181,
           0.222713430536666, 0.328637072037286, 0.428401970421195,
           0.520508665594897, 0.603863963235008, 0.677808106537633,
           0.742106914517924,
           0.796913232063397,
           0.842704943983178,
           0.880208434333047,
           0.910316651641024,
           0.934010026993453,
           0.952286709157474,
           0.966106335760364,
           0.976349255621274,
           0.983791085690519,
           0.989090943545010,
           0.992790734476862,
           0.995322471684673,
           0.997020670497287,
           0.998137242921606,
           0.998856880291507,
           0.999311521658988,
           0.999593069764853,
           0.999763978663631,
           0.999865674959099,
           0.999924991247290,
           0.999958904633937,
           0.999977910895490,
           0.999988352099242,
           0.999993974641812,
           0.999996942500569,
           0.999998478114283,
           0.999999256955620,
           0.999999644163552,
           0.999999832861734,
           0.999999923002115,
           0.999999965210549,
           0.999999984584012,
           0.999999993300537,
           0.999999997144752,
           0.999999998806633,
           0.999999999510873,
           0.999999999803402,
           0.999999999922511,
           0.999999999970050,
           0.999999999988649,
           0.999999999995781};
        
        double x = 0.00001;
        double acceptableError = 0.0000001;
        for(int i = 0; i < matlabErfResults.length; i++){
            assertTrue((Util.erf(x) - matlabErfResults[i]) < acceptableError);
            x += 0.1;
        }
        
        
    }

        /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testCircleIntersections() {
        System.out.println("circleIntersections");

        Point2 c1 = new Point2(0, 0);
        double r1 = 0.5;
        Point2 c2 = new Point2(1.0, 0);
        double r2 = 0.5;
        Point2[] ret = Util.circleIntersections(c1, r1, c2, r2);

        assertTrue(ret[0].equals(new Point2(0.5,0.0), 0.0000001));
        assertTrue(ret[1].equals(new Point2(0.5,0.0), 0.0000001));

        c1 = new Point2(0, 0);
        r1 = 2.0;
        c2 = new Point2(1.75, 0);
        r2 = 2.0;
        ret = Util.circleIntersections(c1, r1, c2, r2);

        //System.out.print(VectorFunctions.print(ret[0]));
        //System.out.print(VectorFunctions.print(ret[1]));

        assertTrue(ret[0].equals(new Point2(0.875,-1.80), 0.01));
        assertTrue(ret[1].equals(new Point2(0.875,1.80), 0.01));

    }

   /**
     * Test of mod method, of class Util.
     */
    @Test
    public void convertAtan2Angle() {
        System.out.println("convertAtan2Angle");

        assertEquals(1.7, Util.convertAtan2Angle(1.7), 0.0000001);
        assertEquals(Math.PI + 3*Math.PI/4.0, Util.convertAtan2Angle(-Math.PI/4.0), 0.0000001);

    }


    /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testRoundToHalfInt() {
        System.out.println("roundToHalfInt");

        assertEquals(1.5, Util.roundToHalfInt(1.7), 0.0000001);
        assertEquals(1.5, Util.roundToHalfInt(1.1), 0.0000001);
        assertEquals(-1.5, Util.roundToHalfInt(-1.1), 0.0000001);

    }

    /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testFloorToHalfInt() {
        System.out.println("floorToHalfInt");

        assertEquals(1.5, Util.floorToHalfInt(1.7), 0.0000001);
        assertEquals(1.5, Util.floorToHalfInt(2.1), 0.0000001);
        assertEquals(-2.5, Util.floorToHalfInt(-2.4), 0.0000001);
        assertEquals(-2.5, Util.floorToHalfInt(-1.6), 0.0000001);

    }

        /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testCeilToHalfInt() {
        System.out.println("ceilToHalfInt");

        assertEquals(2.5, Util.ceilToHalfInt(1.7), 0.0000001);
        assertEquals(2.5, Util.ceilToHalfInt(2.1), 0.0000001);
        assertEquals(-1.5, Util.ceilToHalfInt(-2.4), 0.0000001);
        assertEquals(-1.5, Util.ceilToHalfInt(-1.6), 0.0000001);

    }

    /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testsolveQuadratic() {
        System.out.println("ceilToHalfInt");

        double a, b, c;
        double[] test;

        a = 1; b = 0; c = -4;
        test = solveQuadratic(a, b, c);
        assertEquals(test[0], -2.0, 0.0000001);
        assertEquals(test[1], 2, 0.00000001);

        a = 1; b = 2; c = 1;
        test = solveQuadratic(a, b, c);
        assertEquals(test[0], -1.0, 0.0000001);
        assertEquals(test[1], -1.0, 0.00000001);

        a = 1; b = -2; c = 1;
        test = solveQuadratic(a, b, c);
        assertEquals(test[0], 1.0, 0.0000001);
        assertEquals(test[1], 1.0, 0.00000001);

    }

        /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testlog2() {
        System.out.println("testlog2");

        assertEquals(Util.log2(2), 1.0, 0.0000001);
        assertEquals(Util.log2(4), 2.0, 0.0000001);
        assertEquals(Util.log2(8), 3.0, 0.0000001);

    }

    /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testGamma() {
        System.out.println("testGamma");

        //compute Zadors bounds for vector quantisation
        for(int n = 1; n < 100; n++){
            double lower = Math.exp(2.0*logGamma(n/2.0 + 1.0)/n)/((n+2)*Math.PI);

            double upper = Math.exp(2.0*logGamma(n/2.0 + 1.0)/n + logGamma(2.0/n + 1.0))/n/Math.PI;

            System.out.println(n +" " + upper);
        }

    }

    /**
     * Test of mod method, of class Util.
     */
    @Test
    public void testHyperSohereVolume() {
        System.out.println("test hypersphere volume");

        assertEquals(2.0, Util.hyperSphereVolume(1), 0.0001);
        assertEquals(Math.PI, Util.hyperSphereVolume(2), 0.0001);
        assertEquals(4.18879, Util.hyperSphereVolume(3), 0.0001);
        assertEquals(4.93480, Util.hyperSphereVolume(4), 0.0001);
        assertEquals(5.26379, Util.hyperSphereVolume(5), 0.0001);
        assertEquals(5.16771, Util.hyperSphereVolume(6), 0.0001);
        assertEquals(4.72477, Util.hyperSphereVolume(7), 0.0001);
        assertEquals(4.05871, Util.hyperSphereVolume(8), 0.0001);

    }
}