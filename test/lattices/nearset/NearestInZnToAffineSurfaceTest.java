/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class NearestInZnToAffineSurfaceTest {

    public NearestInZnToAffineSurfaceTest() {
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
     * Test of compute method, of class NearestInZnToAffineSurface.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        double[] c = {0,0,0, 0, 0};
        Matrix P = Matrix.random(6, 4);
        NearestInZnToAffineSurface instance = new NearestInZnToAffineSurface(5);
        instance.compute(c, P, null);
    }

    /**
     * Test of decode method, of class NearestInZnToAffineSurface.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        double[] c = {0,0,0, 0, 0};
        Matrix P = Matrix.random(6, 4);
        NearestInZnToAffineSurface instance = new NearestInZnToAffineSurface(5);
        instance.decode(c, P);

    }

}