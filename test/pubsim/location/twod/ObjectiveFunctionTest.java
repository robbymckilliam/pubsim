/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.location.twod;

import pubsim.location.twod.Transmitter;
import pubsim.location.twod.ObjectiveFunction;
import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.Point2;

/**
 *
 * @author harprobey
 */
public class ObjectiveFunctionTest {

    public ObjectiveFunctionTest() {
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
     * Test of value method, of class ObjectiveFunction.
     */
    @Test
    public void testCorrectWithLargeWavelengthNoWrapping() {
        System.out.println("testCorrectWithLargeWavelengthNoWrapping");
        Point2 x = new Point2();
        Transmitter[] trans = new Transmitter[1];
        trans[0] = new Transmitter(new Point2(1.0,0.0), 100);
        double[] d = new double[1];
        d[0] = 1.0;
        ObjectiveFunction instance = new ObjectiveFunction(trans, d);
        double expResult = -0.0;
        double result = instance.value(x);
        assertEquals(expResult, result, 0.0000001);

        d[0] = 0.8;
        expResult = -0.2*0.2;
        result = instance.value(x);
        assertEquals(expResult, result, 0.0000001);

        d[0] = 0.1;
        trans[0] = new Transmitter(new Point2(1.2,0.0), 0.6);
        expResult = -0.1*0.1;
        result = instance.value(x);
        assertEquals(expResult, result, 0.0000001);
    }


}