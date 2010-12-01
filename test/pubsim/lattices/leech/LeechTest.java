/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.leech;

import pubsim.lattices.leech.Leech;
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
public class LeechTest {

    public LeechTest() {
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
     * Test of volume method, of class Leech.
     */
    @Test
    public void testVolume() {
        Leech lattice = new Leech();

        System.out.println(VectorFunctions.print(lattice.getGeneratorMatrix()));

        assertEquals(lattice.getGeneratorMatrix().det(), 1.0, 0.000000001);
        assertEquals(lattice.getGeneratorMatrix().det(), lattice.volume(), 0.000000001);
    }

}