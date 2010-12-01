/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.oned;

import pubsim.phaseunwrapping.oned.GaussianPulse;
import pubsim.distributions.GaussianNoise;
import pubsim.phaseunwrapping.oned.GeneralOneDUnwrapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;

/**
 *
 * @author robertm
 */
public class GeneralOneDUnwrapperTest {

    public GeneralOneDUnwrapperTest() {
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
     * Test of setSize method, of class GeneralOneDUnwrapper.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int N = 5;
        GeneralOneDUnwrapper instance = new GeneralOneDUnwrapper(2, 5);
        instance.setSize(N);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setSize method, of class GeneralOneDUnwrapper.
     */
    @Test
    public void testUnwrap() {
        System.out.println("testUnwrap");
        int N = 10;
        GeneralOneDUnwrapper instance = new GeneralOneDUnwrapper(2, 3);
        //instance.setSize(N);
        //double[] y = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
//        double[] y = {     1.0237,
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
//        y = VectorFunctions.wrap(y);
        GaussianPulse gaussp = new GaussianPulse(30, 2.0, 0.05);
    gaussp.setNoiseGenerator(new GaussianNoise(0.0, 0.001));
    gaussp.generateData();
        double[] y = gaussp.getWrappedData();
        double[] u = instance.unwrap(y);
        System.out.println(VectorFunctions.print(y));
        System.out.println(VectorFunctions.print(gaussp.getTrueData()));
        System.out.println(VectorFunctions.print(u));
        System.out.println(VectorFunctions.print(gaussp.getWrappedIntegers()));


    }

}