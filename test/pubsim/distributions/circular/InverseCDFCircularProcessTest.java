/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.circular;

import pubsim.VectorFunctions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class InverseCDFCircularProcessTest {
    
    public InverseCDFCircularProcessTest() {
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
     * Test of sinusoidalAutocorrelation method, of class InverseCDFCircularProcess.
     */
    @Test
    public void testSinusoidalAutocorrelation() {
        System.out.println("test the sinusoidal autocorellation");
        
        double[] filter = {1.0, 0.5, 0.25};
        InverseCDFCircularProcess instance = new InverseCDFCircularProcess(new WrappedGaussian(0,0.05), filter);
        
        int N = 100000;
        double[] X = new double[N];
        for(int i = 0; i < N; i++) X[i] = instance.getNoise();
       
        
        double[] ac = instance.sinusoidalAutocorrelation();
        System.out.println(VectorFunctions.print(ac));
        
        for(int k = 0; k < filter.length + 4; k++){
            double Ak = 0;
            for(int i = 0; i < N-k; i++) Ak += Math.sin(2*Math.PI*X[i])*Math.sin(2*Math.PI*X[i+k]);
            Ak/=(N-k);
            System.out.print(Ak + ", ");
            if( k < ac.length) assertEquals(ac[k], Ak, 0.005);
            else assertEquals(0, Ak, 0.005);
        }
        
        

    }
    
    /**
     * Test of sinusoidalAutocorrelation method, of class InverseCDFCircularProcess.
     */
    @Test
    public void testWhatHappensToAntipodalPoint() {
        System.out.println("what happens to the antipodal point?");
        
        double[] filter = {1.0, 0.5, 0.25};
        InverseCDFCircularProcess instance = new InverseCDFCircularProcess(new WrappedGaussian(0,0.1), filter);
        
        double eres = instance.circularMarginal().pdf(-0.5);
        
        int N = 100000;
        double[] X = new double[N];
        for(int i = 0; i < N; i++) X[i] = instance.getNoise();
        
        DensityEstimator dest = new DensityEstimator(X, new WrappedGaussian(0,1.0/10000));
        double res = dest.pdf(-0.5);
                
        System.out.println(res + ", " + eres);
        
        

    }

        /**
     * Test of sinusoidalAutocorrelation method, of class InverseCDFCircularProcess.
     */
    @Test
    public void testWhatHappensToTheUnwrappedVariance() {
        System.out.println("what happens to the unwrapped variance?");
        
        double[] filter = {1.0, 0.5, 0.25};
        InverseCDFCircularProcess instance = new InverseCDFCircularProcess(new WrappedGaussian(0,0.01), filter);
        
        double[] ac = instance.autocorrelation();
        double wv = new WrappedGaussian(0,0.01).unwrappedVariance();
        
        System.out.println(ac[0] + ", " + wv);

    }
    
}
