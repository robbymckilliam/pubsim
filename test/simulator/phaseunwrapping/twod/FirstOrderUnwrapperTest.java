/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.twod;

import Jama.Matrix;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class FirstOrderUnwrapperTest {

    public FirstOrderUnwrapperTest() {
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
     * Test of unwrap method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testUnwrap() {
        System.out.println("unwrap");
        double[][] y = null;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        double[][] expResult = null;
        double[][] result = instance.unwrap(y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unwrapIntegers method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testUnwrapIntegers() {
        System.out.println("unwrapIntegers");
        double[][] y = null;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        double[][] expResult = null;
        double[][] result = instance.unwrapIntegers(y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSize method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int M = 4;
        int N = 4;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        instance.setSize(M, N);

    }

    /**
     * Test of constructMatricesForParameter method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testConstructMatricesForParameter() {
        System.out.println("constructMatricesForParameter");
        int m = 0;
        int n = 0;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        instance.constructMatricesForParameter(m, n);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyVectorToMatrix method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testCopyVectorToMatrix() {
        System.out.println("copyVectorToMatrix");
        Vector<Double[]> v = null;
        Matrix expResult = null;
        Matrix result = FirstOrderUnwrapper.copyVectorToMatrix(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnPIndex method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testReturnPIndex() {
        System.out.println("returnPIndex");
        int m = 0;
        int n = 0;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        int expResult = 0;
        int result = instance.returnPIndex(m, n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnYIndex method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testReturnYIndex() {
        System.out.println("returnYIndex");
        int m = 0;
        int n = 0;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        int expResult = 0;
        int result = instance.returnYIndex(m, n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}