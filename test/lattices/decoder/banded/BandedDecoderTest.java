/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import Jama.Matrix;
import lattices.GeneralLattice;
import lattices.decoder.SphereDecoder;
import lattices.decoder.banded.BandedDecoder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class BandedDecoderTest {

    public BandedDecoderTest() {
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

        int iters = 10;
        int n = 7;

        int band = 3;

        for(int t = 0; t < iters; t++){

            Matrix M = VectorFunctions.randomBandedMatrix(n, band);
            GeneralLattice lattice = new GeneralLattice(M);

            SphereDecoder sd = new SphereDecoder(lattice);

            //just guessing radius here!
            BandedDecoder decoder = new BandedDecoder(lattice, band);

            double[] y = VectorFunctions.randomGaussian(n, 0.0, 1.0);
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