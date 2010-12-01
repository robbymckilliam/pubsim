/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.twod;

import pubsim.phaseunwrapping.twod.ZerothOrderUnwrapper;
import Jama.Matrix;
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
public class ZerothOrderUnwrapperTest {

    public ZerothOrderUnwrapperTest() {
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
     * Test of unwrap method, of class ZerothOrderUnwrapper.
     */
    @Test
    public void testUnwrap() {
        System.out.println("unwrap");
        double[][] y = null;
        ZerothOrderUnwrapper instance = new ZerothOrderUnwrapper();
        double[][] expResult = null;
        double[][] result = instance.unwrap(y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unwrapIntegers method, of class ZerothOrderUnwrapper.
     */
    @Test
    public void testUnwrapIntegers() {
        System.out.println("unwrapIntegers");
        double[][] y = null;
        ZerothOrderUnwrapper instance = new ZerothOrderUnwrapper();
        double[][] expResult = null;
        double[][] result = instance.unwrapIntegers(y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSize method, of class ZerothOrderUnwrapper.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int M = 5;
        int N = 5;
        ZerothOrderUnwrapper instance = new ZerothOrderUnwrapper();
        instance.setSize(M, N);
    }

    /**
     * Test of returnArrayIndex method, of class ZerothOrderUnwrapper.
     */
    @Test
    public void testReturnArrayIndex() {
        System.out.println("returnArrayIndex");
        int m = 0;
        int n = 0;
        ZerothOrderUnwrapper instance = new ZerothOrderUnwrapper();
        int expResult = 0;
        int result = instance.returnArrayIndex(m, n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of matrixSet method, of class ZerothOrderUnwrapper.
     */
    @Test
    public void testMatrixSet() {
        System.out.println("matrixSet");
        Matrix M = null;
        int m = 0;
        int n = 0;
        double val = 0.0;
        ZerothOrderUnwrapper.matrixSet(M, m, n, val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}