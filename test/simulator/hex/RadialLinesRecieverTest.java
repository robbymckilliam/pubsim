/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.hex;

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
public class RadialLinesRecieverTest {

    public RadialLinesRecieverTest() {
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
     * Test of decode method, of class RadialLinesReciever.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        double[] rreal = null;
        double[] rimag = null;
        RadialLinesReciever instance = null;
        instance.decode(rreal, rimag);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReal method, of class RadialLinesReciever.
     */
    @Test
    public void testGetRealReturnsCorrectSize() {
        System.out.println("getReal");
        int N = 10;
        RadialLinesReciever instance = new RadialLinesReciever(N);
        assertEquals(instance.getReal().length, N);
        assertEquals(instance.getImag().length, N);
    }


}