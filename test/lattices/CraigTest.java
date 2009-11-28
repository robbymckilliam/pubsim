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
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class CraigTest {

    public CraigTest() {
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
     * Test of getGeneratorMatrix method, of class Craig.
     */
    @Test
    public void testGetGeneratorMatrix() {
        System.out.println("getGeneratorMatrix");
        int n = 6;
        int r = 3;
        
        Craig instance = new Craig(n, r);
        Matrix result = instance.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(result));
    }

}