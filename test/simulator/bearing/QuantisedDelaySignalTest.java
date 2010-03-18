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
 * @author harprobey
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
     * Test of setDelay method, of class QuantisedDelaySignal.
     */
    @Test
    public void testSetDelay() {

        int n = 5000;
        double p = 1;
        double P = 0.112308417230587;

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