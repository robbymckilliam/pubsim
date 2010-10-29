/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.location.twod;

import robbysim.location.twod.NearSetLocationEstimatorInSphere;
import robbysim.location.twod.Transmitter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import robbysim.Point2;

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
     * Test of estimateLocation method, of class NearSetLocationEstimatorInSphere.
     */
    @Test
    public void testEstimateLocation() {
        System.out.println("estimateLocation");
        double[] d = null;
        NearSetLocationEstimatorInSphere instance = null;
        Point2 expResult = null;
        Point2 result = instance.estimateLocation(d);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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