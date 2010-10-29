/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices;

import robbysim.lattices.Hexagonal;
import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static robbysim.VectorFunctions.randomGaussian;
import static robbysim.VectorFunctions.distance_between;
import static robbysim.VectorFunctions.matrixMultVector;
import static robbysim.VectorFunctionsTest.assertVectorsEqual;
import static robbysim.VectorFunctions.round;
import static robbysim.VectorFunctions.add;

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
    public void testInsideCoveringRadius() {
        System.out.println("InsideCoveringRadius");

        int iters = 1000;
        Hexagonal instance = new Hexagonal();

        for(int i = 0; i < iters; i++){

            double[] x =randomGaussian(2, 0, 100);
            instance.nearestPoint(x);

            assertTrue(distance_between(x, instance.getLatticePoint()) <= instance.coveringRadius());

        }

    }

    /**
     * Test of nearestPoint method, of class Hexagonal.
     */
    @Test
    public void testNearestPoint() {
        System.out.println("NearestPoint");

        int iters = 1000;
        Hexagonal instance = new Hexagonal();
        Matrix M = instance.getGeneratorMatrix();
        double[] x = new double[2];
        double[] u = new double[2];

        for(int i = 0; i < iters; i++){
            
            round( randomGaussian(2, 0, 1000), u );
            x = matrixMultVector(instance.getGeneratorMatrix(), u);
            add(x, randomGaussian(2, 0, 0.0001), x);
            instance.nearestPoint(x);
            //round(ret, ret);

            assertVectorsEqual(u, instance.getIndex());

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