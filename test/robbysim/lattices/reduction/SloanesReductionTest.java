/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.reduction;

import robbysim.lattices.reduction.SloanesReduction;
import Jama.Matrix;
import robbysim.distributions.GaussianNoise;
import robbysim.lattices.leech.Leech;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import robbysim.VectorFunctions;

/**
 *
 * @author harprobey
 */
public class SloanesReductionTest {

    public SloanesReductionTest() {
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
    public void testUpperTriangularBasis() {
        System.out.println("test upper triangular basis");
        int n = 4;
        Matrix B = Matrix.random(n, n);
        Matrix L = SloanesReduction.upperTriangularBasis(B);
        System.out.println(VectorFunctions.print(L));
    }

    @Test
    public void testLwMatrix() {
        System.out.println("test Lw matrix");
        int n = 4;
        Matrix B = Matrix.random(n, n);
        Matrix L = SloanesReduction.upperTriangularBasis(B);
        Matrix Lw = SloanesReduction.computeSlonesLw(L, 10);
        System.out.println(VectorFunctions.print(Lw));
    }

    @Test
    public void testSpecialColReduce() {
        System.out.println("test special column reduce");
        int n = 4;
        Matrix B = Matrix.random(n, n);
        Matrix L = SloanesReduction.upperTriangularBasis(B);
        Matrix Lw = SloanesReduction.computeSlonesLw(L, 5);
        Matrix Lt = SloanesReduction.specialColumnReduce(Lw);
        System.out.println(VectorFunctions.print(Lt));
    }

    @Test
    public void testGetProjectionVector() {
        System.out.println("test get projection vector");
        int n = 24;
        Matrix B = VectorFunctions.randomMatrix(n, n, new GaussianNoise(0, 1.0));
        Matrix L = SloanesReduction.upperTriangularBasis(B);
        Matrix Lw = SloanesReduction.computeSlonesLw(L, 1);
        Matrix Lt = SloanesReduction.specialColumnReduce(Lw);
        System.out.println(VectorFunctions.print(Lw));

        double[] v = new SloanesReduction(L,1).getProjectionVector();
        System.out.println(VectorFunctions.print(v));

    }

    @Test
    public void testOnLeechLattice() {
        System.out.println("test on Leech lattice");
        Matrix M = new Leech().getGeneratorMatrix().times(Math.sqrt(8.0));
        System.out.println(VectorFunctions.print(M));
        Matrix L = SloanesReduction.upperTriangularBasis(M);
        Matrix Lw = SloanesReduction.computeSlonesLw(L, 1);
        Matrix Lt = SloanesReduction.specialColumnReduce(Lw);
        System.out.println(VectorFunctions.print(Lw));
        double[] v = new SloanesReduction(M,1).getProjectionVector();
        System.out.println(VectorFunctions.print(v));

    }


}