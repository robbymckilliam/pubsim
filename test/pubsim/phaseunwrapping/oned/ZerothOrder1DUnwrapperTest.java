/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.oned;

import pubsim.phaseunwrapping.oned.GaussianPulse;
import pubsim.phaseunwrapping.oned.ZerothOrder1DUnwrapper;
import pubsim.distributions.GaussianNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class ZerothOrder1DUnwrapperTest {

    public ZerothOrder1DUnwrapperTest() {
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
     * Test of unwrap method, of class ZerothOrder1DUnwrapper.
     */
    @Test
    public void testUnwrap() {
        System.out.println("testUnwrap");
        int N = 10;
        ZerothOrder1DUnwrapper instance = new ZerothOrder1DUnwrapper();
        //instance.setSize(N);
        //double[] y = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
//        double[] y = {        1.0237,
//    0.8150,
//    0.5304,
//    0.3886,
//    0.3340,
//    0.1301,
//    0.0590,
//    0.2387,
//    0.0390,
//   -0.1177,
//    0.0000,
//   -0.1138,
//   -0.0873,
//    0.0697,
//    0.2474,
//    0.2617,
//    0.4134,
//    0.3518,
//    0.6046,
//    0.7795,
//    0.8810};
  double[] y = {     0.1164,
    0.2314,
    0.2524,
   -0.0292,
    0.0958,
    0.1254,
   -0.1494,
   -0.1041,
    0.1471,
    0.1200,
    0.3190,
    0.4416,
    0.5612,
    0.7690,
    0.8769,
    1.1191,
    1.0898,
    1.4380,
    1.6743,
    1.7996,
    2.2757};
    GaussianPulse gaussp = new GaussianPulse(20, 2.0, 0.1);
    gaussp.setNoiseGenerator(new GaussianNoise(0.0, 0.001));
    gaussp.generateData();
        y = gaussp.getWrappedData();
        double[] u = instance.unwrap(y);
        System.out.println(VectorFunctions.print(y));
        System.out.println(VectorFunctions.print(gaussp.getTrueData()));
        System.out.println(VectorFunctions.print(u));
    }

    /**
     * Test of setSize method, of class ZerothOrder1DUnwrapper.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int N = 8;
        ZerothOrder1DUnwrapper instance = new ZerothOrder1DUnwrapper();
        instance.setSize(N);
    }

}