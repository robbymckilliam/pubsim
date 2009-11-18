/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import lattices.An.AnFastSelect;
import lattices.Phina;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static simulator.VectorFunctions.print;
import static simulator.VectorFunctions.sum2;

/**
 *
 * @author harprobey
 */
public class ShortestVectorTest {

    public ShortestVectorTest() {
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

//    /**
//     * Test of getShortestVector method, of class ShortestVector.
//     */
//    @Test
//    public void testGetShortestVectorAn() {
//        System.out.println("getShortestVector An");
//        int n = 10;
//        ShortestVector sv = new ShortestVector(new AnFastSelect(n));
//        System.out.println(print(sv.getShortestVector()));
//        System.out.println(print(sv.getShortestIndex()));
//
//    }
    
    /**
     * Test of getShortestVector method, of class ShortestVector.
     */
    @Test
    public void testGetShortestVectorPhi() {
        System.out.println("getShortestVector Phi");
        int n = 8;
        int a = 4;
        ShortestVector sv = new ShortestVector(new Phina(a, n));
        System.out.println(print(sv.getShortestVector()));
        System.out.println(print(sv.getShortestIndex()));
        System.out.println(sum2(sv.getShortestVector()));

    }

}