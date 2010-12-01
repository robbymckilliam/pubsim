/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.nearset;

import pubsim.lattices.nearset.NSphereForLines;
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
public class NSphereForLinesTest {

    public NSphereForLinesTest() {
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
     * Test of linePassesThrough method, of class NSphereForLines.
     */
    @Test
    public void testLinePassesThrough() {
        System.out.println("linePassesThrough");
        double[] m = {1.0, 0.0};
        double[] c = {0.0, 0.0};
        NSphereForLines sphere = new NSphereForLines(1.0, 2);
        sphere.linePassesThrough(m, c);
        assertEquals(sphere.minParam(), -1.0, 0.00000001);
        assertEquals(sphere.maxParam(), 1.0, 0.00000001);

        double[] m1 = {1.0, 0.0, 0.0};
        double[] c1 = {0.5, 0.0, 0.0};
        sphere.linePassesThrough(m1, c1);
        assertEquals(sphere.minParam(), -1.5, 0.00000001);
        assertEquals(sphere.maxParam(), 0.5, 0.00000001);

    }

}