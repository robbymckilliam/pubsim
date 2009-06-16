/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import Jama.Matrix;
import lattices.GeneralLattice;
import lattices.decoder.Babai;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class ConstrainedBabaiTest {

    public ConstrainedBabaiTest() {
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
    public void sameAsBabaiWhenNoConstraints() {
        System.out.println("sameAsBabaiWhenNoConstraints");

        int iters = 10;
        int n = 5;
        int m = 6;
        for(int t = 0; t < iters; t++){

            GeneralLattice lattice = new GeneralLattice(Matrix.random(m, n));

            Babai babai = new Babai(lattice);

            //just guessing radius here!
            ConstrainedBabai decoder =
                new ConstrainedBabai(lattice);

            double[] y = VectorFunctions.randomGaussian(m, 0.0, 1.0);
            decoder.nearestPoint(y);
            babai.nearestPoint(y);

            double decdist = VectorFunctions.distance_between2(y, decoder.getLatticePoint());
            double babdist = VectorFunctions.distance_between2(y, babai.getLatticePoint());

            System.out.println(VectorFunctions.print(decoder.getIndex()));
            System.out.println(VectorFunctions.print(babai.getIndex()));

            assertEquals(babdist, decdist, 0.000001);

        }
    }

}