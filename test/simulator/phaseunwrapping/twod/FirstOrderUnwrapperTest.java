/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.twod;

import distributions.GaussianNoise;
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
        System.out.println("testUnwrap");
        int N = 12;
        int M = 12;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        instance.setSize(M, N);
        
        GaussianPulse gaussp = new GaussianPulse(M, N, 2.0, 0.05);
        gaussp.setNoiseGenerator(new GaussianNoise(0.0, 0.001));
        gaussp.generateData();
        double[][] yw = gaussp.getWrappedData();
        double[][] y = instance.unwrap(yw);
        System.out.println("yw = " + VectorFunctions.print(yw));
        System.out.println("i = " + VectorFunctions.print(gaussp.getWrappedIntegers()));
        System.out.println("y = " + VectorFunctions.print(y));
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


}