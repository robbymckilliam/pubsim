/*
 * PnaEfficientTest.java
 * JUnit based test
 *
 * Created on 3 November 2007, 21:25
 */

package lattices;

import lattices.Anstar.AnstarVaughan;
import Jama.Matrix;
import java.util.Random;
import javax.vecmath.Vector2d;
import junit.framework.*;
import simulator.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class PnaEfficientTest extends TestCase {
    
    public PnaEfficientTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class lattices.PhinaStarEfficient.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        // test vs An*
        double[] y = {-1, 0, 0.1, 5, -2};
        PhinaStarEfficient pn1 = new PhinaStarEfficient(1);
        AnstarVaughan ans = new AnstarVaughan();
        
        pn1.nearestPoint(y);
        ans.nearestPoint(y);
        
        System.out.println(" Pn1 v = " + VectorFunctions.print(pn1.getLatticePoint()));
        System.out.println(" Anstar v = " + VectorFunctions.print(ans.getLatticePoint()));
        System.out.println(" Pn1 u = " + VectorFunctions.print(pn1.getIndex()));
        System.out.println(" Anstar u = " + VectorFunctions.print(ans.getIndex()));
        assertEquals(true, VectorFunctions.distance_between(pn1.getLatticePoint(), ans.getLatticePoint())<0.00001);
        
        
        // test vs glue vector algorithm for Frequency estimation lattice
        double[] y1 = {-1, 0, 0.1, 5, -2};
        PhinaStarEfficient pn2 = new PhinaStarEfficient(2);
        Phin2StarGlued pn2g = new Phin2StarGlued();
        
        //pn2.setDimension(5-2);
        
        pn2.nearestPoint(y1);
        pn2g.nearestPoint(y1);
        
        System.out.println(" Pn1 v = " + VectorFunctions.print(pn2.getLatticePoint()));
        System.out.println(" Anstar v = " + VectorFunctions.print(pn2g.getLatticePoint()));
        System.out.println(" Pn1 u = " + VectorFunctions.print(pn2.getIndex()));
        System.out.println(" Anstar u = " + VectorFunctions.print(pn2g.getIndex()));
        assertEquals(true, VectorFunctions.distance_between(pn2.getLatticePoint(), pn2g.getLatticePoint())<0.00001);
        
        //run nearest point test by making small deviations (del) to lattice points.
        int iters = 10;
        Random r = new Random();
        int a = 3;
        double del = 0.0001;
        for(int t = 0; t < iters; t++){
            int n = r.nextInt(10) + 5;
            PhinaStarEfficient pna = new PhinaStarEfficient(a, n-a);
            Matrix G = pna.getGeneratorMatrix();
            
           // System.out.println("G is " + G.getRowDimension() + " by " + G.getColumnDimension());
            double[] x = new double[G.getRowDimension()];
            double[] xdel = new double[G.getRowDimension()];
            double[] u = VectorFunctions.randomIntegerVector(n-a, 1000);
            
            //System.out.println("u is length " + u.length + ", x is length"  + x.length);
            
            VectorFunctions.matrixMultVector(G, u, x);
            for(int i = 0; i < x.length; i++){
                xdel[i] = x[i] +  r.nextGaussian()*del;
            }
            pna.nearestPoint(xdel);
            assertEquals(true, VectorFunctions.distance_between(pna.getLatticePoint(), x)<0.00001);
            
        }
        
        
    }

    /**
     * Test of project method, of class lattices.PhinaStarEfficient.
     */
    public void testProject() {
        System.out.println("project");
        
        double[] x = {1,4,5,2,1};
        double[] y = new double[x.length];
        int a = 2;
        
        //from matlab
        double[] exp = {-2, 1.2, 2.4 ,-0.4, -1.2};
        
        PhinaStarEfficient.project(x, y, a);
        double dist = VectorFunctions.distance_between(y, exp);
        System.out.println(" y = " + VectorFunctions.print(y));
        assertEquals(true, dist<0.0001);
    }
    
    /**
     * Test of volume method, of class lattices.PhinaStarEfficient.
     */
    public void testVolume() {
        System.out.println("volume");
        
        int n = 10;
        int a = 1;
        PhinaStarEfficient pna = new PhinaStarEfficient(a, n-a);
        
        //AnstarVaughan det
        double expres = Math.sqrt(1.0/n);
        
        assertEquals(true, Math.abs(pna.volume() - expres)<0.0001);
        
        n = 10;
        a = 2;
        pna = new PhinaStarEfficient(a, n-a);
        
        //from matlab
        expres = 0.198479065379550;
        
        assertEquals(true, Math.abs(pna.volume()- expres) < 0.00001);
        
        n = 22;
        a = 3;
        pna = new PhinaStarEfficient(a, n-a);
        
        expres = 0.047564984401627;
        
        //assertEquals(true, Math.abs(pna.volume()- expres));
        assertEquals(true, Math.abs(pna.volume()- expres) < 0.00001);
    }
    
        /**
     * Test of inradius method, of class lattices.PhinaStarEfficient.
     */
    public void testInradius() {
        System.out.println("inradius");
        
        int n = 10;
        int a = 1;
        PhinaStarEfficient pna = new PhinaStarEfficient(a, n-a);
        
        //AnstarVaughan det
        double expres = Math.sqrt(1.0 - 1.0/n);
        
        assertEquals(true, Math.abs(pna.inradius() - expres)<0.0001);
        
    }
    
    
    
}
