/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.nearset;

import lattices.Anstar.Anstar;
import lattices.Anstar.AnstarBucketVaughan;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import simulator.VectorFunctionsTest;

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
    public void testGetLatticePoint() {
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

}