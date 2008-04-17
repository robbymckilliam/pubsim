/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class AnstarBucketVaughanTest {

    public AnstarBucketVaughanTest() {
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
     * Test of nearestPoint method, of class AnstarBucketVaughan.
     */
    @Test
    public void nearestPoint() {
        System.out.println("nearestPoint");
        
        int numTrials = 10000;
        int n = 34;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        AnstarBucketVaughan instance = new AnstarBucketVaughan();
        AnstarVaughan tester = new AnstarVaughan();
        
        instance.setDimension(n - 1);
        tester.setDimension(n - 1);
        for(int i=0; i<numTrials; i++){
            for(int k = 0; k < n; k++){
                y[k] = rand.nextGaussian()*10.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            AnstarVaughan.project(y,x);
            //System.out.println(VectorFunctions.distance_between(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
        
        //this is actually a test for the matlab code sent to Warren Smith
        double[] yMTest = {1.1393, 10.6677, 0.5928, -0.9565, -8.3235};
        double[] Mres = {0.2000, 10.2000, 0.2000, -1.8000, -8.8000};
        instance.nearestPoint(yMTest);
        assertEquals(VectorFunctions.distance_between(Mres,  instance.getLatticePoint()) < 0.00001, true);
    }

}