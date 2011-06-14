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
import pubsim.distributions.RandomVariable;

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

        int N = 10000000;
        double[] X = new double[N];
        for(int i = 0; i < N; i++) X[i] = instance.getNoise();

        for(int k = 0; k < autocor.length; k++){
            for(int i = 0; i < N-k; i++) estac[k] += X[i]*X[i+k];
            estac[k]/=(N-k);
            assertEquals(autocor[k], estac[k], 0.01);
        }

        
        System.out.println(VectorFunctions.print(autocor));
        System.out.println(VectorFunctions.print(estac));

    }

}