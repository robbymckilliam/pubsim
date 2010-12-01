/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static pubsim.Range.range;

/**
 *
 * @author harprobey
 */
public class RangeTest {

    public RangeTest() {
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
     * Test of range method, of class Range.
     */
    @Test
    public void testRange_int() {
        System.out.println("range integer");
        int exp = 0;
        for( int i : range(4) ){
            assertEquals(exp, i);
            exp++;
        }

    }

    /**
     * Test of range method, of class Range.
     */
    @Test
    public void testRange_double() {
        System.out.println("range double");
        double exp = 0;
        double step = 0.1;
        for( double d : range(0.0, 4.0, step) ){
            assertEquals(exp, d, 0.00000001);
            exp+=step;
        }
    }

}