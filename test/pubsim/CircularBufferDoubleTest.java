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

/**
 *
 * @author mckillrg
 */
public class CircularBufferDoubleTest {
    
    public CircularBufferDoubleTest() {
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
     * Test of add method, of class CircularBufferDouble.
     */
    @Test
    public void testAddAndGet() {
        System.out.println("test add and get");
        double i = 0.0;
        CircularBufferDouble instance = new CircularBufferDouble(5);
        instance.add(1);
        instance.add(2);
        instance.add(3);
        
        assertEquals(1, instance.get(0), 0.00000001);
        assertEquals(2, instance.get(1), 0.00000001);
        assertEquals(3, instance.get(2), 0.00000001);
        assertEquals(0, instance.get(3), 0.00000001);
        
        instance.add(4);
        instance.add(5);
        
        assertEquals(4, instance.get(3), 0.00000001);
        assertEquals(5, instance.get(4), 0.00000001);
        assertEquals(1, instance.get(5), 0.00000001);

        instance.add(6);
        
        assertEquals(6, instance.get(5), 0.00000001);
        assertEquals(6, instance.get(0), 0.00000001);
        
    }

}
