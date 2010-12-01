/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import pubsim.qam.hex.HexagonalCode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.Complex;

/**
 *
 * @author harprobey
 */
public class HexagonalCodeTest {

    public HexagonalCodeTest() {
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
     * Test of encode method, of class HexagonalCode.
     */
    @Test
    public void testAveragePower() {
        System.out.println("testAveragePower");
        int M = 4;
        HexagonalCode hex = new HexagonalCode(M);

        assertTrue(hex.averagePower() < (2.0*(M*M-1.0)/3.0));
        
    }

    /**
     * Test of encode method, of class HexagonalCode.
     */
    @Test
    public void testForAmbiguity() {
        System.out.println("testAveragePower");
        int M = 4;
        HexagonalCode hex = new HexagonalCode(M);

        double[] v = hex.encode(1,1);
        Complex v11 = new Complex(v[0], v[1]);
        v = hex.encode(0,3);
        Complex v03 = new Complex(v[0], v[1]);

        v = hex.encode(2,2);
        Complex v22 = new Complex(v[0], v[1]);
        v = hex.encode(1,0);
        Complex v10 = new Complex(v[0], v[1]);

        System.out.println(v03.divides(v10).abs());
        System.out.println(v11.divides(v22).abs());

    }

}