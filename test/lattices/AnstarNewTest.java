/*
 * AnstarNewTest.java
 * JUnit based test
 *
 * Created on 14 November 2007, 10:16
 */

package lattices;

import junit.framework.*;
import simulator.IndexedDouble;
import simulator.VectorFunctions;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Robby
 */
public class AnstarNewTest extends TestCase {
    
    public AnstarNewTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class simulator.AnstarOn.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 30;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        AnstarNew instance = new AnstarNew();
        Anstar tester = new Anstar();
        
        instance.setDimension(n - 1);
        tester.setDimension(n - 1);
        for(int i=0; i<50; i++){
            for(int k = 0; k < n; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*10.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            Anstar.project(y,x);
            System.out.println(VectorFunctions.distance_between(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
        
    }
    
}
