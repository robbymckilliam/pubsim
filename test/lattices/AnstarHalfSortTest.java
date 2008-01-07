/*
 * AnstarHalfSortTest.java
 * JUnit based test
 *
 * Created on 7 January 2008, 14:33
 */

package lattices;

import java.util.Random;
import junit.framework.*;
import simulator.VectorFunctions;

/**
 *
 * @author robertm
 */
public class AnstarHalfSortTest extends TestCase {
    
    public AnstarHalfSortTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of nearestPoint method, of class lattices.AnstarHalfSort.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 5;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        AnstarHalfSort instance = new AnstarHalfSort();
        Anstar tester = new Anstar();
        
        instance.setDimension(n - 1);
        tester.setDimension(n - 1);
        for(int i=0; i<50; i++){
            for(int k = 0; k < n; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*10.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            Anstar.project(y,x);
            System.out.println(VectorFunctions.distance_between(v_instance, v_tester));
            
            System.out.println(VectorFunctions.print(instance.getIndex()));
            System.out.println(VectorFunctions.print(tester.getIndex()));
            
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
           
        }
    }
    
}
