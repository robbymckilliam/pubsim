/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import pubsim.lattices.VnmStarSampled;
import pubsim.lattices.VnmStar;
import java.util.Date;
import java.util.Random;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.decoder.SphereDecoder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class VnmStarSampledTest {

    public VnmStarSampledTest() {
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

    @Test
    public void testNearestPointSphereDecoder() {
        System.out.println("testNearestPointSphereDecoder");
        int n = 256;
        int m = 2;
        int N = n + m + 1;
        int[] samples = {6*N, 12*N};

        Random rand = new Random();
        double[] y = new double[N];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[N];
        
        VnmStarSampled instance = new VnmStarSampled(m, n, samples);
        SphereDecoder tester = new SphereDecoder(instance);

        //instance.setDimension(n - 1);
        //tester.setDimension(n - 1);
        for(int i=0; i<100; i++){
            for(int k = 0; k < N; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*100.0;
            }
            long now = (new Date()).getTime();
            instance.nearestPoint(y);
            System.out.println("time = " + (((new Date()).getTime() - now)/1000.0));
            now = (new Date()).getTime();
            tester.nearestPoint(y);
            System.out.println("sd time = " + (((new Date()).getTime() - now)/1000.0));
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();

            //System.out.println(VectorFunctions.distance_between2(v_instance, x));
            //System.out.println(VectorFunctions.distance_between2(v_tester, x));

            System.out.println(VectorFunctions.distance_between2(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
    }

}