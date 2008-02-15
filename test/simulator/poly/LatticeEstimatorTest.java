/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import junit.framework.TestCase;
import simulator.GaussianNoise;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
 */
public class LatticeEstimatorTest extends TestCase {
    
    public LatticeEstimatorTest(String testName) {
        super(testName);
    }            


    /**
     * Test of estimate method, of class LatticeEstimator.
     */
    public void testEstimate() {
        System.out.println("estimate");
                
        int n = 6;
        double[] params = {0.12, 0.15, -0.4};
        
        PolynomialPhaseSignal gen = new PolynomialPhaseSignal();
        gen.setLength(n);
        gen.setParameters(params);
        
        GaussianNoise noise = new GaussianNoise(0, 0.00001);
        gen.setNoiseGenerator(noise);
        
        gen.generateReceivedSignal();
        
        LatticeEstimator instance = new LatticeEstimator(params.length);
        double[] res = instance.estimate(gen.getReal(), gen.getImag());
        
        System.out.println("res = " + VectorFunctions.print(res));
        
        assertEquals( VectorFunctions.distance_between(res, params)<0.0001, true);
    }

}
