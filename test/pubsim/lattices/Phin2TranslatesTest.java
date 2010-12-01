/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import pubsim.lattices.Vn2Translates;
import pubsim.lattices.GeneralLatticeAndNearestPointAlgorithm;
import pubsim.lattices.Vnm;
import Jama.Matrix;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Vn2Star.Vn2Star;
import pubsim.lattices.Vn2Star.Vn2StarGlued;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.VectorFunctions;
import pubsim.VectorFunctionsTest;

/**
 *
 * @author robertm
 */
public class Phin2TranslatesTest {

    public Phin2TranslatesTest() {
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
     * Test of getGeneratorMatrix method, of class Vn2Translates.
     */
    @Test
    public void testGetGeneratorMatrix() {
        System.out.println("getGeneratorMatrix");
        int n = 6;
        int j = 1;
        int k = 1;

        Vn2Translates instance = new Vn2Translates(n, j, k);
        Matrix Mat = instance.getGeneratorMatrix();
        //System.out.println(VectorFunctions.print(Mat));

        GeneralLatticeAndNearestPointAlgorithm sd
                = new GeneralLatticeAndNearestPointAlgorithm(Mat);
        Vn2StarGlued np = new Vn2StarGlued(n);

        double[] r = VectorFunctions.randomGaussian(n+2, 100, 1000);

        sd.nearestPoint(r);
        np.nearestPoint(r);

        VectorFunctionsTest.assertVectorsEqual(sd.getLatticePoint(), np.getLatticePoint());

//        System.out.println(VectorFunctions.print(sd.getLatticePoint()));
//        System.out.println(VectorFunctions.print(np.getLatticePoint()));
//        System.out.println(VectorFunctions.print(r));

    }

    /**
     * Test of getGeneratorMatrix method, of class Vn2Translates.
     */
    @Test
    public void testForSelfDual() {
        System.out.println("testForSelfDual");
        int n = 16;
        int j = 1;
        int k = 1;

        Vn2Translates instance = new Vn2Translates(n, j, k);
        Matrix Mat = instance.getGeneratorMatrix();

        //System.out.println(instance.volume());

    }

        /**
     * Test of getGeneratorMatrix method, of class Vn2Translates.
     */
    @Test
    public void testAnPhin2() {
        System.out.println("testAnPhin2");
        int n = 50;
        int j = 1;
        int k = 1;

        Vnm lattice = new Vnm(2, n);
        AnFastSelect an = new AnFastSelect(n+1);

        double biggest = 0;
        for(int i = 1; i< 1000; i++){

            double[] r = VectorFunctions.randomGaussian(n+2, 100, 1000);
            Vn2Star.project(r, r);
            double[] g = Vn2Star.getgVector(n+2);

            an.nearestPoint(r);
            double[] u = an.getLatticePoint();

            double udg = VectorFunctions.dot(u, g);
            //System.out.println(udg);

            //assertTrue(Math.abs(udg) <= n+2);
            if( Math.abs(udg) > biggest ) {
                biggest = Math.abs(udg);
                System.out.println(udg);
            }

        }
    }

}