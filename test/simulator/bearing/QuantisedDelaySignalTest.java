/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing;

import distributions.discrete.GeometricDistribution;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class QuantisedDelaySignalTest {

    public QuantisedDelaySignalTest() {
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
     * Print out a signal.
     */
    @Test
    public void testSetDelay() {

        int n = 10000;
        double p = 0.5;
        double P = 1/(Math.PI*Math.PI);

        QuantisedDelaySignal siggen = new QuantisedDelaySignal();
        siggen.setDelay(0);
        siggen.setLength(n);
        siggen.setClockPeriod(P);

        siggen.setNoiseGenerator(new GeometricDistribution(p));

        double[] signal = siggen.generateReceivedSignal();

        double var = 0;
        for(int i = 0; i < n; i++){
            var += signal[i]*signal[i];
            System.out.println(signal[i]);
        }
        var = var/n;
        
        double expvar = Math.pow(P/2.0 , 2)/3.0;

        System.out.println(var + ", " + expvar);
        assertEquals(expvar, var, 0.0001);

    }


}