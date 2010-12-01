/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder.nearset;

import pubsim.lattices.decoder.nearset.SampledLine;
import pubsim.lattices.decoder.nearset.ZnLine;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarBucketVaughan;
import pubsim.lattices.Zn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.VectorFunctionsTest;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class ZnLineTest {

    public ZnLineTest() {
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
     * Test of getLatticePoint method, of class ZnLine.
     */
    @Test
    public void testGetLatticePointAn() {
        System.out.println("testAsNearestPointAn*");

        for(int i = 0; i < 1000; i++){

            int N = 51;
            double[] c = VectorFunctions.randomGaussian(N, 10, 100);
            double[] y = VectorFunctions.ones(N);
            double rmin = 0;
            double rmax = 1;

            ZnLine zn = new ZnLine(y, c, 0, 1);
            AnstarBucketVaughan anstar = new AnstarBucketVaughan(N-1);
            anstar.nearestPoint(c);

            double[] yp = new double[N];
            Anstar.project(zn.getLatticePoint(), yp);

            VectorFunctionsTest.assertVectorsEqual(yp, anstar.getLatticePoint());

        }

    }

        /**
     * Test of getLatticePoint method, of class ZnLine.
     */
    @Test
    public void testGetLatticePointSampled() {
        System.out.println("testAsNearestPointSampled");

        for(int i = 0; i < 1000; i++){

            int N = 51;
            double[] c = VectorFunctions.randomGaussian(N, 10, 100);
            double[] y = VectorFunctions.randomGaussian(N, 0.0, 1.0);
            double rmin = -10;
            double rmax = 11.0;

            ZnLine zn = new ZnLine(y, c, rmin, rmax);
            SampledLine smp = new SampledLine(y, c, rmin, rmax, 5000, new Zn(N));

            VectorFunctionsTest.assertVectorsEqual(zn.getLatticePoint(), smp.getLatticePoint());
            assertEquals(smp.getLambda(), zn.getLambda(), 0.00001);

        }

    }

}