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

        int N = 3;
        System.out.println(N);
        System.out.println(instance.upperBoundAmbiguousCodewords(N));
        System.out.println(instance.upperBoundBLER(N));
        System.out.println();

        N = 5;
        System.out.println(N);
        System.out.println(instance.upperBoundAmbiguousCodewords(N));
        System.out.println(instance.upperBoundBLER(N));
        System.out.println();

        N = 7;
        System.out.println(N);
        System.out.println(instance.upperBoundAmbiguousCodewords(N));
        System.out.println(instance.upperBoundBLER(N));
        System.out.println();

        N = 10;
        System.out.println(N);
        System.out.println(instance.upperBoundAmbiguousCodewords(N));
        System.out.println(instance.upperBoundBLER(N));
        System.out.println();

        N = 15;
        System.out.println(N);
        System.out.println(instance.upperBoundAmbiguousCodewords(N));
        System.out.println(instance.upperBoundBLER(N));
        System.out.println();

        N = 20;
        System.out.println(N);
        System.out.println(instance.upperBoundAmbiguousCodewords(N));
        System.out.println(instance.upperBoundBLER(N));
        System.out.println();

    }

    /**
     * Test of möbiusFunction method, of class HexAmbiguityCalculator.
     */
    @Test
    public void testmöbiusFunction() {
        System.out.println("möbiusFunction");
        Vector<EinteinInteger> mset = new Vector<EinteinInteger>();
        mset.add(new EinteinInteger(1.0, 0.0));
        assertEquals(1, HexAmbiguityCalculator.möbiusFunction(mset));

        mset = new Vector<EinteinInteger>();
        mset.add(new EinteinInteger(2.0, 0.0));
        assertEquals(-1, HexAmbiguityCalculator.möbiusFunction(mset));

        mset = new Vector<EinteinInteger>();
        mset.addAll(new EinteinInteger(4.0, 0.0).factorise());
        assertEquals(0, HexAmbiguityCalculator.möbiusFunction(mset));

    }

}