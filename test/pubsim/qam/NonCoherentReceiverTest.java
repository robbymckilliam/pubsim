/*
 * NonCoherentReceiverTest.java
 * JUnit based test
 *
 * Created on 27 September 2007, 14:18
 */

package pubsim.qam;

import pubsim.qam.NonCoherentReceiver;
import junit.framework.*;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class NonCoherentReceiverTest extends TestCase {
    
    public NonCoherentReceiverTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    /**
     * Test of NN method, of class simulator.qam.NonCoherentReceiver.
     */
    public void testNN() {
        System.out.println("NN");
        
        double[] x = {0.1, -0.1, 1.1, 2.1, -2.0001, -1.9999, 3.01, 3.9, -4.1};
        double[] y = new double[x.length];
        NonCoherentReceiver instance = new NonCoherentReceiver();
        
        NonCoherentReceiver.NN(x, y, 16);
        
        System.out.println(" y = " + VectorFunctions.print(y));
        
        double[] expr = {1.0, -1.0, 1.0, 3.0, -3.0, -1.0, 3.0, 3.0, -5.0};
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
        
        double[] xr1 = {3.1,1.1};
        double[] xi1 = {2.3,-1.3};
        double[] yr4 = {-3.1,-1.1};
        double[] yi4 = {-2.3,1.3};
        assertEquals(true, 
                NonCoherentReceiver.ambiguityEqual(xr1,xi1,yr4,yi4));
    }
    
}
