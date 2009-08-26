/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.qam.hex;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simulator.Complex;

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

}