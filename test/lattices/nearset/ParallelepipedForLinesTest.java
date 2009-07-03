/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class ParallelepipedForLinesTest {

    public ParallelepipedForLinesTest() {
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
     * Test of linePassesThrough method, of class ParallelepipedForLines.
     */
    @Test
    public void testLinePassesThroughSquare() {
        System.out.println("linePassesThrough");

        int n = 2;
        Matrix M = Matrix.identity(n, n);
        double[] m = {1.0,0.0};
        double[] c = {0.0,0.5};
        ParallelepipedForLines R = new ParallelepipedForLines(M);
        assertTrue(R.linePassesThrough(m, c));
        assertEquals(0.0, R.minParam(), 0.00000001);
        assertEquals(1.0, R.maxParam(), 0.00000001);

        double[] m1 = {1.0,0.0};
        double[] c1 = {0.0,1.5};
        R = new ParallelepipedForLines(M);
        assertFalse(R.linePassesThrough(m1, c1));

        double[] m2 = {1.0,1.0};
        double[] c2 = {0.0,0.0};
        R = new ParallelepipedForLines(M);
        assertTrue(R.linePassesThrough(m2, c2));
        assertEquals(0.0, R.minParam(), 0.00000001);
        assertEquals(Math.sqrt(2.0), R.maxParam(), 0.00000001);

    }

}