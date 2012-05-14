/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.location.twod;

import pubsim.location.twod.NearSetLocationEstimatorInSphere;
import pubsim.location.twod.Transmitter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.Point2;

/**
 *
 * @author Robby McKilliam
 */
public class NearSetLocationEstimatorInSphereTest {

    public NearSetLocationEstimatorInSphereTest() {
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
     * Test of transmitterRange method, of class NearSetLocationEstimatorInSphere.
     */
    @Test
    public void testTransmitterRange() {
        System.out.println("transmitterRange");

        double R = 1.0;
        NearSetLocationEstimatorInSphere inst = new NearSetLocationEstimatorInSphere(new Transmitter[0], R);

        double exp = 1.5;
        for(double k : inst.transmitterRange(new Transmitter(new Point2(2.0, 0.0), 1.0) ) ){
            assertEquals(exp, k, 0.0000001);
            exp+=1.0;
        }
        assertEquals(exp, 2.5, 0.0000001);

        R = 1.0;
        inst = new NearSetLocationEstimatorInSphere(new Transmitter[0], R);

        exp = 1.5;
        for(double k : inst.transmitterRange(new Transmitter(new Point2(2.0, 0.0), 0.7) ) ){
            assertEquals(exp, k, 0.0000001);
            exp+=1.0;
        }
        assertEquals(exp, 3.5, 0.0000001);


    }

}