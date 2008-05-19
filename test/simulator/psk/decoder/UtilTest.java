/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class UtilTest {

    public UtilTest() {
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
     * Test of differentialEncodedSignal method, of class Util.
     */
    @Test
    public void differentialEncodedSignal() {
        System.out.println("differentialEncodedSignal");
        double[] x = {1, 2, 3, 1, 3, 5, 2, 0};
        int M = 4;
        double[] expResult = {1, 1, 2, 2, 2, 1, 2};
        double[] result = Util.differentialEncodedSignal(x, M);
        System.out.println(VectorFunctions.print(expResult));
        System.out.println(VectorFunctions.print(result));
        assertEquals(VectorFunctions.distance_between2(expResult, result)<0.000001, true);

    }

    /**
     * Test of differentialEncodedEqual method, of class Util.
     */
    @Test
    public void differentialEncodedEqual() {
        System.out.println("differentialEncodedEqual");
        double[] x = {0, 1, 2, 3, 4, 5, 6};
        double[] y = {1, 2, 3, 0, 5, 2, 7};
        int M = 4;
        boolean expResult = true;
        boolean result = Util.differentialEncodedEqual(x, y, M);
        assertEquals(expResult, result);
    }

}