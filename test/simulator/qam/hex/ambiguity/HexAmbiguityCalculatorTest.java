/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.qam.hex.ambiguity;

import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.EinteinInteger;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class HexAmbiguityCalculatorTest {

    public HexAmbiguityCalculatorTest() {
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
     * Test of upperBoundAmbiguousCodewords method, of class HexAmbiguityCalculator.
     */
    @Test
    public void testUpperBoundAmbiguousCodewords() {
        System.out.println("upperBoundAmbiguousCodewords");
        HexAmbiguityCalculator instance = new HexAmbiguityCalculator(16, 16);

        for( int N = 3; N <= 30; N++)
            System.out.println(N + " " + instance.upperBoundBLER(N));


    }

    /**
     * Test of mobiusFunction method, of class HexAmbiguityCalculator.
     */
    @Test
    public void testmobiusFunction() {
        System.out.println("mobiusFunction");
        Vector<EinteinInteger> mset = new Vector<EinteinInteger>();
        mset.add(new EinteinInteger(1.0, 0.0));
        assertEquals(1, HexAmbiguityCalculator.mobiusFunction(mset));

        mset = new Vector<EinteinInteger>();
        mset.add(new EinteinInteger(2.0, 0.0));
        assertEquals(-1, HexAmbiguityCalculator.mobiusFunction(mset));

        mset = new Vector<EinteinInteger>();
        mset.addAll(new EinteinInteger(4.0, 0.0).factorise());
        assertEquals(0, HexAmbiguityCalculator.mobiusFunction(mset));

    }

}