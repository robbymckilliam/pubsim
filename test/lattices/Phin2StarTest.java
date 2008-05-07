/*
 * Phin2StarTest.java
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
public class Phin2StarTest extends TestCase {
    
    public Phin2StarTest(String testName) {
        super(testName);
    }

    /**
     * Test of setDimension method, of class simulator.PhinStar2.
     */
    public void testSetDimension() {
        System.out.println("setDimension");
        
        int n = 0;
        Phin2Star instance = new Phin2Star();
        
        instance.setDimension(n);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nearestPoint method, of class simulator.PhinStar2.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        double[] y = null;
        Phin2Star instance = new Phin2Star();
        
        instance.nearestPoint(y);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of project method, of class simulator.PhinStar2.
     */
    public void testProject() {
        System.out.println("project");
        
        double[] x = { 1.0, 1.1, 2.0, 4.0, 5.4 };
        double[] y = new double[x.length];
        
        Phin2Star.project(x, y);
        
        //calculated in matlab
        double[] expResult = {0.6400, -0.4300, -0.7000, 0.1300, 0.3600};
        
        System.out.println(VectorFunctions.print(y));
        
        for(int i = 0; i < x.length; i++)
            assertEquals(true, Math.abs(expResult[i] - y[i]) < 0.000001);     
            
    }
    
    /**
     * Test of project method, of class simulator.PhinStar2.
     */
    public void testSumg2() {
        System.out.println("sumg2");
        
        int numtests = 100;
        double[] g;
        
        for(int i = 1; i <= numtests; i++){
            g = new double[i];
            for(int j = 0; j < i; j++)
                g[j] = j;
            Anstar.project(g, g);
            //System.out.println(VectorFunctions.print(g));
            assertEquals(VectorFunctions.sum2(g), Phin2Star.sumg2(i));
        }
            
    }
    
}
