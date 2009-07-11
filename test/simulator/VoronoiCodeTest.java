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
import static simulator.VectorFunctions.add;
import static simulator.VectorFunctions.randomGaussian;
import static simulator.VectorFunctionsTest.assertVectorsEqual;

/**
 *
 * @author Robby McKilliam
 */
public class VoronoiCodeTest {

    public VoronoiCodeTest() {
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
     * Test of encode method, of class VoronoiCode.
     */
    @Test
    public void testEncode() {
        System.out.println("endcode");
        double[] a = {-0.25,0.0};
        VoronoiCode code = new VoronoiCode(new Hexagonal(), a, 2);
        double[] u= {0, 0};
        double[] x = code.encode(u);
        System.out.println(print(x));
        double[] u1= {0, 1};
        double[] x1 = code.encode(u1);
        System.out.println(print(x1));
        double[] u2= {1, 0};
        double[] x2 = code.encode(u2);
        System.out.println(print(x2));
        double[] u3= {1, 1};
        double[] x3 = code.encode(u3);
        System.out.println(print(x3));
    }

    /**
     * Test of decode method, of class VoronoiCode.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        double[] a = {-0.25,0.0};
        VoronoiCode code = new VoronoiCode(new Hexagonal(), a, 2);
        double[] u= {0, 0};
        double[] x = code.encode(u);
        assertVectorsEqual(u, code.decode(x));
        double[] u2= {1, 0};
        double[] x2 = code.encode(u2);
        assertVectorsEqual(u2, code.decode(x2));
        double[] u3= {0, 1};
        double[] x3 = code.encode(u3);
        assertVectorsEqual(u3, code.decode(x3));
        double[] u4= {1, 1};
        double[] x4 = code.encode(u4);
        assertVectorsEqual(u4, code.decode(x4));

    }

}