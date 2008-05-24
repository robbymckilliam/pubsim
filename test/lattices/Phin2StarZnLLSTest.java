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
        int n = 10;
        Random rand = new Random();
        
        double[] y = new double[n];
        double[] QgQ1y = new double[n];
        double[] znlls_proj_u = new double[n];
        double[] glued_proj_u = new double[n];
        Phin2StarZnLLS znlls = new Phin2StarZnLLS();
        Phin2StarGlued glued = new Phin2StarGlued();
        
        znlls.setDimension(n-2);
        glued.setDimension(n-2);
        
        for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 10 * rand.nextGaussian();
        
            Phin2StarZnLLS.project(y,QgQ1y);
            
            znlls.nearestPoint(QgQ1y);
            glued.nearestPoint(QgQ1y);
            
            double diff = VectorFunctions.distance_between(znlls_proj_u, glued_proj_u);
            
            assertEquals(diff < 0.0001, true);
            
            
        }
        
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
