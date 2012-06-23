/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices;

import Jama.Matrix;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.Util;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class VnmTest {

    public VnmTest() {
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
     * Test of logVolume method, of class Vnm.
     */
    @Test
    public void testLogVolume() {
        System.out.println("logVolume");
        Vnm instance = new Vnm(0, 12);
        Matrix gen = instance.getGeneratorMatrix();
        Matrix gram = gen.transpose().times(gen);

        //System.out.println(VectorFunctions.print(gen));

        assertEquals(Math.sqrt(gram.det()), instance.volume(), 0.0001);
    }

    /**
     * Test of logVolume method, of class Vnm.
     */
    @Test
    public void testPrintOutShortVects() {
        System.out.println("print out short vectors");
        int maxn = 15;
        int m = 2;
        for (int n = 1; n < maxn; n=n+1) {
            Vnm instance = new Vnm(m, n);
            System.out.println(n + ", " + instance.kissingNumber() + ", " + Math.pow(2*instance.inradius(),2));
        }
    }
    
    /**
     * Test of Vn0.
     */
    @Test
    public void testVn0() {
        System.out.println("test Vn0");
        int minn = 1;
        int maxn = 50;
        int m = 0;
        for(int n = minn; n < maxn; n++){
            Vnm.Vn0 testee = new Vnm.Vn0(n);
            Vnm tester = new Vnm(m,n);
            assertEquals(testee.volume(), tester.volume(), 0.00001);
            assertEquals(testee.kissingNumber(), tester.kissingNumber());
        }
    }
    
     /**
     * Test of Vn1.
     */
    @Test
    public void testVn1() {
        System.out.println("test Vn1");
        int minn = 2;
        int maxn = 60;
        int m = 1;
        for(int n = minn; n < maxn; n=n+1){
            Vnm.Vn1 testee = new Vnm.Vn1(n);
            Vnm tester = new Vnm(m,n);
            assertEquals(testee.volume(), tester.volume(), 0.00001);
            //System.out.println(testee.kissingNumber() + ", " + tester.kissingNumber());
            assertEquals(testee.kissingNumber(), tester.kissingNumber());
        }
    }
    
    /**
     * Test ProbCodingError.
     */
    @Test
    public void testProbCodingError() {
        System.out.println("ProbCodingError");
        Vnm instance = new Vnm(1, 12);
        Matrix gen = instance.getGeneratorMatrix();
        Matrix gram = gen.transpose().times(gen);
        for(double S = 0.1; S < 5; S+=0.1) System.out.println(instance.probCodingError(S));
    }
    
    
    

//    /**
//     * Test of logVolume method, of class Vnm.
//     */
//    @Test
//    public void searchForSquares() {
//        System.out.println("search for squares");
//        int m = 1;
//        for (int n = m + 1; n < 1000; n++) {
//            double vol = (new Vnm(4, n)).volume();
//            String marker = "";
//            if (Math.abs(vol - Math.round(vol)) < 0.000001) {
//                marker = "******";
//            }
//            System.out.println(n + "\t" + vol + "\t" + marker);
//        }
//
//    }
}