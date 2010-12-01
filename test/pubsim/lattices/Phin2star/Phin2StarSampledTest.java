/*
 * Phin2StarSampledTest.java
 * JUnit based test
 *
 * Created on 18 August 2007, 17:55
 */

package robbysim.lattices.Phin2star;

import pubsim.lattices.Vn2Star.Vn2Star;
import pubsim.lattices.Vn2Star.Vn2StarSampled;
import pubsim.lattices.Vn2Star.Vn2StarGlued;
import junit.framework.*;
import java.util.Random;
import pubsim.*;

/**
 *
 * @author Robby McKilliam
 */
public class Phin2StarSampledTest extends TestCase {
    
    public Phin2StarSampledTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class simulator.Vn2StarSampled.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 256;
        Random rand = new Random();
        
        double[] y = new double[n];
        Vn2StarSampled instance = new Vn2StarSampled();
        Vn2StarGlued tester = new Vn2StarGlued();
        
        for(int i = 0; i < 100; i++){
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
