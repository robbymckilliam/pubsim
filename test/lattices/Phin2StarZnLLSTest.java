/*
 * Phin2StarZnLLSTest.java
 * JUnit based test
 *
 * Created on 12 August 2007, 22:11
 */

package lattices;

import java.util.Random;
import junit.framework.*;
import lattices.Phin2Star.Phin2StarGlued;
import lattices.Phin2Star.Phin2StarZnLLS;
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
        int n = 5;
        Phin2StarZnLLS instance = new Phin2StarZnLLS();        
        instance.setDimension(5);
    }

    /**
     * Test of nearestPoint method, of class simulator.PhinStar2.
     */
    public void testNearestPoint() {
        
        System.out.println("nearestPoint");
        int n = 34;
        Random rand = new Random();
        
        double[] y = new double[n];
        double[] QgQ1y = new double[n];
        Phin2StarZnLLS znlls = new Phin2StarZnLLS();
        Phin2StarGlued glued = new Phin2StarGlued();
        
        znlls.setDimension(n-2);
        glued.setDimension(n-2);
        
        for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 100 * rand.nextGaussian();
        
            Phin2StarZnLLS.project(y,QgQ1y);
            
            znlls.nearestPoint(QgQ1y);
            glued.nearestPoint(QgQ1y);
            
            double diff = VectorFunctions.distance_between(znlls.getLatticePoint(), glued.getLatticePoint());

            //System.out.println(diff);
            
            assertEquals(diff < 0.000001, true);
            
            
        }
        
    }
    
}
