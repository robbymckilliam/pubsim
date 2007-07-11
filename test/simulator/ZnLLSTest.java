/*
 * ZnLLSTest.java
 * JUnit based test
 *
 * Created on 9 July 2007, 12:24
 */

package simulator;

import junit.framework.*;
import java.util.TreeMap;

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
        int n = 10;
        double f = 1.29;
        double T = 1/f;
        ZnLLS instance = new ZnLLS();
        
        GaussianNoise noise = new simulator.GaussianNoise(0.0,0.03*0.03*0.03*0.03);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoise(noise);
          
        long seed = 106;
        noise.setSeed(seed);
        sig.generateTransmittedSignal(n, seed);
        double[] trans = sig.generateTransmittedSignal(n);
        y = sig.generateReceivedSignal();
        
        System.out.println(VectorFunctions.print(trans));
        
        double expResult = f;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(expResult, result);

    }
    
}
