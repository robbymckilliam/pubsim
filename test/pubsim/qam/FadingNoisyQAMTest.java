/*
 * FadingNoisyQAMTest.java
 * JUnit based test
 *
 * Created on 14 September 2007, 13:50
 */

package pubsim.qam;

import pubsim.qam.FadingNoisyQAM;
import junit.framework.*;
import pubsim.SignalGenerator;
import pubsim.distributions.GaussianNoise;
import pubsim.distributions.RealRandomVariable;
import java.util.Random;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class FadingNoisyQAMTest extends TestCase {
    
    public FadingNoisyQAMTest(String testName) {
        super(testName);
    }

    /**
     * Test of generateQAMSignal method, of class simulator.qam.FadingNoisyQAM.
     */
    public void testGenerateQAMSignal() {
        System.out.println("generateQAMSignal");
        
        FadingNoisyQAM instance = new FadingNoisyQAM();
        
        double[] xr = instance.getTransmittedRealQAMSignal();
        double[] xi = instance.getTransmittedImagQAMSignal();
        
        for(int i = 0; i < xr.length; i++){
            assertEquals(true, Math.abs(xr[i])<=7);
            assertEquals(true, Math.abs(xr[i])<=7);
        }
        
        
    }

    /**
     * Test of generateReceivedSignal method, of class simulator.qam.FadingNoisyQAM.
     */
    public void testGenerateReceivedSignal() {
        System.out.println("generateReceivedSignal");
        
        FadingNoisyQAM instance = new FadingNoisyQAM();
        double[] xr ={1.0, 2.0, -2.0};
        double[] xi ={1.0, -3.0, 1.0};
        instance.setChannel(-0.4326, -1.6656);
        
        RealRandomVariable noise = new pubsim.distributions.UniformNoise(0.0, 0.0);
        instance.setNoiseGenerator(noise);  
               
        instance.setTransmittedSignal(xr, xi);
        
        double[] expr = {1.2330, -5.8620, 2.5308};
        double[] expi = {-2.0982, -2.0334, 2.8986};
        instance.generateReceivedSignal();
        
        System.out.println(VectorFunctions.print(instance.getInphase()));
        System.out.println(VectorFunctions.print(instance.getQuadrature()));
        
        assertEquals(true, VectorFunctions.distance_between(expr,instance.getInphase())<0.00001);
        assertEquals(true, VectorFunctions.distance_between(expi,instance.getQuadrature())<0.00001);

    }
    
    /**
     * Test of symbolErrorRate method, of class simulator.qam.FadingNoisyQAM.
     */
    public void testSymbolErrorRate() {
        System.out.println("symbolErrors");
        
        double xr[] = { 1, 1, 3 };
        double xi[] = { 1, 1, 3 };
        double yr[] = { 1, 1, 3 };
        double yi[] = { 1, 1, 3 };
        
        FadingNoisyQAM instance = new FadingNoisyQAM();
        instance.setLength(3);
        
        assertEquals(0.0, instance.symbolErrorRate(xr, xi, yr, yi));
        
        double xr1[] = { 1, 1, 3 };
        double xi1[] = { 1, 1, 3 };
        double yr1[] = { 1, 1, 3 };
        double yi1[] = { 1, 1, -1 };
        
        assertEquals(1.0/3.0, instance.symbolErrorRate(xr1, xi1, yr1, yi1));
           
    }
    
}
