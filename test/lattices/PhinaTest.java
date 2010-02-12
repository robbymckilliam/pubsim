/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import simulator.Util;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class PhinaTest {

    public PhinaTest() {
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
     * Test of volume method, of class Phina.
     */
    @Test
    public void volume() {
        System.out.println("volume");
        
        int n = 20;
        int a = 3;
        
        Phina instance = new Phina(a, n);

        Matrix gen = instance.getGeneratorMatrix();
        //System.out.println(VectorFunctions.print(gen));
        Matrix gram = gen.transpose().times(gen);
        double expResult = Math.sqrt(gram.det());
        
        double result = instance.volume();
        assertEquals(expResult, result, 0.001);

    }

        /**
     * Test of volume method, of class Phina.
     */
    @Test
    public void generatorMatrix() {
        System.out.println("generatorMatrix");

        int n = 10;
        int a = 3;

        Phina instance = new Phina(a, n);
        Matrix M = instance.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(M));
        System.out.println(VectorFunctions.print(M.transpose().times(M)));

    }

}