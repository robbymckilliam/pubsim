/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util.region;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctions.add;

/**
 *
 * @author harprobey
 */
public class ParallelepipedTest {

    public ParallelepipedTest() {
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
     * Test of within method, of class Parallelepiped.
     */
    @Test
    public void testWithin() {
        System.out.println("within");
        int m = 6;
        int n  = 4;
        Matrix M = Matrix.random(m, n);
        double[] p1 = {0.5, 0.5, 0.5, 0.5};
        double[] y = matrixMultVector(M, p1);
        Parallelepiped R = new Parallelepiped(M);
        assertTrue(R.within(y));

        double[] p2 = {1.5, 0.5, 0.5, 0.5};
        y = matrixMultVector(M, p2);
        assertFalse(R.within(y));

        double[] t = {1.0,2.0,3.0,4.0,5.0,6.0};
        R = new Parallelepiped(M, t);
        y = add(matrixMultVector(M, p1), t);
        assertTrue(R.within(y));

        R = new Parallelepiped(M, t);
        y = add(matrixMultVector(M, p2), t);
        assertFalse(R.within(y));

    }

}