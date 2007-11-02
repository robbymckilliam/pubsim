/*
 * Pn2SampledTest.java
 * JUnit based test
 *
 * Created on 18 August 2007, 17:55
 */

package lattices;

import junit.framework.*;
import java.util.Random;
import simulator.*;

/**
 *
 * @author Robby
 */
public class Pn2SampledTest extends TestCase {
    
    public Pn2SampledTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class simulator.Pn2Sampled.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 11;
        Random rand = new Random();
        
        double[] y = new double[n];
        Pn2Sampled instance = new Pn2Sampled(1000);
        Pn2Glued tester = new Pn2Glued();
        
        for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 10 * rand.nextGaussian();
            
            Pn2.project(y,y);
            
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            
            double dist = VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint());
            System.out.println("dist = " + dist);
            assertEquals(dist < 0.0001, true);
            
        }

    }
    
}
