/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes.crb;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Robby McKilliam
 */
public class WeinsteinTest {

    public WeinsteinTest() {
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
     * Test of getBound method, of class Weinstein.
     */
    @Test
    public void testGetBound() {
        int N = 6;
        System.out.println("getBound");
        Weinstein instance = new Weinstein();
        instance.setN(N);
        instance.setAmplitude(1.0);
        instance.setVariance(1.0);
        
        instance.setNumberOfTestPoints(3);
        
        instance.getBound();
    }

}