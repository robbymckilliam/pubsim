/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.Vn2Star;

import robbysim.lattices.Vn2Star.Vn2StarZnLLS;
import robbysim.lattices.Vn2Star.Vn2StarRelVecApprox;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import robbysim.VectorFunctions;

/**
 *
 * @author harprobey
 */
public class Vn2StarRelVecApproxTest {

    public Vn2StarRelVecApproxTest() {
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
     * Test of nearestPoint method, of class Vn2StarRelVecApprox.
     */
    @Test
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        int n = 50;
        Random rand = new Random();

        double[] y = new double[n];
        double[] QgQ1y = new double[n];
        Vn2StarZnLLS znlls = new Vn2StarZnLLS();
        Vn2StarRelVecApprox relvec = new Vn2StarRelVecApprox();

        znlls.setDimension(n-2);
        relvec.setDimension(n-2);

        for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 100.0 * rand.nextGaussian();

            Vn2StarZnLLS.project(y,QgQ1y);

            znlls.nearestPoint(QgQ1y);
            relvec.nearestPoint(QgQ1y);

            double diff = VectorFunctions.distance_between(znlls.getLatticePoint(), relvec.getLatticePoint());

            System.out.println(diff);
            System.out.println();

            //assertEquals(diff < 0.000001, true);
        }
    }

}