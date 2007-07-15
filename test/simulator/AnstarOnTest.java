/*
 * AnstarOnTest.java
 * JUnit based test
 *
 * Created on 7 July 2007, 09:46
 */

package simulator;

import junit.framework.*;
import java.util.Random;

/**
 *
 * @author Robby
 */
public class AnstarOnTest extends TestCase {
    
    public AnstarOnTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class simulator.AnstarOn.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 10;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        AnstarOn instance = new AnstarOn();
        Anstar tester = new Anstar();
        
        instance.setDimension(n - 1);
        tester.setDimension(n - 1);
        for(int i=0; i<=1000; i++){
            for(int k = 0; k < n; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*10.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getv();
            v_tester = tester.getv();
            Anstar.project(y,x);
            System.out.println("y = " + VectorFunctions.print(y));
            System.out.println("v_i = " + VectorFunctions.print(v_instance));
            System.out.println("v_t = " + VectorFunctions.print(v_tester));
            System.out.println("u_i = " + VectorFunctions.print(instance.getu()));
            System.out.println("u_t = " + VectorFunctions.print(tester.getu()));
            System.out.println("v_i dist = " + VectorFunctions.distance_between(x, v_instance));
            System.out.println("v_t dist = " + VectorFunctions.distance_between(x, v_tester));
            System.out.println();  
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.01, true);
        }
        
    }
    
}
