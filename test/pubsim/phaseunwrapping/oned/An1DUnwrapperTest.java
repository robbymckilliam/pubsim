/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.oned;

import pubsim.phaseunwrapping.oned.An1DUnwrapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class An1DUnwrapperTest {

    public An1DUnwrapperTest() {
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
     * Test of unwrap method, of class An1DUnwrapper.
     */
    @Test
    public void testUnwrap() {
       System.out.println("testUnwrap");
        int N = 10;
        An1DUnwrapper instance = new An1DUnwrapper();
        //instance.setSize(N);
        //double[] y = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        double[] y = {         1.0000,
    0.8100,
    0.6400,
    0.4900,
    0.3600,
    0.2500,
    0.1600,
    0.0900,
    0.0400,
    0.0100,
         0,
    0.0100,
    0.0400,
    0.0900,
    0.1600,
    0.2500,
    0.3600,
    0.4900,
    0.6400,
    0.8100,
    1.0000};
        y = VectorFunctions.wrap(y);
        double[] u = instance.unwrap(y);
        System.out.println(VectorFunctions.print(y));
        System.out.println(VectorFunctions.print(u));
    }

    /**
     * Test of setSize method, of class An1DUnwrapper.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int N = 0;
        An1DUnwrapper instance = new An1DUnwrapper();
        instance.setSize(N);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}