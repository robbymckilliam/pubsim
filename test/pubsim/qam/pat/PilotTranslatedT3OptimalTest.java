/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.qam.pat.PilotTranslatedT3Optimal;
import pubsim.qam.pat.PilotTranslatedFadingNoisyQAM;
import junit.framework.TestCase;
import pubsim.distributions.GaussianNoise;
import pubsim.VectorFunctions;

/**
 *
 * @author robertm
 */
public class PilotTranslatedT3OptimalTest extends TestCase {
    
    public PilotTranslatedT3OptimalTest(String testName) {
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
     * Test of NN method, of class PilotTranslatedT3Optimal.
     */
    public void testNN() {
        System.out.println("NN");
        
        double pr = 0.1;
        double pi = 0.1;
        int M = 4;
        
        double[] x = {0.11, 0.09, 4.3, -1.91};
        double[] y = new double[x.length];
        
        
        PilotTranslatedT3Optimal instance = new PilotTranslatedT3Optimal();
        instance.setQAMSize(M);
        instance.setPATSymbol(pr, pi);
        
        instance.NN(x, y);
        
        System.out.println(" y = " + VectorFunctions.print(y));
        
        double[] expr = {1.1, -0.9, 3.1, -2.9};
        assertEquals(true, VectorFunctions.distance_between(expr,y)<0.000001);
    }

    /**
     * Test of decode method, of class PilotTranslatedT3Optimal.
     */
    public void testDecode() {
        System.out.println("decode");
        
        int M = 8;
        int T = 8;
        long seed = 11111;
        double pr = 0.1;
        double pi = 0.0;
        
        PilotTranslatedFadingNoisyQAM siggen = new PilotTranslatedFadingNoisyQAM(M);
        siggen.setChannel(1.0,0.2);
        siggen.setPATSymbol(pr, pi);
        
        GaussianNoise noise = new GaussianNoise(0.0,0.0000001);
        siggen.setNoiseGenerator(noise);
        
        PilotTranslatedT3Optimal instance = new PilotTranslatedT3Optimal();
        instance.setQAMSize(M);
        instance.setT(T);
        instance.setPATSymbol(pr, pi);
        
        //siggen.setSeed(seed);
        //noise.setSeed(seed);
        siggen.generateChannel();
        
        siggen.generateQAMSignal(T);
        siggen.generateReceivedSignal();
        instance.decode(siggen.getInphase(), siggen.getQuadrature());
        
        System.out.println("treal = " + VectorFunctions.print(siggen.getTransmittedRealQAMSignal()));
        System.out.println("rreal = " + VectorFunctions.print(instance.getReal()));
        System.out.println("timag = " + VectorFunctions.print(siggen.getTransmittedImagQAMSignal()));
        System.out.println("rimag = " + VectorFunctions.print(instance.getImag()));
        
        assertEquals(0.0, siggen.symbolErrorRate(
                instance.getReal(),
                instance.getImag(),
                siggen.getTransmittedRealQAMSignal(),
                siggen.getTransmittedImagQAMSignal()));
    }

}
