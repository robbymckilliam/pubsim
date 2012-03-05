/*
 * Vn2StarSampledTest.java
 * JUnit based test
 *
 * Created on 18 August 2007, 17:55
 */

package pubsim.lattices.Vn2Star;

import java.util.Random;
import junit.framework.TestCase;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class Vn2StarSampledTest extends TestCase {
    
    public Vn2StarSampledTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class simulator.Vn2StarSampled.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 30;
        Random rand = new Random();
        
        double[] y = new double[n];
        Vn2StarSampled instance = new Vn2StarSampled(n-2, n*10);
        Vn2StarGlued tester = new Vn2StarGlued(n-2);
        
        for(int i = 0; i < 10; i++){
            for(int j=0; j<n; j++)
                y[j] = 10 * rand.nextGaussian();
            
            Vn2Star.project(y,y);
            
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
