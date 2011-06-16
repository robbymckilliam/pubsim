/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.processes;

import Jama.Matrix;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.optimisation.AutoIntegralFunction;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class ColouredGaussianNoiseTest {

    public ColouredGaussianNoiseTest() {
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
     * Test that the autocorrellation is computed correctly by
     * Monte Carlo simulation.
     */
    @Test
    public void testAutocorrelation() {
        System.out.println("autocorrelation");
        double[] f = {1.0, 0.5, 0.5, 0.25};
        ColouredGaussianNoise instance = new ColouredGaussianNoise(f);
        double[] autocor = instance.autocorrelation();
        double[] estac = new double[autocor.length];
        //System.out.println(VectorFunctions.print(autocor));

        int N = 100000;
        double[] X = new double[N];
        for(int i = 0; i < N; i++) X[i] = instance.getNoise();

        for(int k = 0; k < autocor.length; k++){
            for(int i = 0; i < N-k; i++) estac[k] += X[i]*X[i+k];
            estac[k]/=(N-k);
            assertEquals(autocor[k], estac[k], 0.05);
        }

        
        System.out.println(VectorFunctions.print(autocor));
        System.out.println(VectorFunctions.print(estac));

    }
    
        /**
     * Test that the autocorrellation is computed correctly by
     * Monte Carlo simulation.
     */
    @Test
    public void testBivariatePDF() {
        System.out.println("bivariate pdf");
        double[] f = {1.0, 0.5, 0.5, 0.25};
        final ColouredGaussianNoise instance = new ColouredGaussianNoise(f);
        double[] ac = instance.autocor;
        
        double[] min = {-8,-8}; double[] max = {8,8};
        final int k = 2;
        
        double pdfint = new AutoIntegralFunction(200) {
            public double value(Matrix x) {
                return instance.bivariatePdf(k, x.get(0,0), x.get(1,0));
            }
        }.integral(min, max);
        assertEquals(1.0,pdfint, 0.0001);
        
        pdfint = new AutoIntegralFunction(200) {
            public double value(Matrix x) {
                return x.get(0,0)*instance.bivariatePdf(k, x.get(0,0), x.get(1,0));
            }
        }.integral(min, max);
        assertEquals(0.0,pdfint, 0.0001);
        
        pdfint = new AutoIntegralFunction(200) {
            public double value(Matrix x) {
                return x.get(1,0)*instance.bivariatePdf(k, x.get(0,0), x.get(1,0));
            }
        }.integral(min, max);
        assertEquals(0.0,pdfint, 0.0001);
        
        pdfint = new AutoIntegralFunction(200) {
            public double value(Matrix x) {
                return x.get(1,0)*x.get(1,0)*instance.bivariatePdf(k, x.get(0,0), x.get(1,0));
            }
        }.integral(min, max);
        assertEquals(ac[0],pdfint, 0.0001);
        
        pdfint = new AutoIntegralFunction(200) {
            public double value(Matrix x) {
                return x.get(1,0)*x.get(0,0)*instance.bivariatePdf(k, x.get(0,0), x.get(1,0));
            }
        }.integral(min, max);
        assertEquals(ac[k],pdfint, 0.0001);
        
    }

}