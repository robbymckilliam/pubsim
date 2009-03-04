/*
 * Phin2StarSampledTest.java
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
 * @author Robby McKilliam
 */
public class Phin2StarSampledTest extends TestCase {
    
    public Phin2StarSampledTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class simulator.Phin2StarSampled.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 20;
        Random rand = new Random();
        
        double[] y = new double[n];
        Phin2StarSampled instance = new Phin2StarSampled();
        Phin2StarGlued tester = new Phin2StarGlued();
        
        for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 10 * rand.nextGaussian();
            
            Phin2StarZnLLSOld.project(y,y);
            
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            
            double dist = VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint());
            
            System.out.println("inst = " + VectorFunctions.print(instance.getIndex()));
            System.out.println("test = " + VectorFunctions.print(tester.getIndex()));
            double[] s = VectorFunctions.subtract(instance.getIndex(), tester.getIndex());
            System.out.println("diff = " + VectorFunctions.print(s));
            
            System.out.println("inst = " + VectorFunctions.distance_between(instance.getLatticePoint(), y));
            System.out.println("test = " + VectorFunctions.distance_between(tester.getLatticePoint(), y));
            assertEquals(dist < 0.0001, true);
            
        }

    }
    
}
