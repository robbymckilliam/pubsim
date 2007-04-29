/*
 * AnstarTest.java
 * JUnit based test
 *
 * Created on 28 April 2007, 14:10
 */

package simulator;

import junit.framework.*;

/**
 *
 * @author Robby
 */
public class AnstarTest extends TestCase {
    
    public AnstarTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of setDimension method, of class simulator.Anstar.
     */
    public void testSetDimension() {
        System.out.println("setDimension");
        
        int n = 0;
        Anstar instance = new Anstar();
        
        instance.setDimension(n);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nearestPoint method, of class simulator.Anstar.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        double[] y = {1-1.0/5, -1.0/5, -1.0/5, -1.0/5, -1.0/5};
        Anstar instance = new Anstar();
        
        instance.nearestPoint(y);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of rotate method, of class simulator.Anstar.
     */
    public void testRotate() {
        System.out.println("rotate");
        
        int r = 0;
        Anstar instance = new Anstar();
        
        instance.rotate(r);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of project method, of class simulator.Anstar.
     */
    public void testProject() {
        System.out.println("project");
        
        double[] x = {5.0, 1.0, 1.0, 1.0};
        
        Anstar.project(x, x);
        
        double[] expResult = {3.0, -1, -1, -1};
        
        for(int i = 0; i < x.length; i++)
            assertEquals(expResult[i], x[i]);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
