/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.qam.hex.LineHexReciever.DoubleAndPoint2AndIndex;

/**
 *
 * @author robertm
 */
public class LineHexRecieverTest {

    public LineHexRecieverTest() {
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
     * Test of nextHexangonalNearPoint method, of class RadialLinesReciever.
     */
    @Test
    public void testNextHexangonalNearPoint() {
        System.out.println("nextHexangonalNearPoint");
        double d1 = 1.0;
        double d2 = 0.0;
        double c1 = 0.0;
        double c2 = 0.0;
        double expr = 0.5;
        LineHexReciever.DoubleAndPoint2AndIndex dp
            = LineHexReciever.nextHexangonalNearPoint(d1, d2, c1, c2);
        assertEquals(expr, dp.value, 0.0000001);

        //System.out.println(dp.value);
        //System.out.println(dp.point);

        d1 = 0.5;
        d2 = Math.sqrt(3)/2;
        expr = 0.5;
        dp = LineHexReciever.nextHexangonalNearPoint(d1, d2, c1, c2);
        assertEquals(expr, dp.value, 0.0000001);

        //System.out.println(dp.value);
        //System.out.println(dp.point);

    }

}