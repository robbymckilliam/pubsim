/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import junit.framework.TestCase;
import simulator.GaussianNoise;
import simulator.NoiseVector;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
 */
public class DnTest extends TestCase {
    
    public DnTest(String testName) {
        super(testName);
    }            

    /**
     * Test of nearestPoint method, of class Dn.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 3;
        int iters = 100;
        
        //this test using the fact that A_3 = D_3, but need to rotate
        //the point in A_3 into a space in R^4 to test.
        
        GaussianNoise noise = new GaussianNoise(0.0, 1000.0);
        NoiseVector siggen = new NoiseVector();
        siggen.setNoiseGenerator(noise);
        siggen.setLength(n);
        
        Dn instance = new Dn();
        AnSorted tester = new AnSorted();
        
        for(int i = 0; i < iters; i++){
            double[] y = siggen.generateReceivedSignal();
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            assertEquals(VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint())<0.0001, true);
        }
    }

}
