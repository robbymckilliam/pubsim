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
        
        double[] x1 = {0, 1, 2, 3, 4, 5, 6};
        double[] y1 = {1, 2, 3, 0, 6, 2, 7};
        M = 4;
        expResult = false;
        result = Util.differentialEncodedEqual(x1, y1, M);
        assertEquals(expResult, result);
        
        double[] x2 = {2.0, -2.0, 0.0, 1.0, 2.0, -1.0, 2.0, -1.0, 2.0, 2.0};
        double[] y2 = {2.0, 2.0, 0.0, 1.0, 2.0, 3.0, 2.0, 3.0, 2.0, 2.0};
        M = 4;
        expResult = true;
        result = Util.differentialEncodedEqual(x2, y2, M);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of differentialEncodedSymbolErrors method, of class Util.
     */
    @Test
    public void differentialEncodedSymbolErrors() {
        System.out.println("differentialEncodedSymbolErrors");
        double[] x = {0, 1, 2, 3, 4, 5, 6};
        double[] y = {1, 2, 3, 0, 5, 2, 7};
        int M = 4;
        int expResult = 0;
        int result = Util.differentialEncodedSymbolErrors(x, y, M);
        assertEquals(expResult, result);
        
        double[] x1 = {0, 1, 2, 3, 4, 5, 6};
        double[] y1 = {1, 2, 3, 1, 3, 2, 7};
        M = 8;
        expResult = 4;
        result = Util.differentialEncodedSymbolErrors(x1, y1, M);
        assertEquals(expResult, result);
        
        double[] x2 = {0, 1, 2, 3, 4, 5, 6};
        double[] y2 = {1, 1, 3, 0, 5, 2, 7};
        M = 4;
        expResult = 2;
        result = Util.differentialEncodedSymbolErrors(x2, y2, M);
        assertEquals(expResult, result);
        
        double[] x3 = {0, 1, 2, 3, 4, 5, 6};
        double[] y3 = {1, 2, 3, 0, 5, 2, 8};
        M = 4;
        expResult = 1;
        result = Util.differentialEncodedSymbolErrors(x3, y3, M);
        assertEquals(expResult, result);
        
        double[] x4 = {0, 1, 2, 3, 4, 5, 6};
        double[] y4 = {1, 2, 3, 1, 4, 2, 7};
        M = 4;
        expResult = 3;
        result = Util.differentialEncodedSymbolErrors(x4, y4, M);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of differentialEncodedEqual method, of class Util.
     */
    @Test
    public void differentialEncodedBitErrors() {
        System.out.println("differentialEncodedEqual");
        double[] x = {0, 1, 2, 3, 4, 5, 6};
        double[] y = {1, 2, 3, 0, 5, 2, 7};
        int M = 4;
        int expResult = 0;
        int result = Util.differentialEncodedBitErrors(x, y, M);
        assertEquals(expResult, result);
        
        double[] x1 = {0, 1, 2, 3, 4, 5, 6};
        double[] y1 = {2, 2, 3, 0, 5, 2, 7};
        M = 4;
        expResult = 1;
        result = Util.differentialEncodedBitErrors(x1, y1, M);
        assertEquals(expResult, result);
    }

}