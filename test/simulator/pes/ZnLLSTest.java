/*
 * ZnLLSTest.java
 * JUnit based test
 *
 * Created on 9 July 2007, 12:24
 */

package simulator.pes;

import junit.framework.*;
import java.util.TreeMap;
import simulator.*;

/**
 *
 * @author Robby McKilliam
 */
public class ZnLLSTest extends TestCase {
    
    public ZnLLSTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateFreq method, of class simulator.ZnLLS.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double fmin = 0.7;
        double fmax = 1.3;
        int n = 30;
        double f = 1.0;
        double T = 1/f;
        ZnLLS instance = new ZnLLS();
        
        double noisestd = 0.01;
        GaussianNoise noise = new simulator.GaussianNoise(0.0,noisestd*noisestd);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoiseGenerator(noise);
          
        long seed = 1331;
        noise.setSeed(seed);
        sig.generateSparseSignal(n, seed);
        double[] trans = sig.generateSparseSignal(n);
        double[] y = sig.generateReceivedSignal();
        
        double expResult = f;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(true, Math.abs(result-expResult)<0.001);

    }
    
}
