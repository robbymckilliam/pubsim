/*
 * PnaTest.java
 * JUnit based test
 *
 * Created on 2 November 2007, 14:19
 */

package lattices;

import junit.framework.*;
import simulator.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class PnaTest extends TestCase {
    
    public PnaTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of nearestPoint method, of class lattices.Pna.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        double[] y = {-1, 0, 0.1, 5, -2};
        Pna pn1 = new Pna(1);
        Anstar ans = new Anstar();
        
        pn1.nearestPoint(y);
        ans.nearestPoint(y);
        
        System.out.println(" Pn1 v = " + VectorFunctions.print(pn1.getLatticePoint()));
        System.out.println(" Anstar v = " + VectorFunctions.print(ans.getLatticePoint()));
        System.out.println(" Pn1 u = " + VectorFunctions.print(pn1.getIndex()));
        System.out.println(" Anstar u = " + VectorFunctions.print(ans.getIndex()));
        assertEquals(true, VectorFunctions.distance_between(pn1.getLatticePoint(), ans.getLatticePoint())<0.00001);
        
        double[] y1 = {-1, 0, 0.1, 5, -2};
        Pna pn2 = new Pna(2);
        Pn2Glued pn2g = new Pn2Glued();
        
        pn2.nearestPoint(y1);
        pn2g.nearestPoint(y1);
        
        System.out.println(" Pn1 v = " + VectorFunctions.print(pn2.getLatticePoint()));
        System.out.println(" Anstar v = " + VectorFunctions.print(pn2g.getLatticePoint()));
        System.out.println(" Pn1 u = " + VectorFunctions.print(pn2.getIndex()));
        System.out.println(" Anstar u = " + VectorFunctions.print(pn2g.getIndex()));
        assertEquals(true, VectorFunctions.distance_between(pn2.getLatticePoint(), pn2g.getLatticePoint())<0.00001);
        
        
    }

    /**
     * Test of createg method, of class lattices.Pna.
     */
    public void testCreateg() {
        System.out.println("createg");
        
        int n = 5;
        int a = 2;
        
        double[] expResult = {-2, -1, 0, 1, 2};
        double[] result = Pna.createg(n-a, a);
        System.out.println("g = " + VectorFunctions.print(result));
        assertEquals(true, VectorFunctions.distance_between(expResult, result)<0.00001);
        
        n = 8;
        a = 3;
        
        double[] expResult2 = {7, 1, -3, -5, -5, -3, 1, 7};
        double[] result2 = Pna.createg(n-a, a);
        System.out.println("g = " + VectorFunctions.print(result2));
        assertEquals(true, VectorFunctions.distance_between(expResult2, result2)<0.00001);
        
    }
    
    /**
     * Test of generateRotationMatrix method, of class lattices.Pna.
     */
    public void testGenerateRotationMatrix() {
        System.out.println("generateRotationMatrix");
        
        int n = 11;
        int a = 4;
        
        double[][] mat = VectorFunctions.transpose(Pna.generateRotationMatrix(n-a, a));
        System.out.println("mat = " + VectorFunctions.print(VectorFunctions.transpose(mat)));
        for(int i = 0; i < n - a; i++){
            for(int j = 1; j <= a; j++)
            assertEquals(true, Math.abs(VectorFunctions.dot(mat[i], Pna.createg(n-j,j)))<0.00001);
        }
        
    }

    /**
     * Test of project method, of class lattices.Pna.
     */
    public void testProject() {
        System.out.println("project");
        
        double[] x = {1,4,5,2,1};
        double[] y = new double[x.length];
        int a = 2;
        
        //from matlab
        double[] exp = {-2, 1.2, 2.4 ,-0.4, -1.2};
        
        Pna.project(x, y, a);
        double dist = VectorFunctions.distance_between(y, exp);
        System.out.println(" y = " + VectorFunctions.print(y));
        assertEquals(true, dist<0.0001);
        
    }
    
}
