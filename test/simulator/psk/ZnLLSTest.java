/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk;

import junit.framework.*;
import distributions.GaussianNoise;
import simulator.VectorFunctions;


/**
 *
 * @author Tim
 */
public class ZnLLSTest extends TestCase {
    
    public ZnLLSTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateFreq method, of class simulator.qpsk.SamplingLatticeCarrierEstimator.
     */
    public void testEstimateFreq() {
        System.out.println("estimateFreq");
        
        int n = 70;
        int M = 4;
        int iters = 1000;
        double transF = 0.1;
        double symbF = 0.6;
        double sampF = 3.0;
        double phase = 0.3;
        
        PSKSignal sig = new PSKSignal();
        sig.setSymbolRate(symbF);
        sig.setCarrierPhase(phase);
        sig.setCarrierFrequency(transF);
        sig.setSampleRate(sampF);
        sig.setLength(n);
        sig.setM(M);
        
        GaussianNoise noise = new GaussianNoise(0, 0.0001);
        sig.setNoiseGenerator(noise);
        
        for (int i = 0; i < iters; i++) {
            sig.generateTransmittedQPSKSignal();

            sig.generateReceivedSignal();
            

            double[] rr = sig.getReal();
            double[] ri = sig.getImag();
            
            ZnLLS instance = new ZnLLS();
            instance.setM(M);
            instance.setSize(n);
            instance.setSymbolRate(symbF/sampF);

            //System.out.println("data: " + VectorFunctions.print(sig.getTransmittedQPSKSignal()));
            //System.out.println("real = [" + VectorFunctions.print(sig.getReal()) + "];");
            //System.out.println("imag = [ " + VectorFunctions.print(sig.getImag()) + "];");
            
            instance.estimateCarrier(rr, ri);
            assertTrue("carrier freq est dunna match", Math.abs(transF/sampF - instance.getFreqency()) < 0.01);
            
            /*
            double[] decoded = instance.decode(rr, ri);

            assertTrue("decoded output does not match input", 
                       VectorFunctions.distance_between(sig.getTransmittedQPSKSignal(), decoded) < 0.01
                      );
            */
        }
    }
    
}
