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
        double fmin = 0.55;
        double fmax = 1.6;
        int n = 30;
        double T = 1.0;
        ZnLLS instance = new ZnLLS();
        
        GaussianNoise noise = new simulator.GaussianNoise(0.0,0.08*0.08);
        
        SparseNoisyPeriodicSignal sig = new SparseNoisyPeriodicSignal();
        sig.setPeriod(T);
        sig.setNoise(noise);
          
        //noise.setSeed(200);
        double[] trans = sig.generateTransmittedSignal(n);
        y = sig.generateReceivedSignal();
        
        System.out.println(VectorFunctions.print(trans));
        
        double expResult = T;
        double result = instance.estimateFreq(y, fmin, fmax);
        assertEquals(expResult, result);

    }
    
}
