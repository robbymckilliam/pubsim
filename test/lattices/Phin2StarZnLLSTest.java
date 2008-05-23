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
        double[] sampled_proj_u = new double[n];
        double[] glued_proj_u = new double[n];
        Phin2StarZnLLS znlls = new Phin2StarZnLLS();
        Phin2StarSampled sampled = new Phin2StarSampled(100);
        Phin2StarGlued glued = new Phin2StarGlued();
        
        znlls.setDimension(n-2);
        sampled.setDimension(n-2);
        glued.setDimension(n-2);
        
        for(int i = 0; i < 5000; i++){
            for(int j=0; j<n; j++)
                y[j] = 10 * rand.nextGaussian();
        
            //System.out.println("y: " + VectorFunctions.print(y));
            
            Phin2StarZnLLS.project(y,QgQ1y);
            
            znlls.nearestPoint(QgQ1y);
            sampled.nearestPoint(QgQ1y);
            glued.nearestPoint(QgQ1y);
            /*
            System.out.println("QgQ1y: " + VectorFunctions.print(QgQ1y));
            
            System.out.println("znlls v: " + VectorFunctions.print(znlls.getLatticePoint()));
            System.out.println("sampled v: " + VectorFunctions.print(sampled.getLatticePoint()));
            System.out.println("glued v: " + VectorFunctions.print(glued.getLatticePoint()));
            
            System.out.println("znlls u: " + VectorFunctions.print(znlls.getIndex()));
            System.out.println("sampled u: " + VectorFunctions.print(sampled.getIndex()));
            System.out.println("glued u: " + VectorFunctions.print(glued.getIndex()));
            System.out.println();

            System.out.println("|v - u| znlls: " + VectorFunctions.distance_between(znlls.getLatticePoint(), znlls.getIndex()));
            System.out.println("|v - u| sampled: " + VectorFunctions.distance_between(sampled.getLatticePoint(), sampled.getIndex()));
            System.out.println("|v - u| glued: " + VectorFunctions.distance_between(glued.getLatticePoint(), glued.getIndex()));
            System.out.println();
            
            System.out.println("|QgQ1y - v| znlls = " + VectorFunctions.distance_between(znlls.getLatticePoint(), QgQ1y));
            System.out.println("|QgQ1y - v| sampled = " + VectorFunctions.distance_between(sampled.getLatticePoint(), QgQ1y));
            System.out.println("|QgQ1y - v| glued = " + VectorFunctions.distance_between(glued.getLatticePoint(), QgQ1y));
            System.out.println();
            
            System.out.println("|QgQ1y - u| znlls: " + VectorFunctions.distance_between(QgQ1y, znlls.getIndex()));
            System.out.println("|QgQ1y - u| sampled: " + VectorFunctions.distance_between(QgQ1y, sampled.getIndex()));
            System.out.println("|QgQ1y - u| glued: " + VectorFunctions.distance_between(QgQ1y, glued.getIndex()));
            System.out.println();
            */
            Phin2StarZnLLS.project(znlls.getIndex(), znlls_proj_u);
            Phin2StarZnLLS.project(sampled.getIndex(), sampled_proj_u);
            Phin2StarZnLLS.project(glued.getIndex(), glued_proj_u);
            
            System.out.println("QgQ1u znlls: " + VectorFunctions.print(znlls_proj_u));
            System.out.println("QgQ1u sampled: " + VectorFunctions.print(sampled_proj_u));
            System.out.println("QgQ1u glued: " + VectorFunctions.print(glued_proj_u));
            System.out.println();
            
            System.out.println("|QgQ1(y - u)| znlls: " + VectorFunctions.distance_between(QgQ1y, znlls_proj_u));
            System.out.println("|QgQ1(y - u)| sampled: " + VectorFunctions.distance_between(QgQ1y, sampled_proj_u));
            System.out.println("|QgQ1(y - u)| glued: " + VectorFunctions.distance_between(QgQ1y, glued_proj_u));
            
            System.out.println(i);
            
            double dist = VectorFunctions.distance_between(znlls.getLatticePoint(), sampled.getLatticePoint());
            
            double diff = VectorFunctions.distance_between(znlls_proj_u, sampled_proj_u);
            
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
