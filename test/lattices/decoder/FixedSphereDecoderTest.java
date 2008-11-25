/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class FixedSphereDecoderTest {

    public FixedSphereDecoderTest() {
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
     * Test of nearestPoint method, of class FixedSphereDecoder.
     */
    @Test
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        double[] y = null;
        FixedSphereDecoder instance = new FixedSphereDecoder();
        instance.nearestPoint(y);

    }

}