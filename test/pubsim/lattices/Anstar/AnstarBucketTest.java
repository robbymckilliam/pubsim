/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anstar;

import pubsim.lattices.Anstar.AnstarBucket;
import pubsim.lattices.Anstar.AnstarVaughan;
import java.util.Random;
import junit.framework.TestCase;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class AnstarBucketTest extends TestCase {
    
    public AnstarBucketTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of nearestPoint method, of class AnstarBucket.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int numTrials = 10000;
        int n = 34;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        AnstarBucket instance = new AnstarBucket(n-1);
        AnstarVaughan tester = new AnstarVaughan(n-1);
        
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
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.000001, true);
        }
        

    }

}
