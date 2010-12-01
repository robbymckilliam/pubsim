/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import pubsim.lattices.decoder.SphereDecoderSchnorrEuchner;
import pubsim.lattices.decoder.SphereDecoder;
import Jama.Matrix;
import java.util.Date;
import pubsim.lattices.GeneralLattice;
import pubsim.lattices.Lattice;
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
public class SphereDecoderSchnorrEuchnerTest {

    public SphereDecoderSchnorrEuchnerTest() {
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
    public void sameAsSphereDecoder() {
        System.out.println("sameAsSphereDecoder");

        SphereDecoder decoder = new SphereDecoder();
        SphereDecoder sdSE = new SphereDecoderSchnorrEuchner();

        int iters = 100;
        int n = 20;

        Lattice lattice = new GeneralLattice(Matrix.random(n, n));

        decoder.setLattice(lattice);
        sdSE.setLattice(lattice);

        for(int t = 0; t < iters; t++){

            double[] y = VectorFunctions.randomGaussian(n, 0.0, 100.0);
            decoder.nearestPoint(y);
            sdSE.nearestPoint(y);

            double decdist = VectorFunctions.distance_between2(sdSE.getLatticePoint(), decoder.getLatticePoint());

            System.out.println(decdist);

            assertTrue(decdist <= 0.000001);

        }
    }

    @Test
    public void speedTest() {
        System.out.println("sameAsSphereDecoder");

        SphereDecoder decoder = new SphereDecoderSchnorrEuchner();
        //SphereDecoder decoder = new SphereDecoder();

        int iters = 1000;
        int n = 30;

        Lattice lattice = new GeneralLattice(Matrix.random(n, n));

        decoder.setLattice(lattice);

        Date start = new Date();
        for(int t = 0; t < iters; t++){

            double[] y = VectorFunctions.randomGaussian(n, 0.0, 100.0);
            decoder.nearestPoint(y);
        }
        Date end = new Date();
        System.out.println( (end.getTime() - start.getTime())/1000.0 );
    }

}