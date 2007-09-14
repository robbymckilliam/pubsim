/*
 * Pn1SampledTest.java
 * JUnit based test
 *
 * Created on 18 August 2007, 17:55
 */

package simulator;

import junit.framework.*;
import java.util.Random;

/**
 *
 * @author Robby
 */
public class Pn1SampledTest extends TestCase {
    
    public Pn1SampledTest(String testName) {
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
