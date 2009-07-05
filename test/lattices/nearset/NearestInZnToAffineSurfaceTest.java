/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import lattices.Phin2star.Phin2Star;
import lattices.Phin2star.Phin2StarZnLLS;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static simulator.VectorFunctions.randomGaussian;
import static simulator.VectorFunctions.columnMatrix;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctions.print;
import static simulator.VectorFunctions.distance_between;
import static simulator.VectorFunctions.distance_between2;

/**
 *
 * @author Robby McKilliam
 */
public class NearestInZnToAffineSurfaceTest {

    public NearestInZnToAffineSurfaceTest() {
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
     * Test of compute method, of class NearestInZnToAffineSurface.
     */
    @Test
    public void correctForLine() {
        System.out.println("correctForLine");

        int iters = 100;

        for( int i = 0; i < iters; i++){
            int N = 10;
            RegionForLines R = new NSphereForLines(100, randomGaussian(N));
            double[] m = randomGaussian(N);
            double[] c = randomGaussian(N);

            NearestInZnToLine tester = new NearestInZnToLine(N);
            tester.compute(c, columnMatrix(m), R);

            NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(N);
            inst.compute(c, columnMatrix(m), R);

            assertEquals(tester.nearestParams()[0], inst.nearestParams()[0], 0.0000001);
            double[] utest = tester.nearestPoint();
            double[] uinst = inst.nearestPoint();
            for(int n = 0; n < N; n++){
                assertEquals(utest[n], uinst[n], 0.00000001);
            }
        }
    }

    /**
     * Test of compute method, of class NearestInZnToAffineSurface.
     */
    @Test
    public void returnZeroForPlaneThroughOrigin() {
        System.out.println("returnZeroForPlaneThroughOrigin");

        //int iters = 100;

       // for( int i = 0; i < iters; i++){
            int N = 20;
            RegionForLines R = new NSphereForLines(5, randomGaussian(N));
            Matrix P = Matrix.random(N, 2);
            double[] c = new double[N];

            NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(N);
            inst.compute(c, P, R);

            assertEquals(0.0, inst.nearestParams()[0], 0.0000001);
            assertEquals(0.0, inst.nearestParams()[1], 0.0000001);
        //}
    }

    /**
     * Test of compute method, of class NearestInZnToAffineSurface.
     */
    @Test
    public void sameAsFrequencyEstimationLattice() {
        System.out.println("sameAsFrequencyEstimationLattice");

        //int iters = 100;
        int N = 10;
        Matrix P = Phin2Star.getMMatrix(N-2);
        RegionForLines R = new ParallelepipedForLines(P.times(1));
        Matrix Pt = P.transpose();
        Matrix K = (Pt.times(P)).inverse().times(Pt);
        Matrix G = Matrix.identity(N, N).minus(P.times(K));

        NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(N);
        Phin2Star test = new Phin2StarZnLLS(N-2);

        double[] c = randomGaussian(N, 0.0, 10.0);

        test.nearestPoint(c);
        inst.compute(c, P, R);

        double[] instp = matrixMultVector(G, inst.nearestPoint());
        double[] testp = test.getLatticePoint();

        System.out.println(print( inst.nearestPoint()));
        System.out.println(print(instp));
        System.out.println(print(testp));

        System.out.println(distance_between(instp, matrixMultVector(G,c)));
        System.out.println(distance_between(testp, matrixMultVector(G, c)));

        for(int n = 0; n < N; n++){
                assertEquals(testp[n], instp[n], 0.00000001);
        }
        //}
    }


}