/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim;

import pubsim.EisensteinInteger;
import pubsim.Complex;
import pubsim.Point2;
import Jama.Matrix;
import java.util.Arrays;
import pubsim.lattices.Hexagonal;
import pubsim.lattices.util.PointInSphere;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class EisensteinIntegerTest {

    public EisensteinIntegerTest() {
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
     * Test of isUnit method, of class EisensteinInteger.
     */
    @Test
    public void testIsUnit() {
        System.out.println("isUnit");
        EisensteinInteger instance = new EisensteinInteger(1.0, 0.0);
        assertEquals(true, instance.isUnit());
        instance = new EisensteinInteger(0.5, Math.sqrt(3)/2.0);
        assertEquals(true, instance.isUnit());
        instance = new EisensteinInteger(2.0, Math.sqrt(3)/2.0);
        assertEquals(false, instance.isUnit());
    }

    /**
     * Test of toArray method, of class EisensteinInteger.
     */
    @Test
    public void testToArray() {
        System.out.println("toArray");
        EisensteinInteger instance = new EisensteinInteger(1.0, 0.0);
        double[] expResult = {1.0, 0.0};
        double[] result = EisensteinInteger.toArray(instance);
        VectorFunctionsTest.assertVectorsEqual(expResult, result);
    }

    /**
     * Test of fromArray method, of class EisensteinInteger.
     */
    @Test
    public void testFromArray() {
        System.out.println("fromArray");
        double[] a =  {1.0, 0.0};
        EisensteinInteger expResult= new EisensteinInteger(1.0, 0.0);
        EisensteinInteger result = EisensteinInteger.fromArray(a);

        assertTrue(expResult.equals(result));
    }

    /**
     * Test of fromArray method, of class EisensteinInteger.
     */
    @Test
    public void testFromMatrix() {
        System.out.println("fromMatrix");
        Point2 p = new Point2(1.0,0.0);
        EisensteinInteger expResult= new EisensteinInteger(1.0, 0.0);
        EisensteinInteger result = EisensteinInteger.fromMatrix(p);

        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class EisensteinInteger.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        EisensteinInteger instance = new EisensteinInteger(0.5, Math.sqrt(3)/2.0);

        //System.out.println(instance);

        assertTrue(instance.equals(new Complex(0.5, Math.sqrt(3)/2.0)));
        assertFalse(instance.equals(new Complex(-2.0, Math.sqrt(3)/2.0)));
    }

    /**
     * Test of isInteger method, of class EisensteinInteger.
     */
    @Test
    public void testIsInteger() {
        System.out.println("isInteger");
        assertFalse(EisensteinInteger.isInteger(new Complex(-2.0, Math.sqrt(3)/2.0)));
        assertTrue(EisensteinInteger.isInteger(new Complex(2.5, Math.sqrt(3)/2.0)));
    }

    /**
     * Test of divides method, of class EisensteinInteger.
     */
    @Test
    public void testDivides() {
        System.out.println("divides");
        EisensteinInteger a = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        EisensteinInteger b = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        assertTrue(EisensteinInteger.divides(a, b));

        a = new EisensteinInteger(1.5, Math.sqrt(3)/2.0);
        b = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        assertFalse(EisensteinInteger.divides(a, b));

        a = new EisensteinInteger(1.0, 0.0);
        b = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        assertTrue(EisensteinInteger.divides(a, b));

        a = new EisensteinInteger(-2.5, -Math.sqrt(3)/2.0);
        b = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        assertTrue(EisensteinInteger.divides(a, b));
    }

    /**
     * Test of factorise method, of class EisensteinInteger.
     */
    @Test
    public void testFactorise() {
        System.out.println("factorise");
        EisensteinInteger a = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        System.out.println(a.factorise());

        a = new EisensteinInteger(4.0, 0.0);
        System.out.println(a.factorise());

        a = new EisensteinInteger(8.0, 0.0);
        System.out.println(a.factorise());

        a = new EisensteinInteger(2.0, 0.0);
        System.out.println(a.factorise());

        //a = new EisensteinInteger(31.0 + 51/2.0, 51*Math.sqrt(3)/2.0);
        //System.out.println(a.factorise());

        //a = new EisensteinInteger(4.5, 33.7749907475931);
        //System.out.println(a.factorise());

        //a = new EisensteinInteger(-13.0, -29.444863728670914);
        //System.out.println(a.factorise());

    }

    /**
     * Test of factorise(EisensteinInteger[]) method, of class EisensteinInteger.
     */
    @Test
    public void testFactoriseWithArrayArg() {
        System.out.println("factorise");

        double maxmag = (new EisensteinInteger(31.0 + 51/2.0, 51*Math.sqrt(3)/2.0)).abs();
        double[] origin = {0.0,0.0};
        PointInSphere points = new PointInSphere(new Hexagonal(), maxmag, origin);
        int count = 0;
        for( Matrix p : points ) count++;
        EisensteinInteger[] ring = new EisensteinInteger[count];
        points = new PointInSphere(new Hexagonal(), maxmag, origin);
        count = 0;
        for( Matrix p : points ) {
            ring[count] = EisensteinInteger.fromMatrix(p);
            count++;
        }
        Arrays.sort(ring);

        EisensteinInteger a = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        System.out.println(a.factorise(ring));

        a = new EisensteinInteger(4.0, 0.0);
        System.out.println(a.factorise(ring));

        a = new EisensteinInteger(8.0, 0.0);
        System.out.println(a.factorise(ring));

        a = new EisensteinInteger(2.0, 0.0);
        System.out.println(a.factorise(ring));

        a = new EisensteinInteger(31.0 + 51/2.0, 51*Math.sqrt(3)/2.0);
        System.out.println(a.factorise(ring));

        a = new EisensteinInteger(4.5, 33.7749907475931);
        System.out.println(a.factorise(ring));

        a = new EisensteinInteger(-13.0, -29.444863728670914);
        System.out.println(a.factorise(ring));

    }

    /**
     * Test of factorise method, of class EisensteinInteger.
     */
    @Test
    public void testEquivalentIdeal() {
        System.out.println("equivalentIdeal");
        EisensteinInteger a = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        EisensteinInteger b = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        assertTrue(EisensteinInteger.equivalentIdeal(a, b));

        a = new EisensteinInteger(0.5, Math.sqrt(3)/2.0);
        b = new EisensteinInteger(1.0, 0.0);
        assertTrue(EisensteinInteger.equivalentIdeal(a, b));

        a = new EisensteinInteger(-0.5, Math.sqrt(3)/2.0);
        b = new EisensteinInteger(1.0, 0.0);
        assertTrue(EisensteinInteger.equivalentIdeal(a, b));

        a = new EisensteinInteger(-0.5, Math.sqrt(3)/2.0);
        b = new EisensteinInteger(0.5, Math.sqrt(3)/2.0);
        assertTrue(EisensteinInteger.equivalentIdeal(a, b));

        a = new EisensteinInteger(-0.5, Math.sqrt(3)/2.0);
        b = new EisensteinInteger(2.5, Math.sqrt(3)/2.0);
        assertFalse(EisensteinInteger.equivalentIdeal(a, b));


    }

}