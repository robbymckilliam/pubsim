/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class AmbiguityRemoverTest {

    public AmbiguityRemoverTest() {
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


    @Test
    public void throwsExceptionWhenIncorrectSize() {
        System.out.println("throwsExceptionWhenIncorrectSize");
        int a = 5;
        double[] p = {3, 2, 4, 5};
        AmbiguityRemover instance = new AmbiguityRemover(a);

        boolean caught = false;
        try{
            instance.disambiguate(p);
        } catch(RuntimeException e){
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    public void testDisambiguate() {
        System.out.println("testDisambiguate");
        double[] p1 = {1.3, -2.1};
        int a = p1.length;
        AmbiguityRemover instance = new AmbiguityRemover(a);
        p1 = instance.disambiguate(p1);
        double[] y1 = {0.3, -0.1};
        assertTrue(VectorFunctions.distance_between2(p1, y1) < 0.000001);

        double[] p2 = {1.3, -2.1, 2.6};
        a = p2.length;
        instance = new AmbiguityRemover(a);
        p2 = instance.disambiguate(p2);
        double[] y2 = {0.3, 0.4, 0.1};
        assertTrue(VectorFunctions.distance_between2(p2, y2) < 0.000001);
        
    }


        /**
     * Test of setSize method, of class AmbiguityRemover.
     */
    @Test
    public void testNextColumn() {
        System.out.println("testNextColumn");
        double[] c = VectorFunctions.eVector(0, 1);
        //System.out.println("c = " + VectorFunctions.print(c));
        c = AmbiguityRemover.getNextColumn(c);
        //System.out.println("c = " + VectorFunctions.print(c));
        double[] y1 = {0, 1};
        assertTrue(VectorFunctions.distance_between2(c, y1) < 0.000001);

        c = AmbiguityRemover.getNextColumn(c);
        //System.out.println("c = " + VectorFunctions.print(c));
        double[] y2 = {0, 1.0/2.0, 1.0/2.0};
        assertTrue(VectorFunctions.distance_between2(c, y2) < 0.000001);

        c = AmbiguityRemover.getNextColumn(c);
        //System.out.println("c = " + VectorFunctions.print(c));
        double[] y3 = {0, 1.0/3.0, 1.0/2.0, 1.0/6};
        assertTrue(VectorFunctions.distance_between2(c, y3) < 0.000001);

    }

}