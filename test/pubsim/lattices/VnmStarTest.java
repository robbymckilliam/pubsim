/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import pubsim.lattices.reduction.LLL;
import Jama.Matrix;
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
public class VnmStarTest {

    public VnmStarTest() {
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
     * Test of project method, of class lattices.VnmStarSampledEfficient.
     */
    @Test
    public void testProject() {
        System.out.println("project");

        double[] x = {1,4,5,2,1};
        double[] y = new double[x.length];
        int m = 1;

        //from matlab
        double[] exp = {-2, 1.2, 2.4 ,-0.4, -1.2};

        VnmStar.project(x, y, m);

        System.out.println(VectorFunctions.print(y));
        double dist = VectorFunctions.distance_between(y, exp);
        System.out.println(" y = " + VectorFunctions.print(y));
        assertEquals(true, dist<0.0001);

        //test with a larger projection.
        x = VectorFunctions.randomGaussian(100);
        y = new double[x.length];
        m = 4;
        VnmStar.project(x, y, m);
        VnmStar.getMMatrix(m, 100 - m - 1);
        Matrix ym = VectorFunctions.rowMatrix(y);
        Matrix ret = ym.times(VnmStar.getMMatrix(m, 100 - m - 1));
        for(int i = 0; i <= m; i++){
            assertEquals(0.0, ret.get(0,i), 0.000001);
        }


    }

    @Test
    public void testMMatrix(){
        System.out.println("testGeneratorMatrix");

        int n = 8;
        int m = 2;

        Matrix M = VnmStar.getMMatrix(m, n);
        System.out.println(VectorFunctions.print(M));
    }

    @Test
    public void testGeneratorMatrix(){
        System.out.println("testGeneratorMatrix");

        int n = 11;
        int m = 3;

        Matrix M = VnmStar.getGeneratorMatrix(m, n);
        System.out.println(VectorFunctions.print(M));

        Matrix Mpt = M.inverse().transpose();
        System.out.println(VectorFunctions.print(Mpt));

        LLL lll = new LLL();
        System.out.println(VectorFunctions.print(lll.reduce(Mpt)));

    }

}