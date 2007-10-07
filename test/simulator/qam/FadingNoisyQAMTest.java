/*
 * FadingNoisyQAMTest.java
 * JUnit based test
 *
 * Created on 14 September 2007, 13:50
 */

package simulator.qam;

import junit.framework.*;
import simulator.SignalGenerator;
import simulator.GaussianNoise;
import simulator.NoiseGenerator;
import java.util.Random;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
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
        
        int length = 10;
        FadingNoisyQAM instance = new FadingNoisyQAM();
        
        instance.generateQAMSignal(length);
        
        double[] xr = instance.getTransmittedRealQAMSignal();
        double[] xi = instance.getTransmittedImagQAMSignal();
        
        for(int i = 0; i < length; i++){
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
        
        NoiseGenerator noise = new simulator.UniformNoise(0.0, 0.0);
        instance.setNoise(noise);  
               
        instance.setTransmittedSignal(xr, xi);
        
        double[] expr = {1.2330, -5.8620, 2.5308};
        double[] expi = {-2.0982, -2.0334, 2.8986};
        instance.generateReceivedSignal();
        
        System.out.println(VectorFunctions.print(instance.getInphase()));
        System.out.println(VectorFunctions.print(instance.getQuadrature()));
        
        assertEquals(true, VectorFunctions.distance_between(expr,instance.getInphase())<0.00001);
        assertEquals(true, VectorFunctions.distance_between(expi,instance.getQuadrature())<0.00001);

    }
    
}