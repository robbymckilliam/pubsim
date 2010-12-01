/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.quantisedFTs;

import pubsim.quantisedFTs.Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.Complex;
import static pubsim.VectorFunctions.print;
import static pubsim.ComplexMatrix.toComplexArray;


/**
 *
 * @author Robby McKilliam
 */
public class UtilTest {

    public UtilTest() {
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
     * Test of DFTMatrix method, of class Util.
     */
    @Test
    public void testDFTMatrix() {
        System.out.println("DFTMatrix");
        int N = 8;
        System.out.println(print(Util.DFTMatrix(N)));
    }

    /**
     * Test of DFTMatrix method, of class Util.
     */
    @Test
    public void testquantisedDFTMatrix() {
        System.out.println("quantisedDFTMatrix");
        int N = 12;
        int M = 8;
        System.out.println(print(Util.quantisedDFTMatrix(N, M)));
    }


    /**
     * Test Channel matrix code.
     */
    @Test
    public void testLTIChannelMatrix() {
        System.out.println("LTIChannelMatrix");
        double[] hr = {2,1,1,0,0,0,0,0};
        double[] hi = {-1,0,0,0,0,0,0,0};
        Complex[] h = toComplexArray(hr,hi);
        Complex[][] H = Util.circulantChannelMatrix(h);
        System.out.println(print(H));
    }

}