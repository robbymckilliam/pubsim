/*
 * T2LogTOptimalNonCoherentRecieverTest.java
 * JUnit based test
 *
 * Created on 18 September 2007, 13:33
 */

package simulator.qam;

import junit.framework.*;
import java.util.TreeMap;
import simulator.VectorFunctions;
import simulator.GaussianNoise;

/**
 * 
 * @author Robby
 */
public class T2LogTOptimalNonCoherentRecieverTest extends TestCase {
    
    public T2LogTOptimalNonCoherentRecieverTest(String testName) {
        super(testName);
    }

    /**
     * Test of decode method, of class simulator.qam.T2LogTOptimalNonCoherentReciever.
     */
    public void testDecode() {
        System.out.println("decode");
        
        int M = 16;
        int T = 13;
        
        FadingNoisyQAM siggen = new FadingNoisyQAM(M);
        siggen.setChannel(1.0,0.0);
        
        GaussianNoise noise = new GaussianNoise(0.0,0.0001);
        siggen.setNoise(noise);
        
        T2LogTOptimalNonCoherentReciever instance = new T2LogTOptimalNonCoherentReciever();
        instance.setQAMSize(M);
        instance.setT(T);
        
        siggen.generateQAMSignal(T);
        siggen.generateReceivedSignal();
        instance.decode(siggen.getInphase(), siggen.getQuadrature());
        
        System.out.println("treal = " + VectorFunctions.print(siggen.getTransmittedRealQAMSignal()));
        System.out.println("rreal = " + VectorFunctions.print(instance.getReal()));
        System.out.println("timag = " + VectorFunctions.print(siggen.getTransmittedImagQAMSignal()));
        System.out.println("rimag = " + VectorFunctions.print(instance.getImag()));
        
        assertEquals(true, instance.ambiguityEqual(siggen.getTransmittedRealQAMSignal(), 
                siggen.getTransmittedImagQAMSignal(), 
                instance.getReal(), instance.getImag()));
        
    }

    /**
     * Test of NN method, of class simulator.qam.T2LogTOptimalNonCoherentReciever.
     */
    public void testNN() {
        System.out.println("NN");
        
        double[] x = {0.1, -0.1, 1.1, 2.1};
        double[] y = new double[x.length];
        T2LogTOptimalNonCoherentReciever instance = new T2LogTOptimalNonCoherentReciever();
        
        instance.NN(x, y);
        
        double[] expr = {1.0, -1.0, 1.0, 3.0};
        assertEquals(true, VectorFunctions.distance_between(expr,y)<0.000001);
        
    }
    
    public void testAmbiguityEqual(){
        System.out.println("ambiguityEqual");
        
        double[] xr = {3.0,1.0};
        double[] xi = {2.0,-1.0};
        double[] yr = {3.0,1.0};
        double[] yi = {2.0,-1.0};
        assertEquals(true, 
                T2LogTOptimalNonCoherentReciever.ambiguityEqual(xr,xi,yr,yi));
        
        double[] yr1 = {-3.0,-1.0};
        double[] yi1 = {-2.0,1.0};
        assertEquals(true, 
                T2LogTOptimalNonCoherentReciever.ambiguityEqual(xr,xi,yr1,yi1));
        
        double[] yr2 = {2.0,-1.0};
        double[] yi2 = {-3.0,-1.0};
        assertEquals(true, 
                T2LogTOptimalNonCoherentReciever.ambiguityEqual(xr,xi,yr2,yi2));
        
        double[] yr3 = {-2.0,1.0};
        double[] yi3 = {3.0,1.0};
        assertEquals(true, 
                T2LogTOptimalNonCoherentReciever.ambiguityEqual(xr,xi,yr3,yi3));
        
    }
    
}
