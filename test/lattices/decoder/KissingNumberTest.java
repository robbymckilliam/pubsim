/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import lattices.Zn;
import lattices.leech.Leech;
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
public class KissingNumberTest {

    public KissingNumberTest() {
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
     * Test of kissingNumber method, of class KissingNumber.
     */
    @Test
    public void testKissingNumberonZn() {
        System.out.println("kissingNumber Zn test");
        int n = 5;
        KissingNumber instance = new KissingNumber(new Zn(n));
        int expResult = 2*n;
        int result = instance.kissingNumber();
        assertEquals(expResult, result);

    }

        /**
     * Test of kissingNumber method, of class KissingNumber.
     */
    @Test
    public void testKissingNumberonLeech() {
        System.out.println("kissingNumber Leech test");
        KissingNumber instance = new KissingNumber(new Leech());
        int expResult = 196560;
        int result = instance.kissingNumber();
        assertEquals(expResult, result);

    }

}