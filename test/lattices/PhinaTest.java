/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class PhinaTest {

    public PhinaTest() {
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
     * Test of volume method, of class Phina.
     */
    @Test
    public void volume() {
        System.out.println("volume");
        
        int n = 8;
        int a = 1;
        
        Phina instance = new Phina(a, n);
        double expResult = Math.sqrt(9);
        double result = instance.volume();
        assertEquals(true, Math.abs(expResult-result)<0.00001);

    }

}