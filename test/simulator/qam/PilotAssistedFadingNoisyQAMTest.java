/*
 * PilotAssistedFadingNoisyQAMTest.java
 * JUnit based test
 *
 * Created on 27 November 2007, 12:54
 */

package simulator.qam;

import junit.framework.*;
import simulator.SignalGenerator;

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
    
}
