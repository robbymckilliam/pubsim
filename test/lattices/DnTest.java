/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import junit.framework.TestCase;
import distributions.GaussianNoise;
import lattices.decoder.SphereDecoder;
import distributions.processes.IIDNoise;
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
        
        int n = 7;
        int iters = 100;
        
        GaussianNoise noise = new GaussianNoise(0.0, 1000.0);
        IIDNoise siggen = new IIDNoise();
        siggen.setNoiseGenerator(noise);
        siggen.setLength(n);
        
        Dn instance = new Dn(n);
        SphereDecoder tester = new SphereDecoder(instance);
        
        for(int i = 0; i < iters; i++){
            double[] y = siggen.generateReceivedSignal();
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            assertEquals(VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint())<0.0001, true);
        }
    }

        /**
     * Test of nearestPoint method, of class Dn.
     */
    public void testGeneratorMatrix() {
        System.out.println("testGeneratorMatrix");

        int n = 5;

        Dn instance = new Dn(n);
        Matrix G = instance.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(G));

        System.out.println(G.det());

    }


}
