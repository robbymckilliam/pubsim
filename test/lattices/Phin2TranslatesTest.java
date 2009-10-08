/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import lattices.Phin2star.Phin2StarGlued;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import simulator.VectorFunctionsTest;

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
     * Test of getGeneratorMatrix method, of class Phin2Translates.
     */
    @Test
    public void testGetGeneratorMatrix() {
        System.out.println("getGeneratorMatrix");
        int n = 6;
        int j = 1;
        int k = 1;

        Phin2Translates instance = new Phin2Translates(n, j, k);
        Matrix Mat = instance.getGeneratorMatrix();
        //System.out.println(VectorFunctions.print(Mat));

        GeneralLatticeAndNearestPointAlgorithm sd
                = new GeneralLatticeAndNearestPointAlgorithm(Mat);
        Phin2StarGlued np = new Phin2StarGlued(n);

        double[] r = VectorFunctions.randomGaussian(n+2, 100, 1000);

        sd.nearestPoint(r);
        np.nearestPoint(r);

        VectorFunctionsTest.assertVectorsEqual(sd.getLatticePoint(), np.getLatticePoint());

//        System.out.println(VectorFunctions.print(sd.getLatticePoint()));
//        System.out.println(VectorFunctions.print(np.getLatticePoint()));
//        System.out.println(VectorFunctions.print(r));

    }

    /**
     * Test of getGeneratorMatrix method, of class Phin2Translates.
     */
    @Test
    public void testForSelfDual() {
        System.out.println("testForSelfDual");
        int n = 16;
        int j = 1;
        int k = 1;

        Phin2Translates instance = new Phin2Translates(n, j, k);
        Matrix Mat = instance.getGeneratorMatrix();

        System.out.println(instance.volume());

    }

}