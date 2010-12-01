/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder.banded;

import Jama.Matrix;
import pubsim.lattices.GeneralLattice;
import pubsim.lattices.decoder.SphereDecoder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class ConstrainedSphereDecoder2Test {

    public ConstrainedSphereDecoder2Test() {
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
     * Test of getNonNullIndices method, of class ConstrainedSphereDecoder2.
     */
    @Test
    public void testGetNonNullIndices() {
        System.out.println("getNonNullIndices");
         Double[] d = new Double[4];
        d[1] = new Double(1.0);
        d[3] = new Double(2.0);
        int[] result = ConstrainedSphereDecoder2.getNonNullIndices(d);
        assertTrue(result.length == 2);
        assertEquals(result[0], 1);
        assertEquals(result[1], 3);
    }

    /**
     * Test of getNonNullIndices method, of class ConstrainedSphereDecoder2.
     */
    @Test
    public void testGetNullIndices() {
        System.out.println("getNullIndices");
         Double[] d = new Double[5];
        d[1] = new Double(1.0);
        d[3] = new Double(2.0);
        int[] result = ConstrainedSphereDecoder2.getNullIndices(d);
        assertTrue(result.length == 3);
        assertEquals(result[0], 0);
        assertEquals(result[1], 2);
        assertEquals(result[2], 4);
    }

    /**
     * Test of stripNulls method, of class ConstrainedSphereDecoder2.
     */
    @Test
    public void testStripNulls() {
        System.out.println("stripNulls");
        Double[] d = new Double[3];
        d[1] = new Double(1.0);
        double[] result = ConstrainedSphereDecoder2.stripNulls(d);
        assertTrue(result.length == 1);
        assertEquals(d[1].doubleValue(), result[0], 0.0000000001);
    }

    /**
     * Test of countNull method, of class ConstrainedSphereDecoder2.
     */
    @Test
    public void testCountNull() {
        System.out.println("countNull");
        Double[] d = new Double[4];
        d[1] = new Double(1.0);
        d[3] = new Double(2.0);
        int result = ConstrainedSphereDecoder2.countNull(d);
        assertTrue(result == 2);
    }

    /**
     * Test of countNonNull method, of class ConstrainedSphereDecoder2.
     */
    @Test
    public void testCountNonNull() {
        System.out.println("countNonNull");
        Double[] d = new Double[5];
        d[1] = new Double(1.0);
        d[3] = new Double(2.0);
        int result = ConstrainedSphereDecoder2.countNull(d);
        assertTrue(result == 3);
    }

    /**
     * Test of countNonNull method, of class ConstrainedSphereDecoder2.
     */
    @Test
    public void testcopyIntoConstraints() {
        System.out.println("copyIntoConstraints");
        Double[] d = new Double[5];
        d[1] = new Double(1.0);
        d[3] = new Double(3.0);
        double[] v = new double[3];
        v[0] = 0.0; v[1] = 2.0; v[2] = 4.0;
        double[] u = new double[5];
        ConstrainedSphereDecoder2.copyIntoConstraints(d, v, u);
        for(int i = 0; i < d.length; i++)
            assertEquals(u[i], (double)i, 0.00001);
    }

    @Test
    public void returnsErrorWhenDimensionAreWrong() {
        System.out.println("returnsErrorWhenDimensionAreWrong");
        double[] y = {1, 2, 3, 4};
        GeneralLattice lattice = new GeneralLattice(Matrix.random(6, 5));
        Double[] c = new Double[5];
        ConstrainedSphereDecoder2 decoder =
                new ConstrainedSphereDecoder2(lattice, c, 1.0);

        boolean caught = false;
        try{
            decoder.nearestPoint(y);
        } catch(RuntimeException e){
            caught = true;
        }

        assertTrue(caught);

        lattice = new GeneralLattice(Matrix.random(6, 4));
        c = new Double[5];

        caught = false;
        try{
            decoder = new ConstrainedSphereDecoder2(lattice, c, 1.0);
        } catch(RuntimeException e){
            caught = true;
        }

        assertTrue(caught);

    }

    @Test
    public void sameAsSphereDecoderWhenNoConstraints() {
        System.out.println("sameAsSphereDecoderWhenNoConstraints");

        int iters = 10;
        int n = 5;
        int m = 6;
        for(int t = 0; t < iters; t++){

            GeneralLattice lattice = new GeneralLattice(Matrix.random(m, n));

            SphereDecoder sd = new SphereDecoder(lattice);

            //just guessing radius here!
            ConstrainedSphereDecoder2 decoder =
                new ConstrainedSphereDecoder2(lattice, new Double[n], 2.0);

            double[] y = VectorFunctions.randomGaussian(m, 0.0, 1.0);
            decoder.nearestPoint(y);
            sd.nearestPoint(y);

            double decdist = VectorFunctions.distance_between2(y, decoder.getLatticePoint());
            double sddist = VectorFunctions.distance_between2(y, sd.getLatticePoint());

            //System.out.println(decdist);
            //System.out.println(babdist);

            assertEquals(decdist, sddist, 0.000001);

        }
    }



}