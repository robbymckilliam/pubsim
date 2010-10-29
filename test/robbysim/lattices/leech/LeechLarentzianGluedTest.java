/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.leech;

import robbysim.lattices.leech.LeechLarentzianGlued;
import Jama.Matrix;
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
public class LeechLarentzianGluedTest {

    public LeechLarentzianGluedTest() {
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
     * Test of nearestPoint method, of class LeechLarentzianGlued.
     */
    @Test
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        double[] y = null;
        LeechLarentzianGlued instance = new LeechLarentzianGlued();
        instance.nearestPoint(y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}