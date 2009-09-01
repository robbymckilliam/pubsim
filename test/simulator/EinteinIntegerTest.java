/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import Jama.Matrix;
import java.util.Arrays;
import lattices.Hexagonal;
import lattices.util.PointInSphere;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class EinteinIntegerTest {

    public EinteinIntegerTest() {
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
     * Test of isUnit method, of class EinteinInteger.
     */
    @Test
    public void testIsUnit() {
        System.out.println("isUnit");
        EinteinInteger instance = new EinteinInteger(1.0, 0.0);
        assertEquals(true, instance.isUnit());
        instance = new EinteinInteger(0.5, Math.sqrt(3)/2.0);
        assertEquals(true, instance.isUnit());
        instance = new EinteinInteger(2.0, Math.sqrt(3)/2.0);
        assertEquals(false, instance.isUnit());
    }

    /**
     * Test of toArray method, of class EinteinInteger.
     */
    @Test
    public void testToArray() {
        System.out.println("toArray");
        EinteinInteger instance = new EinteinInteger(1.0, 0.0);
        double[] expResult = {1.0, 0.0};
        double[] result = EinteinInteger.toArray(instance);
        VectorFunctionsTest.assertVectorsEqual(expResult, result);
    }

    /**
     * Test of fromArray method, of class EinteinInteger.
     */
    @Test
    public void testFromArray() {
        System.out.println("fromArray");
        double[] a =  {1.0, 0.0};
        EinteinInteger expResult= new EinteinInteger(1.0, 0.0);
        EinteinInteger result = EinteinInteger.fromArray(a);

        assertTrue(expResult.equals(result));
    }

    /**
     * Test of fromArray method, of class EinteinInteger.
     */
    @Test
    public void testFromMatrix() {
        System.out.println("fromMatrix");
        Point2 p = new Point2(1.0,0.0);
        EinteinInteger expResult= new EinteinInteger(1.0, 0.0);
        EinteinInteger result = EinteinInteger.fromMatrix(p);

        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class EinteinInteger.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        EinteinInteger instance = new EinteinInteger(0.5, Math.sqrt(3)/2.0);

        //System.out.println(instance);

        assertTrue(instance.equals(new Complex(0.5, Math.sqrt(3)/2.0)));
        assertFalse(instance.equals(new Complex(-2.0, Math.sqrt(3)/2.0)));
    }

    /**
     * Test of isInteger method, of class EinteinInteger.
     */
    @Test
    public void testIsInteger() {
        System.out.println("isInteger");
        assertFalse(EinteinInteger.isInteger(new Complex(-2.0, Math.sqrt(3)/2.0)));
        assertTrue(EinteinInteger.isInteger(new Complex(2.5, Math.sqrt(3)/2.0)));
    }

    /**
     * Test of divides method, of class EinteinInteger.
     */
    @Test
    public void testDivides() {
        System.out.println("divides");
        EinteinInteger a = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        EinteinInteger b = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        assertTrue(EinteinInteger.divides(a, b));

        a = new EinteinInteger(1.5, Math.sqrt(3)/2.0);
        b = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        assertFalse(EinteinInteger.divides(a, b));

        a = new EinteinInteger(1.0, 0.0);
        b = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        assertTrue(EinteinInteger.divides(a, b));

        a = new EinteinInteger(-2.5, -Math.sqrt(3)/2.0);
        b = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        assertTrue(EinteinInteger.divides(a, b));
    }

    /**
     * Test of factorise method, of class EinteinInteger.
     */
    @Test
    public void testFactorise() {
        System.out.println("factorise");
        EinteinInteger a = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        System.out.println(a.factorise());

        a = new EinteinInteger(4.0, 0.0);
        System.out.println(a.factorise());

        a = new EinteinInteger(8.0, 0.0);
        System.out.println(a.factorise());

        a = new EinteinInteger(2.0, 0.0);
        System.out.println(a.factorise());

        //a = new EinteinInteger(31.0 + 51/2.0, 51*Math.sqrt(3)/2.0);
        //System.out.println(a.factorise());

        //a = new EinteinInteger(4.5, 33.7749907475931);
        //System.out.println(a.factorise());

        //a = new EinteinInteger(-13.0, -29.444863728670914);
        //System.out.println(a.factorise());

    }

    /**
     * Test of factorise(EinteinInteger[]) method, of class EinteinInteger.
     */
    @Test
    public void testFactoriseWithArrayArg() {
        System.out.println("factorise");

        double maxmag = (new EinteinInteger(31.0 + 51/2.0, 51*Math.sqrt(3)/2.0)).abs();
        double[] origin = {0.0,0.0};
        PointInSphere points = new PointInSphere(new Hexagonal(), maxmag, origin);
        int count = 0;
        for( Matrix p : points ) count++;
        EinteinInteger[] ring = new EinteinInteger[count];
        points = new PointInSphere(new Hexagonal(), maxmag, origin);
        count = 0;
        for( Matrix p : points ) {
            ring[count] = EinteinInteger.fromMatrix(p);
            count++;
        }
        Arrays.sort(ring);

        EinteinInteger a = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        System.out.println(a.factorise(ring));

        a = new EinteinInteger(4.0, 0.0);
        System.out.println(a.factorise(ring));

        a = new EinteinInteger(8.0, 0.0);
        System.out.println(a.factorise(ring));

        a = new EinteinInteger(2.0, 0.0);
        System.out.println(a.factorise(ring));

        a = new EinteinInteger(31.0 + 51/2.0, 51*Math.sqrt(3)/2.0);
        System.out.println(a.factorise(ring));

        a = new EinteinInteger(4.5, 33.7749907475931);
        System.out.println(a.factorise(ring));

        a = new EinteinInteger(-13.0, -29.444863728670914);
        System.out.println(a.factorise(ring));

    }

    /**
     * Test of factorise method, of class EinteinInteger.
     */
    @Test
    public void testEquivalentIdeal() {
        System.out.println("equivalentIdeal");
        EinteinInteger a = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        EinteinInteger b = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        assertTrue(EinteinInteger.equivalentIdeal(a, b));

        a = new EinteinInteger(0.5, Math.sqrt(3)/2.0);
        b = new EinteinInteger(1.0, 0.0);
        assertTrue(EinteinInteger.equivalentIdeal(a, b));

        a = new EinteinInteger(-0.5, Math.sqrt(3)/2.0);
        b = new EinteinInteger(1.0, 0.0);
        assertTrue(EinteinInteger.equivalentIdeal(a, b));

        a = new EinteinInteger(-0.5, Math.sqrt(3)/2.0);
        b = new EinteinInteger(0.5, Math.sqrt(3)/2.0);
        assertTrue(EinteinInteger.equivalentIdeal(a, b));

        a = new EinteinInteger(-0.5, Math.sqrt(3)/2.0);
        b = new EinteinInteger(2.5, Math.sqrt(3)/2.0);
        assertFalse(EinteinInteger.equivalentIdeal(a, b));


    }

}