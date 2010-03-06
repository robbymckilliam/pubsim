/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import lattices.Dn;
import lattices.GeneralLattice;
import lattices.Lattice;
import lattices.Vnm;
import lattices.VnmStar;
import lattices.VnmStarGlued;
import static simulator.Util.discreteLegendrePolynomial;
import static simulator.Util.binom;
import static simulator.Util.factorial;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static simulator.VectorFunctions.print;

/**
 *
 * @author harprobey
 */
public class PointsInHypercubeTest {

    public PointsInHypercubeTest() {
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

//    /**
//     * Test of nextElement method, of class PointsInHypercube.
//     */
//    @Test
//    public void testWithScaledZn() {
//        System.out.println("testWithScaledZn");
//        int n = 3;
//        Matrix M = Matrix.identity(n, n).times(0.5);
//        Lattice L = new GeneralLattice(M);
//        PointsInHypercube points = new PointsInHypercube(L);
//
//        int count = 0;
//        while(points.hasMoreElements()){
//            Matrix p = points.nextElement();
//            System.out.println(print(p));
//            count++;
//        }
//        assertEquals(count, (int)Math.pow(2, n));
//
//    }
//
//    /**
//     * Test of nextElement method, of class PointsInHypercube.
//     */
//    @Test
//    public void testWithDn() {
//        System.out.println("testWithDn");
//
//        Matrix M = Matrix.identity(2, 2);
//        M.set(0,1, 0.5); M.set(1,1, 0.5);
//        Lattice L = new GeneralLattice(M);
//        PointsInHypercube points = new PointsInHypercube(L);
//        int count = 0;
//        while(points.hasMoreElements()){
//            Matrix p = points.nextElement();
//            System.out.println(print(p));
//            count++;
//        }
//        assertEquals(count, 2);
//
//        L = new Dn(2);
//        points = new PointsInHypercube(L);
//
//        count = 0;
//        while(points.hasMoreElements()){
//            Matrix p = points.nextElement();
//            System.out.println(print(p));
//            count++;
//        }
//        assertEquals(count, 1);
//
//    }

        /**
     * Test of nextElement method, of class PointsInHypercube.
     */
    @Test
    public void testWithVnmPolys() {
        System.out.println("testWithVnmPolys");

        int n = 5;
        int m = 1;
        int N = n+m+1;

        Matrix M = new Matrix(m+1, m+1);
        for(int k = 0; k <= m; k++){
            long fk = factorial(k);
            double ldl = fk*fk*binom(N+k, 2*k+1)/((double)binom(2*k,k));
            for(int i = 0; i <= m; i++){
                double l = discreteLegendrePolynomial(k, N, i);
                M.set(i, k, l/ldl);
            }
        }

        Matrix L = new Matrix(m+1, m+1);
        for(int k = 0; k <= m; k++){
            double scale = factorial(k)/((double)binom(2*k,k));
            for(int s = 0; s <= m; s++){
                double l = scale*Math.pow(-1, s+k)*binom(s+k, s)*binom(N-s-1, N-k-1);
                L.set(k, s, l);
            }
        }

        Matrix B = M.times(L);

        System.out.println(print(M));
        System.out.println(print(L));
        System.out.println(print(B));

        System.out.println(M.det());
        System.out.println(L.det());
        System.out.println(B.det());

        Lattice Vnm = new Vnm(m+1,n);
        double volume = Vnm.volume();
        double det = volume*volume;
        //assertEquals(1.0/det, B.det(), 0.000001);

        PointsInHypercube points = new PointsInHypercube(new GeneralLattice(B));


        int count = 0;
        while(points.hasMoreElements()){
            points.nextElement();
            count++;
        }
        assertEquals(det, count, 0.1);

    }


}