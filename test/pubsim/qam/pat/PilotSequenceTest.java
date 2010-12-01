/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.qam.pat.PilotSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.Complex;

/**
 *
 * @author Robby
 */
public class PilotSequenceTest {

    public PilotSequenceTest() {
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
     * Test of next method, of class PilotSequence.
     */
    @Test
    public void next() {
        System.out.println("next");
        
        Complex[] ca = { new Complex(1,2), new Complex(3,4), new Complex(5,6) };
        
        PilotSequence instance = new PilotSequence(ca);
        
        assertEquals(true, instance.next().equals(new Complex(3,4)));
        assertEquals(true, instance.next().equals(new Complex(5,6)));
        assertEquals(true, instance.next().equals(new Complex(1,2)));
        assertEquals(true, instance.next().equals(new Complex(3,4)));
        
        double[] da = { 1,2, 3,4, 5,6 };
        
        instance = new PilotSequence(da);
        
        assertEquals(true, instance.next().equals(new Complex(3,4)));
        assertEquals(true, instance.next().equals(new Complex(5,6)));
        assertEquals(true, instance.next().equals(new Complex(1,2)));
        assertEquals(true, instance.next().equals(new Complex(3,4)));

    }

    /**
     * Test of current method, of class PilotSequence.
     */
    @Test
    public void current() {
        System.out.println("current");
        
        Complex[] ca = { new Complex(1,2), new Complex(3,4), new Complex(5,6) };
        
        PilotSequence instance = new PilotSequence(ca);
        
        assertEquals(true, instance.current().equals(new Complex(1,2)));
        assertEquals(true, instance.next().equals(new Complex(3,4)));
        assertEquals(true, instance.current().equals(new Complex(3,4)));
        assertEquals(true, instance.next().equals(new Complex(5,6)));
        assertEquals(true, instance.current().equals(new Complex(5,6)));
        assertEquals(true, instance.next().equals(new Complex(1,2)));
        assertEquals(true, instance.current().equals(new Complex(1,2)));
        assertEquals(true, instance.next().equals(new Complex(3,4)));
        assertEquals(true, instance.current().equals(new Complex(3,4)));
    }

}