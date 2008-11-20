/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import junit.framework.TestCase;
import distributions.GaussianNoise;
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
                
        int n = 10;
        double[] params = {0.2 , 0.15, 0.1};
        
        PolynomialPhaseSignal gen = new PolynomialPhaseSignal();
        gen.setLength(n);
        gen.setParameters(params);
        
        GaussianNoise noise = new GaussianNoise(0, 0.0000001);
        gen.setNoiseGenerator(noise);
        
        gen.generateReceivedSignal();
        
        LatticeEstimator instance = new LatticeEstimator(params.length);
        double[] res = instance.estimate(gen.getReal(), gen.getImag());
        
        double[] mse = PolynomialPhaseSignal.disambiguateMSE(params, res);
        
        System.out.println("res = " + VectorFunctions.print(res));
        System.out.println("mse = " + VectorFunctions.print(mse));
        
        assertEquals( VectorFunctions.magnitude(mse)<0.01, true);
    }

}
