/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import lattices.Zn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class PointInSphereTest {

    public PointInSphereTest() {
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
     * Test of nextElementDouble method, of class PointInSphere.
     */
    @Test
    public void testNextElement() {
        System.out.println("nextElement");
        int N = 2;
        double radius = 4.0;
        PointInSphere instance = new PointInSphere(new Zn(N), radius);
        while(instance.hasMoreElements()){
            System.out.println(VectorFunctions.print(instance.nextElementDouble()));
        }

    }

}