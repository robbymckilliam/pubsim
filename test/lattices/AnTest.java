/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import lattices.An.An;
import lattices.An.AnSorted;
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
 * @author Robby
 */
public class AnTest {

    public AnTest() {
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
     * Test of getGeneratorMatrix method, of class An.
     */
    @Test
    public void testGetGeneratorMatrix() {
        System.out.println("getGeneratorMatrix");
        An instance = new AnSorted(4);
        //instance.setDimension(4);
        Matrix result = instance.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(result));
    }

}