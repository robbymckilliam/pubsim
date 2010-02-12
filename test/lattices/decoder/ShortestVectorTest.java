/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import lattices.Fermat;
import lattices.Vnm;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
        int n = 78;
        int a = 7;
        ShortestVector sv = new ShortestVector(new Vnm(a, n), 14);
        System.out.println(print(sv.getShortestVector()));
        System.out.println(print(sv.getShortestIndex()));
        System.out.println(sum2(sv.getShortestVector()));

    }

//        /**
//     * Test of getShortestVector method, of class ShortestVector.
//     */
//    @Test
//    public void testGetShortestVectorFermat() {
//        System.out.println("getShortestVector Fermat");
//        int n = 55;
//        int a = 2;
//        ShortestVector sv = new ShortestVector(new Fermat(n,a));
//        System.out.println(print(sv.getShortestVector()));
//        System.out.println(print(sv.getShortestIndex()));
//        System.out.println(sum2(sv.getShortestVector()));
//
//    }

}