/*
 * PnoneTest.java
 * JUnit based test
 *
 * Created on 12 August 2007, 22:11
 */

package simulator;

import junit.framework.*;

/**
 *
 * @author Robby
 */
public class PnoneTest extends TestCase {
    
    public PnoneTest(String testName) {
        super(testName);
    }

    /**
     * Test of setDimension method, of class simulator.Pn1.
     */
    public void testSetDimension() {
        System.out.println("setDimension");
        
        int n = 0;
        Pn1 instance = new Pn1();
        
        instance.setDimension(n);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nearestPoint method, of class simulator.Pn1.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        double[] y = null;
        Pn1 instance = new Pn1();
        
        instance.nearestPoint(y);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of project method, of class simulator.Pn1.
     */
    public void testProject() {
        System.out.println("project");
        
        double[] x = { 1.0, 1.1, 2.0, 4.0, 5.4 };
        double[] y = new double[x.length];
        
        Pn1.project(x, y);
        
        //calculated in matlab
        double[] expResult = {0.6400, -0.4300, -0.7000, 0.1300, 0.3600};
        
        System.out.println(VectorFunctions.print(y));
        
        for(int i = 0; i < x.length; i++)
            assertEquals(true, Math.abs(expResult[i] - y[i]) < 0.000001);     
            
    }
    
}
