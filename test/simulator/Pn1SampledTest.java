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
     * Test of nearestPoint method, of class simulator.Pn1Sampled.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 10;
        Random rand = new Random();
        
        double[] y = new double[10];
        Pn1Sampled instance = new Pn1Sampled(1000);
        Pn1Glued tester = new Pn1Glued();
        
        for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 10 * rand.nextGaussian();
            
            Pn1.project(y,y);
            
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            
            double dist = VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint());
            System.out.println("dist = " + dist);
            assertEquals(dist < 5, true);
            
        }

    }
    
}
