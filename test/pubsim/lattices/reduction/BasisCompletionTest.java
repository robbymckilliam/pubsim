/*
 */
package pubsim.lattices.reduction;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.lattices.GeneralLattice;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;

/**
 *
 * @author Robby McKilliam
 */
public class BasisCompletionTest {

    public BasisCompletionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of notDone method, of class BasisCompletion.
     */
    @Test
    public void testWorking() {
        Matrix B = Matrix.random(3, 3);
        ShortVectorSphereDecoded svsd = new ShortVectorSphereDecoded(new GeneralLattice(B));
        BasisCompletion cb = new BasisCompletion();
        cb.completeBasis(VectorFunctions.columnMatrix(svsd.getShortestVector()), B).print(8, 2);
        cb.getUnimodularMatrix().print(8, 2);
    }
}
