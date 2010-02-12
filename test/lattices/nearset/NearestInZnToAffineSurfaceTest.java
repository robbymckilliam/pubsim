/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import lattices.Vn2Star.Vn2Star;
import lattices.Vn2Star.Vn2StarZnLLS;
import lattices.VnmStarSampled;
import lattices.decoder.SphereDecoder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctionsTest;
import static org.junit.Assert.*;
import static simulator.VectorFunctions.randomGaussian;
import static simulator.VectorFunctions.columnMatrix;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctions.print;
import static simulator.VectorFunctions.distance_between;

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
        int N = 10;

        for( int i = 0; i < iters; i++){
            RegionForLines R = new NSphereForLines(100, randomGaussian(N));
            double[] m = randomGaussian(N);
            double[] c = randomGaussian(N);

            NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(columnMatrix(m), R);
            NearestInZnToLine tester = new NearestInZnToLine(columnMatrix(m), R);

            tester.compute(c);
            inst.compute(c);

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

            NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(P, R);
            inst.compute(c);

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

        int iters = 100;

        int N = 10;
        Matrix P = Vn2Star.getMMatrix(N-2);
        Matrix Pt = P.transpose();
        Matrix K = (Pt.times(P)).inverse().times(Pt);
        Matrix G = Matrix.identity(N, N).minus(P.times(K));

        Vn2Star test = new Vn2StarZnLLS(N-2);

        for( int i = 0; i < iters; i++){

            //NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(N);

            double[] c = randomGaussian(N, 0.0, 10000.0);
            //Vn2Star.project(c, c);

            RegionForLines R = new ParallelepipedForLines(P.times(-1.0), c);
            //RegionForLines R = new NSphereForLines(2*N*N*N, c);

            //System.out.println(print(c));

            test.nearestPoint(c);
            NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(P.times(-1.0), R);
            inst.compute(c);

            double[] instp = matrixMultVector(G, inst.nearestPoint());
            double[] testp = test.getLatticePoint();

            //System.out.println(print( inst.nearestPoint()));
            //System.out.println(print(instp));
            //System.out.println(print(testp));

            //System.out.println(distance_between(instp, matrixMultVector(G,c)));
            //System.out.println(distance_between(testp, matrixMultVector(G, c)));

            VectorFunctionsTest.assertVectorsEqual(testp, instp);
            
        }
    }

     /**
     * Test of compute method, of class NearestInZnToAffineSurface.
     */
    @Test
    public void sameAsPolyEst3() {
        System.out.println("sameAsPolyEst3");

        int iters = 100;

        int N = 15;
        int a = 3;

        Matrix scaler = new Matrix(a,a);
        for(int i = 0; i < a; i ++){
            scaler.set(i,i, -1.0/simulator.Util.factorial(i));
        }

        SphereDecoder test = new SphereDecoder(new VnmStarSampled(a, N-a));

        Matrix P = VnmStarSampled.getMMatrix(a, N-a);
        Matrix Pt = P.transpose();
        Matrix K = (Pt.times(P)).inverse().times(Pt);
        Matrix G = Matrix.identity(N, N).minus(P.times(K));

        //for( int i = 0; i < iters; i++){

            //NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(N);

            double[] c = randomGaussian(N, 0.0, 1000.0);
            //Vn2Star.project(c, c);

            RegionForLines R = new ParallelepipedForLines(P.times(scaler), c);
            //RegionForLines R = new NSphereForLines(2*N*N*N, c);

            //System.out.println(print(c));
            NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(P.times(-1.0), R);

            test.nearestPoint(c);
            inst.compute(c);

            double[] instp = matrixMultVector(G, inst.nearestPoint());
            double[] testp = test.getLatticePoint();

            //System.out.println(print( inst.nearestPoint()));
            System.out.println(print(instp));
            System.out.println(print(testp));

            System.out.println(distance_between(instp, matrixMultVector(G,c)));
            System.out.println(distance_between(testp, matrixMultVector(G, c)));

            VectorFunctionsTest.assertVectorsEqual(testp, instp);

        //}
    }

//     /**
//     * Test of compute method, of class NearestInZnToAffineSurface.
//     */
//    @Test
//    public void sameAsPolyEst4() {
//        System.out.println("sameAsPolyEst4");
//
//        int iters = 100;
//
//        int N = 7;
//        int a = 4;
//
//        Matrix scaler = new Matrix(a,a);
//        for(int i = 0; i < a; i ++){
//            scaler.set(i,i, -1.0/simulator.Util.factorial(i));
//        }
//
//        SphereDecoder test = new SphereDecoder(new VnmStarSampled(a, N-a));
//
//        Matrix P = VnmStarSampled.getMMatrix(a, N-a);
//        Matrix Pt = P.transpose();
//        Matrix K = (Pt.times(P)).inverse().times(Pt);
//        Matrix G = Matrix.identity(N, N).minus(P.times(K));
//
//        //for( int i = 0; i < iters; i++){
//
//            //NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(N);
//
//            double[] c = randomGaussian(N, 0.0, 1000.0);
//            //Vn2Star.project(c, c);
//
//            RegionForLines R = new ParallelepipedForLines(P.times(scaler), c);
//            //RegionForLines R = new NSphereForLines(2*N*N*N, c);
//
//            //System.out.println(print(c));
//            NearestInZnToAffineSurface inst = new NearestInZnToAffineSurface(P.times(-1.0), R);
//
//            test.nearestPoint(c);
//            inst.compute(c);
//
//            double[] instp = matrixMultVector(G, inst.nearestPoint());
//            double[] testp = test.getLatticePoint();
//
//            //System.out.println(print( inst.nearestPoint()));
//            System.out.println(print(instp));
//            System.out.println(print(testp));
//
//            System.out.println(distance_between(instp, matrixMultVector(G,c)));
//            System.out.println(distance_between(testp, matrixMultVector(G, c)));
//
//            VectorFunctionsTest.assertVectorsEqual(testp, instp);
//
//        //}
//    }


}