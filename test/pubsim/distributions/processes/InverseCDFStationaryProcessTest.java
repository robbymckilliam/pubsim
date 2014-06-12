/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.processes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;
import pubsim.distributions.RealRandomVariable;
import pubsim.distributions.UniformNoise;
import pubsim.distributions.circular.VonMises;
import pubsim.distributions.circular.WrappedGaussian;
import pubsim.distributions.circular.WrappedUniform;

/**
 *
 * @author mckillrg
 */
public class InverseCDFStationaryProcessTest {
    
    public InverseCDFStationaryProcessTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNoise method, of class InverseCDFStationaryProcess.
     */
    @Test
    public void testGetNoiseWithUniform() {
        System.out.println("test with uniform");
        double[] filter = {1.0, 0.25, 0.25};
        InverseCDFStationaryProcess instance = new InverseCDFStationaryProcess(new UniformNoise(0,1.0/20), filter);
        
        int N = 100000;
        double[] X = new double[N];
        for(int i = 0; i < N; i++) X[i] = instance.noise();
        
        //check average is near the mean, i.e. zero
        assertEquals(0,VectorFunctions.mean(X), 0.01);
        
        double[] ac = instance.autocorrelation();
        System.out.println(VectorFunctions.print(ac));
        
        for(int k = 0; k < filter.length + 4; k++){
            double Ak = 0;
            for(int i = 0; i < N-k; i++) Ak += X[i]*X[i+k];
            Ak/=(N-k);
            System.out.print(Ak + ", ");
            if( k < ac.length) assertEquals(ac[k], Ak, 0.001);
            else assertEquals(0, Ak, 0.001);
        }
        
    }
    
    
//    /**
//     * Test of getNoise method, of class InverseCDFStationaryProcess.
//     */
//    @Test
//    public void testGetNoiseWithVonMises() {
//        System.out.println("test with Von Mises");
//        double[] filter = {1.0, 0.5, 0.25};
//        InverseCDFStationaryProcess instance = new InverseCDFStationaryProcess(new VonMises(0,3), filter);
//        
//        int N = 10000;
//        double[] X = new double[N];
//        for(int i = 0; i < N; i++) X[i] = instance.getNoise();
//        
//        //check average is near the mean, i.e. zero
//        assertEquals(0,VectorFunctions.mean(X), 0.01);
//        
//        double[] ac = instance.autocorrelation(100);
//        System.out.println(VectorFunctions.print(ac));
//        
//        for(int k = 0; k < filter.length + 4; k++){
//            double Ak = 0;
//            for(int i = 0; i < N-k; i++) Ak += X[i]*X[i+k];
//            Ak/=(N-k);
//            System.out.print(Ak + ", ");
//            if( k < ac.length) assertEquals(ac[k], Ak, 0.001);
//            else assertEquals(0, Ak, 0.001);
//        }
//        
//    }
    
    /**
     * Test of getNoise method, of class InverseCDFStationaryProcess.
     */
    @Test
    public void testGetNoiseWithWrappedGaussian() {
        System.out.println("test with wrapped Gaussian");
        double[] filter = {1.0, 0.5, 0.25};
        InverseCDFStationaryProcess instance = new InverseCDFStationaryProcess(new WrappedGaussian(0,0.1), filter);
        
        int N = 100000;
        double[] X = new double[N];
        for(int i = 0; i < N; i++) X[i] = instance.noise();
        
        //check average is near the mean, i.e. zero
        assertEquals(0,VectorFunctions.mean(X), 0.3);
        
        double[] ac = instance.autocorrelation(100);
        System.out.println(VectorFunctions.print(ac));
        
        for(int k = 0; k < filter.length + 4; k++){
            double Ak = 0;
            for(int i = 0; i < N-k; i++) Ak += X[i]*X[i+k];
            Ak/=(N-k);
            System.out.print(Ak + ", ");
            if( k < ac.length) assertEquals(ac[k], Ak, 0.001);
            else assertEquals(0, Ak, 0.001);
        }
        
    }
    
    /**
     * Test of getNoise method, of class InverseCDFStationaryProcess.
     */
    @Test
    public void testSpeedTestNoiseGeneration() {
        System.out.println("test the noise generation speed of some of the generators");
        double[] filter = {1.0, 0.5, 0.25};
        
        int N = 10000;
        
        InverseCDFStationaryProcess instance = new InverseCDFStationaryProcess(new WrappedGaussian(0,1), filter);
        long starttime = (new java.util.Date()).getTime();
        for(int i = 0; i < N; i++) instance.noise();
        System.out.println( "wrappg ran for " + (((new java.util.Date()).getTime() - starttime)/1000.0) );
        
        instance = new InverseCDFStationaryProcess(new UniformNoise(0,1.0/20), filter);
        starttime = (new java.util.Date()).getTime();
        for(int i = 0; i < N; i++) instance.noise();
        System.out.println( "wrappu ran for " + (((new java.util.Date()).getTime() - starttime)/1000.0) );
        
//        instance = new InverseCDFStationaryProcess(new VonMises(0,3), filter);
//        starttime = (new java.util.Date()).getTime();
//        for(int i = 0; i < N; i++) instance.getNoise();
//        System.out.println( "vonmis ran for " + (((new java.util.Date()).getTime() - starttime)/1000.0) );
        
    }
    
    /**
     * Test of getNoise method, of class InverseCDFStationaryProcess.
     */
    @Test
    public void testSpeedTestCLT() {
        System.out.println("test the autocorellation speed of some of the generators");
        double[] filter = {1.0, 0.5, 0.25};
        
        InverseCDFStationaryProcess instance = new InverseCDFStationaryProcess(new UniformNoise(0,1.0/20), filter);
        long starttime = (new java.util.Date()).getTime();
        instance.autocorrelation();
        System.out.println( "wrappu ran for " + (((new java.util.Date()).getTime() - starttime)/1000.0) );
        
        instance = new InverseCDFStationaryProcess(new WrappedGaussian(0,1), filter);
        starttime = (new java.util.Date()).getTime();
        instance.autocorrelation();
        System.out.println( "wrappg ran for " + (((new java.util.Date()).getTime() - starttime)/1000.0) );
        
//        instance = new InverseCDFStationaryProcess(new VonMises(0,3), filter);
//        starttime = (new java.util.Date()).getTime();
//        for(int i = 0; i < N; i++) instance.getNoise();
//        System.out.println( "vonmis ran for " + (((new java.util.Date()).getTime() - starttime)/1000.0) );
        
    }
    
}
