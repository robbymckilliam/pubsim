/*
 * PilotAssistedFadingNoisyQAMTest.java
 * JUnit based test
 *
 * Created on 27 November 2007, 12:54
 */

package pubsim.qam.pat;

import pubsim.qam.pat.PilotAssistedFadingNoisyQAM;
import junit.framework.*;
import pubsim.SignalGenerator;

/**
 *
 * @author Robby
 */
public class PilotAssistedFadingNoisyQAMTest extends TestCase {
    
    public PilotAssistedFadingNoisyQAMTest(String testName) {
        super(testName);
    }

    /**
     * Test of generateQAMSignal method, of class simulator.qam.PilotAssistedFadingNoisyQAM.
     */
    public void testGenerateQAMSignal() {
        System.out.println("generateQAMSignal");
        
        PilotAssistedFadingNoisyQAM instance = new PilotAssistedFadingNoisyQAM();
        instance.setPATSymbol(3,3);
        instance.setQAMSize(8);
        instance.setLength(10);
        
        instance.generateQAMSignal();
        
        double[] xr = instance.getTransmittedRealQAMSignal();
        double[] xi = instance.getTransmittedImagQAMSignal();
               
        for(int i = 0; i < xr.length; i++){
            assertEquals(true, Math.abs(xr[i])<=7);
            assertEquals(true, Math.abs(xr[i])<=7);
        }
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
        
        PilotAssistedFadingNoisyQAM instance = new PilotAssistedFadingNoisyQAM();
        instance.setLength(3);
        
        assertEquals(0.0, instance.symbolErrorRate(xr, xi, yr, yi));
        
        double xr1[] = { 1, 1, 3 };
        double xi1[] = { 1, 1, 3 };
        double yr1[] = { 1, 1, 3 };
        double yi1[] = { 1, 1, -1 };
        
        assertEquals(1.0/2.0, instance.symbolErrorRate(xr1, xi1, yr1, yi1));
           
    }
    
}
