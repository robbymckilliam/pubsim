/*
 * Phin2Test.java
 * JUnit based test
 *
 * Created on 12 August 2007, 22:11
 */

package lattices;

import junit.framework.*;
import simulator.*;

/**
 *
 * @author Robby McKilliam
 */
public class Phin2Test extends TestCase {
    
    public Phin2Test(String testName) {
        super(testName);
    }

    /**
     * Test of setDimension method, of class simulator.Phin2.
     */
    public void testSetDimension() {
        System.out.println("setDimension");
        
        int n = 0;
        Phin2 instance = new Phin2();
        
        instance.setDimension(n);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nearestPoint method, of class simulator.Phin2.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        double[] y = null;
        Phin2 instance = new Phin2();
        
        instance.nearestPoint(y);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of project method, of class simulator.Phin2.
     */
    public void testProject() {
        System.out.println("project");
        
        double[] x = { 1.0, 1.1, 2.0, 4.0, 5.4 };
        double[] y = new double[x.length];
        
        Phin2.project(x, y);
        
        //calculated in matlab
        double[] expResult = {0.6400, -0.4300, -0.7000, 0.1300, 0.3600};
        
        System.out.println(VectorFunctions.print(y));
        
        for(int i = 0; i < x.length; i++)
            assertEquals(true, Math.abs(expResult[i] - y[i]) < 0.000001);     
            
    }
    
}