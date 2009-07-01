/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import lattices.Anstar.Anstar;
import lattices.Anstar.AnstarAnGlued;
import lattices.Anstar.AnstarBucket;
import java.util.Random;
import junit.framework.TestCase;
import simulator.VectorFunctions;

/**
 *
 * @author robertm
 */
public class AnstarAnGluedTest extends TestCase {
    
    public AnstarAnGluedTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    /**
     * Test of nearestPoint method, of class AnstarAnGlued.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        int n = 8;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        Anstar tester = new AnstarBucket(n-1);
        Anstar instance = new AnstarAnGlued(n-1);
        
        //instance.setDimension(n - 1);
        //tester.setDimension(n - 1);
        for(int i=0; i<50; i++){
            for(int k = 0; k < n; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*10.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            Anstar.project(y,x);
            
            //System.out.println(VectorFunctions.distance_between2(v_instance, x));
            //System.out.println(VectorFunctions.distance_between2(v_tester, x));
            
            //System.out.println(VectorFunctions.distance_between2(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
    }

}
