/*
 * Phin2StarZnLLSTest.java
 * JUnit based test
 *
 * Created on 12 August 2007, 22:11
 */

package lattices;

import java.util.Random;
import junit.framework.*;
import simulator.*;

/**
 *
 * @author Robby McKilliam
 */
public class Phin2StarZnLLSTest extends TestCase {
    
    public Phin2StarZnLLSTest(String testName) {
        super(testName);
    }

    /**
     * Test of setDimension method, of class simulator.PhinStar2.
     */
    public void testSetDimension() {
        System.out.println("setDimension");
        
        int n = 0;
        Phin2StarZnLLS instance = new Phin2StarZnLLS();
        
        instance.setDimension(n);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of nearestPoint method, of class simulator.PhinStar2.
     */
    public void testNearestPoint() {
        
        System.out.println("nearestPoint");
        int n = 9;
        Random rand = new Random();
        
        double[] y = new double[n];
        Phin2StarZnLLS instance = new Phin2StarZnLLS();
        Phin2StarSampled tester = new Phin2StarSampled(5000000);
        //Phin2StarGlued tester = new Phin2StarGlued();
        
        instance.setDimension(n-2);
        tester.setDimension(n-2);
        
       // for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 9 * rand.nextGaussian();
            
            Phin2StarZnLLS.project(y,y);
            
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            
            System.out.println("inst pt: " + VectorFunctions.print(instance.getLatticePoint()));
            System.out.println("test pt: " + VectorFunctions.print(tester.getLatticePoint()));
            
            System.out.println("inst quant: " + VectorFunctions.print(instance.getIndex()));
            System.out.println("test quant: " + VectorFunctions.print(tester.getIndex()));

            System.out.println("inst dist: " + VectorFunctions.distance_between(instance.getLatticePoint(), instance.getIndex()));
            System.out.println("test dist: " + VectorFunctions.distance_between(tester.getLatticePoint(), tester.getIndex()));
            
            double dist = VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint());
            System.out.println("inst = " + VectorFunctions.distance_between(instance.getLatticePoint(), y));
            System.out.println("test = " + VectorFunctions.distance_between(tester.getLatticePoint(), y));
            assertEquals(dist < 0.0001, true);
            
            
        //}
        
        /*
        System.out.println("nearestPoint");
        
        double[] y = null;
        Phin2StarZnLLS instance = new Phin2StarZnLLS();
        
        instance.nearestPoint(y);
        */
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
