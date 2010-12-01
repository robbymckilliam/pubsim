/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder.banded;

import pubsim.lattices.decoder.banded.ConstrainedSphereDecoder;
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
 * @author Robby McKilliam
 */
public class ConstrainedSphereDecoderTest {

    public ConstrainedSphereDecoderTest() {
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

    @Test
    public void returnsErrorWhenDimensionAreWrong() {
        System.out.println("returnsErrorWhenDimensionAreWrong");
        double[] y = {1, 2, 3, 4};
        GeneralLattice lattice = new GeneralLattice(Matrix.random(6, 5));
        Double[] c = new Double[5];
        ConstrainedSphereDecoder decoder =
                new ConstrainedSphereDecoder(lattice, c);

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
            decoder = new ConstrainedSphereDecoder(lattice, c);
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
            ConstrainedSphereDecoder decoder =
                new ConstrainedSphereDecoder(lattice);

            double[] y = VectorFunctions.randomGaussian(m, 0.0, 10.0);
            decoder.nearestPoint(y);
            sd.nearestPoint(y);

            double decdist = VectorFunctions.distance_between2(y, decoder.getLatticePoint());
            double sddist = VectorFunctions.distance_between2(y, sd.getLatticePoint());

            System.out.println(VectorFunctions.print(decoder.getIndex()));
            System.out.println(VectorFunctions.print(sd.getIndex()));

            assertEquals(sddist, decdist, 0.000001);

        }
    }

    @Test
    public void bothConstrainedOutputSame() {
        System.out.println("bothConstrainedOutputSame");

        //UNDER CONSTRUCTION.

        int iters = 10;
        int n = 5;
        int m = 6;
        Double[] c = new Double[n];
        c[0] = new Double(1.0);
        c[2] = new Double(2.0);
        for(int t = 0; t < iters; t++){

            GeneralLattice lattice = new GeneralLattice(Matrix.random(m, n));

            ConstrainedSphereDecoder decoder =
                new ConstrainedSphereDecoder(lattice, c);

            //just guessing radius here!
            ConstrainedSphereDecoder2 decoder2 =
                new ConstrainedSphereDecoder2(lattice, c, 10.0);

            double[] y = VectorFunctions.randomGaussian(m, 0.0, 1.0);
            decoder.nearestPoint(y);
            decoder2.nearestPoint(y);

            double decdist = VectorFunctions.distance_between2(y, decoder.getLatticePoint());
            double dec2dist = VectorFunctions.distance_between2(y, decoder2.getLatticePoint());

            System.out.println(VectorFunctions.print(decoder.getIndex()));
            System.out.println(VectorFunctions.print(decoder2.getIndex()));

            assertTrue(decdist <= dec2dist);

        }
    }

    

}