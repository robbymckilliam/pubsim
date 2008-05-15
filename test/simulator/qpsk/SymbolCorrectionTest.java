/*
 * SymbolCorrectionTest.java
 * JUnit based test
 *
 * Created on 13 December 2007, 11:58
 */

package simulator.qpsk;

import simulator.psk.SymbolCorrection;
import junit.framework.*;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
 */
public class SymbolCorrectionTest extends TestCase {
    
    public SymbolCorrectionTest(String testName) {
        super(testName);
    }

    /**
     * Test of getCorrection method, of class simulator.qpsk.SymbolCorrection.
     */
    public void testGetCorrection() {
        System.out.println("getCorrection");
        
        int n = 5;
        int M = 4;
        double p = 0.3;
        double f = 0.2;
        double[] arg = new double[n];
        
        for(int i = 0; i < n; i++){
            double t = 2*Math.PI*(f*i + p);
            arg[i] = Math.atan2(Math.sin(t), Math.cos(t))/(2*Math.PI);
        }
        
        SymbolCorrection instance = new SymbolCorrection();
        instance.setF(f);
        instance.setP(p);
        
        double[] expr = {0,0,0,0,0};
        double[] result = instance.getCorrection(arg);
        
        assertEquals(true, VectorFunctions.distance_between(expr,result)<0.00001);
    }
    
}
