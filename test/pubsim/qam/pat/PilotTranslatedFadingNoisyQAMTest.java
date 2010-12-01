/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.qam.pat.PilotTranslatedFadingNoisyQAM;
import junit.framework.TestCase;

/**
 *
 * @author robertm
 */
public class PilotTranslatedFadingNoisyQAMTest extends TestCase {
    
    public PilotTranslatedFadingNoisyQAMTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of generateQAMSignal method, of class PilotTranslatedFadingNoisyQAM.
     */
    public void testGenerateQAMSignal() {
        System.out.println("generateQAMSignal");
        
        PilotTranslatedFadingNoisyQAM instance = new PilotTranslatedFadingNoisyQAM();
        instance.setPATSymbol(0.1,0.1);
        instance.setQAMSize(8);
        instance.setLength(10);
        
        instance.generateQAMSignal();
        
        double[] xr = instance.getTransmittedRealQAMSignal();
        double[] xi = instance.getTransmittedImagQAMSignal();
               
        for(int i = 0; i < xr.length; i++){
            assertEquals(true, xr[i]<=7.1 && xr[i]>=-6.9);
            assertEquals(true, xi[i]<=7.1 && xi[i]>=-6.9);
        }
    }

}
