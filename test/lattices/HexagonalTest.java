/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static simulator.VectorFunctions.randomGaussian;
import static simulator.VectorFunctions.distance_between;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctionsTest.assertVectorsEqual;

/**
 *
 * @author harprobey
 */
public class HexagonalTest {

    public HexagonalTest() {
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
     * Test of nearestPoint method, of class Hexagonal.
     */
    @Test
    public void testNearestPoint() {
        System.out.println("nearestPoint");

        int iters = 1000;
        Hexagonal instance = new Hexagonal();

        for(int i = 0; i < iters; i++){

            double[] x =randomGaussian(2, 0, 100);
            instance.nearestPoint(x);

            assertTrue(distance_between(x, instance.getLatticePoint()) <= instance.coveringRadius());

        }

    }

    /**
     * Test of getIndex method, of class Hexagonal.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        Hexagonal instance = new Hexagonal();
        Matrix M = instance.getGeneratorMatrix();
        double[] u = new double[2];
        u[0] = 2.0; u[1] = -3.0;
        double[] x = matrixMultVector(M, u);
        instance.nearestPoint(x);
        assertVectorsEqual(u, instance.getIndex());        
    }

}