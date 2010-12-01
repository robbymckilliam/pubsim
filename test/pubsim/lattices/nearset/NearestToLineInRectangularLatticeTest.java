/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.nearset;

import pubsim.lattices.nearset.NearestInZnToLine;
import pubsim.lattices.nearset.NearestToLineInRectangularLattice;
import pubsim.lattices.nearset.NSphereForLines;
import pubsim.lattices.nearset.RegionForLines;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static pubsim.VectorFunctions.ones;
import static pubsim.VectorFunctions.randomGaussian;
import static pubsim.VectorFunctions.columnMatrix;
import static robbysim.VectorFunctionsTest.assertVectorsEqual;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class NearestToLineInRectangularLatticeTest {

    public NearestToLineInRectangularLatticeTest() {
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
     * Test of compute method, of class NearestToLineInRectangularLattice.
     */
    @Test
    public void workWithIdentity() {
        System.out.println("workWithIdentity");
        int N = 10;
        double[] d = ones(N);

        double[] m = randomGaussian(N);
        double[] c = randomGaussian(N);
        RegionForLines R = new NSphereForLines(1, c);

        NearestInZnToLine test = new NearestInZnToLine(columnMatrix(m), R);
        test.compute(c, m, -10.0, 10.0);

        NearestToLineInRectangularLattice inst = new NearestToLineInRectangularLattice(columnMatrix(m), R, d);
        inst.compute(c, m, -10.0, 10.0);

        double[] uinst = inst.nearestPoint();
        double rinst = inst.nearestParam();
        double[] utest = test.nearestPoint();
        double rtest = test.nearestParam();
        assertEquals(rtest, rinst, 0.00000001);
        assertVectorsEqual(utest, uinst);

    }



}