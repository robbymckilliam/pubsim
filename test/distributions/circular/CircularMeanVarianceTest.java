/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uqrmckil
 */
public class CircularMeanVarianceTest {

    public CircularMeanVarianceTest() {
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
     * Test of circularVariance method, of class CircularMeanVariance.
     */
    @Test
    public void testCircularVariance() {
        System.out.println("circularVariance");
        CircularMeanVariance instance = null;
        double expResult = 0.0;
        double result = instance.circularVariance();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of circularMean method, of class CircularMeanVariance.
     */
    @Test
    public void testCircularMean() {
        System.out.println("circularMean");
        CircularMeanVariance instance = null;
        double expResult = 0.0;
        double result = instance.circularMean();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}