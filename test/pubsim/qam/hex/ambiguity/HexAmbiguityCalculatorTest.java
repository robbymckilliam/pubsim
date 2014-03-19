package pubsim.qam.hex.ambiguity;

import pubsim.qam.hex.ambiguity.HexAmbiguityCalculator;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.EisensteinInteger;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
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
        HexAmbiguityCalculator instance = new HexAmbiguityCalculator(4, 4);

        for( int N = 3; N <= 30; N++)
            System.out.println(N + " " + instance.upperBoundBLER(N));


    }

    /**
     * Test of mobiusFunction method, of class HexAmbiguityCalculator.
     */
    @Test
    public void testmobiusFunction() {
        System.out.println("mobiusFunction");
        Vector<EisensteinInteger> mset = new Vector<EisensteinInteger>();
        mset.add(new EisensteinInteger(1.0, 0.0));
        assertEquals(1, HexAmbiguityCalculator.mobiusFunction(mset));

        mset = new Vector<EisensteinInteger>();
        mset.add(new EisensteinInteger(2.0, 0.0));
        assertEquals(-1, HexAmbiguityCalculator.mobiusFunction(mset));

        mset = new Vector<EisensteinInteger>();
        mset.addAll(new EisensteinInteger(4.0, 0.0).factorise());
        assertEquals(0, HexAmbiguityCalculator.mobiusFunction(mset));

    }

}