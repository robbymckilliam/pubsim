/*
 * PnaEfficientTest.java
 * JUnit based test
 *
 * Created on 3 November 2007, 21:25
 */

package lattices;

import junit.framework.*;
import java.util.ArrayList;
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
     * Test of nearestPoint method, of class lattices.PnaEfficient.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        double[] y = {-1, 0, 0.1, 5, -2};
        PnaEfficient pn1 = new PnaEfficient(1);
        Anstar ans = new Anstar();
        
        pn1.nearestPoint(y);
        ans.nearestPoint(y);
        
        System.out.println(" Pn1 v = " + VectorFunctions.print(pn1.getLatticePoint()));
        System.out.println(" Anstar v = " + VectorFunctions.print(ans.getLatticePoint()));
        System.out.println(" Pn1 u = " + VectorFunctions.print(pn1.getIndex()));
        System.out.println(" Anstar u = " + VectorFunctions.print(ans.getIndex()));
        assertEquals(true, VectorFunctions.distance_between(pn1.getLatticePoint(), ans.getLatticePoint())<0.00001);
        
        double[] y1 = {-1, 0, 0.1, 5, -2};
        PnaEfficient pn2 = new PnaEfficient(2);
        Pn2Glued pn2g = new Pn2Glued();
        
        //pn2.setDimension(5-2);
        
        pn2.nearestPoint(y1);
        pn2g.nearestPoint(y1);
        
        System.out.println(" Pn1 v = " + VectorFunctions.print(pn2.getLatticePoint()));
        System.out.println(" Anstar v = " + VectorFunctions.print(pn2g.getLatticePoint()));
        System.out.println(" Pn1 u = " + VectorFunctions.print(pn2.getIndex()));
        System.out.println(" Anstar u = " + VectorFunctions.print(pn2g.getIndex()));
        assertEquals(true, VectorFunctions.distance_between(pn2.getLatticePoint(), pn2g.getLatticePoint())<0.00001);
        
    }

    /**
     * Test of project method, of class lattices.PnaEfficient.
     */
    public void testProject() {
        System.out.println("project");
        
        double[] x = {1,4,5,2,1};
        double[] y = new double[x.length];
        int a = 2;
        PnaEfficient pna = new PnaEfficient(a);
        
        //from matlab
        double[] exp = {-2, 1.2, 2.4 ,-0.4, -1.2};
        
        pna.project(x, y, a);
        double dist = VectorFunctions.distance_between(y, exp);
        System.out.println(" y = " + VectorFunctions.print(y));
        assertEquals(true, dist<0.0001);
    }
    
    /**
     * Test of volume method, of class lattices.PnaEfficient.
     */
    public void testVolume() {
        System.out.println("volume");
        
        int n = 10;
        int a = 1;
        PnaEfficient pna = new PnaEfficient(a, n-a);
        
        //Anstar det
        double expres = Math.sqrt(1.0/n);
        
        assertEquals(true, Math.abs(pna.volume() - expres)<0.0001);
        
        n = 10;
        a = 2;
        pna = new PnaEfficient(a, n-a);
        
        //from matlab
        expres = 0.198479065379550;
        
        assertEquals(true, Math.abs(pna.volume()- expres) < 0.00001);
        
        n = 22;
        a = 3;
        pna = new PnaEfficient(a, n-a);
        
        expres = 0.047564984401627;
        
        assertEquals(true, Math.abs(pna.volume()- expres));
        assertEquals(true, Math.abs(pna.volume()- expres) < 0.00001);
    }
    
}
