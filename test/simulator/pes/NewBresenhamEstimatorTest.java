/*
 * NewBresenhamEstimatorTest.java
 * JUnit based test
 *
 * Created on 12 June 2007, 11:01
 */

package simulator.pes;

import junit.framework.*;
import simulator.*;

/**
 *
 * @author Robby McKilliam
 */
public class NewBresenhamEstimatorTest extends TestCase {
    
    public NewBresenhamEstimatorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of estimateFreq method, of class simulator.NewBresenhamEstimator.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double[] y = {1.0,2.0,3.0,4.0};
        int n = 5;
        
        GaussianNoise noise = new simulator.GaussianNoise(0.0,0.0000001*0.0000001);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(1.0);
        sig.setNoiseGenerator(noise);
          
        noise.setSeed(200);
        sig.generateSparseSignal(n,200);
        
        for(int i=0; i < 228; i++){
            sig.generateSparseSignal(n);
            sig.generateReceivedSignal();
        }
                       
        //System.out.println("real u = " + VectorFunctions.print(sig.generateSparseSignal(n)) + "\n");
        
        double fmin = 0.6;
        double fmax = 1.4;
        NewBresenhamEstimator instance = new NewBresenhamEstimator();
        
        double expResult = 1.0;
        double result = instance.estimateFreq(sig.generateReceivedSignal(), fmin, fmax);
        assertEquals(expResult, result);
        
    }
    
}
