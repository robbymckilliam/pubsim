/*
 * IdealisedDigitalVOCTest.java
 * JUnit based test
 *
 * Created on 13 December 2007, 11:00
 */

package simulator.qpsk;

import simulator.psk.IdealisedDigitalVOC;
import junit.framework.*;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
 */
public class IdealisedDigitalVOCTest extends TestCase {
    
    public IdealisedDigitalVOCTest(String testName) {
        super(testName);
    }

    /**
     * Test of getPhaseSignal method, of class simulator.qpsk.IdealisedDigitalVOC.
     */
    public void testGetPhaseSignal() {
        System.out.println("getPhaseSignal");
        
        int n = 5;
        int M = 4;
        double transF = 0.2;
        double real[] = new double[n];
        double imag[] = new double[n];
        
        for(int i = 0; i < n; i++){
            double t = 2*Math.PI*transF*i;
            real[i] = Math.cos(t);
            imag[i] = Math.sin(t);
        }
        
        IdealisedDigitalVOC instance = new IdealisedDigitalVOC(0.1);
        
        double[] expr = {0.0,0.1,0.2,0.3,0.4};
        double[] result = instance.getPhaseSignal(real, imag);
        
        System.out.println(VectorFunctions.print(result));
        
        assertEquals(true, VectorFunctions.distance_between(expr,result)<0.00001);
        
    }
    
}
