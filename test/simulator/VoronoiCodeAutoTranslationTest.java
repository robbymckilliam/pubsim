/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import lattices.Hexagonal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static simulator.VectorFunctions.print;
import static simulator.VectorFunctionsTest.assertVectorsEqual;

/**
 *
 * @author Robby McKilliam
 */
public class VoronoiCodeAutoTranslationTest {

    public VoronoiCodeAutoTranslationTest() {
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
     * This should return [-1/4, 0] or [1/4, 0] as per
     * Conway and Sloane.
     */
    @Test
    public void test16Hex() {
        System.out.println("test16Hex");
        Hexagonal lattice = new Hexagonal();
        double r = 4;
        VoronoiCodeAutoTranslation inst = 
                new VoronoiCodeAutoTranslation(lattice, 4);
        //System.out.println(print(inst.getTranslation()));
        double[] exp = {-0.25, 0.0};
        assertVectorsEqual(exp, inst.getTranslation(), 0.00001);

    }


}