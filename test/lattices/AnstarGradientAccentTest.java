/*
 * AnstarGradientAccentTest.java
 * JUnit based test
 *
 * Created on 11 December 2007, 16:25
 */

package lattices;

import lattices.Anstar.AnstarGradientAccent;
import lattices.Anstar.AnstarVaughan;
import java.util.Random;
import junit.framework.*;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
 */
public class AnstarGradientAccentTest extends TestCase {
    
    public AnstarGradientAccentTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class lattices.AnstarGradientAccent.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 30;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        AnstarGradientAccent instance = new AnstarGradientAccent();
        AnstarVaughan tester = new AnstarVaughan();
        
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
            System.out.println(VectorFunctions.distance_between(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
    }
    
}
