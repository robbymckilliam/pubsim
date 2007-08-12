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
 * @author robertm
 */
public class ZnLLSTest extends TestCase {
    
    public ZnLLSTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    /**
     * Test of estimateFreq method, of class simulator.ZnLLS.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        double[] y = {1.0,2.0,3.0,4.0};
        double fmin = 0.7;
        double fmax = 1.3;
        int n = 30;
        double f = 1.0;
        double T = 1/f;
        ZnLLS instance = new ZnLLS();
        
        double noisestd = Math.pow(10, (0.0 + 0.05*5));
        GaussianNoise noise = new simulator.GaussianNoise(0.0,noisestd*noisestd);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoise(noise);
          
        long seed = 133;
        noise.setSeed(seed);
        sig.generateSparseSignal(n, seed);
        double[] trans = sig.generateSparseSignal(n);
        y = sig.generateReceivedSignal();
        
        //System.out.println(VectorFunctions.print(trans));
        
        double expResult = f;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(expResult, result);

    }
    
}
