/*
 * NonCoherentReceiverTest.java
 * JUnit based test
 *
 * Created on 27 September 2007, 14:18
 */

package simulator.qam;

import junit.framework.*;
import simulator.VectorFunctions;

/**
 *
 * @author robertm
 */
public class NonCoherentReceiverTest extends TestCase {
    
    public NonCoherentReceiverTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of NN method, of class simulator.qam.NonCoherentReceiver.
     */
    public void testNN() {
        System.out.println("NN");
        
        double[] x = {0.1, -0.1, 1.1, 2.1};
        double[] y = new double[x.length];
        NonCoherentReceiver instance = new NonCoherentReceiver();
        
        instance.NN(x, y);
        
        double[] expr = {1.0, -1.0, 1.0, 3.0};
        assertEquals(true, VectorFunctions.distance_between(expr,y)<0.000001);
    }

    /**
     * Test of ambiguityEqual method, of class simulator.qam.NonCoherentReceiver.
     */
    public void testAmbiguityEqual() {
        System.out.println("ambiguityEqual");
        
         double[] xr = {3.0,1.0};
        double[] xi = {2.0,-1.0};
        double[] yr = {3.0,1.0};
        double[] yi = {2.0,-1.0};
        assertEquals(true, 
                NonCoherentReceiver.ambiguityEqual(xr,xi,yr,yi));
        
        double[] yr1 = {-3.0,-1.0};
        double[] yi1 = {-2.0,1.0};
        assertEquals(true, 
                NonCoherentReceiver.ambiguityEqual(xr,xi,yr1,yi1));
        
        double[] yr2 = {2.0,-1.0};
        double[] yi2 = {-3.0,-1.0};
        assertEquals(true, 
                NonCoherentReceiver.ambiguityEqual(xr,xi,yr2,yi2));
        
        double[] yr3 = {-2.0,1.0};
        double[] yi3 = {3.0,1.0};
        assertEquals(true, 
                NonCoherentReceiver.ambiguityEqual(xr,xi,yr3,yi3));
    }
    
}
