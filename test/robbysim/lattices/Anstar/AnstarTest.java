/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.Anstar;

import robbysim.lattices.Anstar.Anstar;
import robbysim.lattices.Anstar.AnstarAnGlued;
import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import robbysim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class AnstarTest {

    public AnstarTest() {
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
     * Test of volume method, of class Anstar.
     */
    @Test
    public void testVolume() {
        System.out.println("volume");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inradius method, of class Anstar.
     */
    @Test
    public void testInradius() {
        System.out.println("inradius");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of project method, of class Anstar.
     */
    @Test
    public void testProject() {
        System.out.println("project");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGeneratorMatrix method, of class Anstar.
     */
    @Test
    public void testGetGeneratorMatrix() {
        System.out.println("getGeneratorMatrix");
        Anstar instance = new AnstarAnGlued(4);
        instance.setDimension(4);
        Matrix result = instance.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(result));
    }

    /**
     * Test of glue method, of class Anstar.
     */
    @Test
    public void testGlue() {
        System.out.println("glue");
        int i = 3, n = 6;
        double dn = 1.0/n;
        double[] glue = new double[n];
        Anstar.glue(i, glue);
        assertEquals(glue[0], i*(1.0 - dn), 0.000001);
        assertEquals(glue[1], i*(-dn), 0.000001);
    }

}