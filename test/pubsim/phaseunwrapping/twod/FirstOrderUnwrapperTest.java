/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.twod;

import pubsim.phaseunwrapping.twod.GaussianPulse;
import pubsim.phaseunwrapping.twod.FirstOrderUnwrapper;
import pubsim.distributions.GaussianNoise;
import pubsim.distributions.ContinuousRandomVariable;
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
public class FirstOrderUnwrapperTest {

    public FirstOrderUnwrapperTest() {
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
     * Test of unwrap method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testGaussUnwrap() {
        System.out.println("testGaussUnwrap");
        int N = 9;
        int M = 8;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        instance.setSize(M, N);
        
        GaussianPulse gaussp = new GaussianPulse(M, N, 2.0, 0.08);
        gaussp.setNoiseGenerator(new GaussianNoise(0.0, 0.01));
        gaussp.generateData();
        double[][] yw = gaussp.getWrappedData();
        double[][] y = instance.unwrap(yw);
        System.out.println("yw = " + VectorFunctions.print(yw));
        System.out.println("i = " + VectorFunctions.print(gaussp.getWrappedIntegers()));
        System.out.println("y = " + VectorFunctions.print(y));
    }

        /**
     * Test of unwrap method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testCrazyHillUnwrap() {
        System.out.println("testCrazyHillUnwrap");
        int N = 25;
        int M = 12;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        //instance.setSize(M, N);

        double[] ywvec =
        {0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,        0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,
        0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.2500,    0.2500,   0.2500,    0.2500,    0.2500,    0.4000,    0.4000,         0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,
       -0.4000,  -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,    0.3000,    0.3000,   0.3000,    0.3000,    0.3000,   -0.4000,   -0.4000,     -0.4000,   -0.4000,   -0.4000,  -0.4000,   -0.4000,  -0.4000,   -0.4000,   -0.4000,   -0.4000,
       -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,    0.3500,    0.3500,   0.3500,    0.3500,    0.3500,   -0.2000,   -0.2000,  -0.2000,   -0.2000,   -0.2000,  -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,
             0,         0,         0,         0,         0,         0,         0,         0,         0,    0.4000,    0.4000,  0.4000,    0.4000,    0.4000,         0,         0,  0,         0,         0,         0,         0,         0,         0,         0,         0,
        0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.4500,    0.4500,    0.4500,    0.4500,    0.4500,    0.2000,    0.2000,0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,
        0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,   -0.5000,   -0.5000,   -0.5000,   -0.5000,   -0.5000,    0.4000,    0.4000,0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,
       -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4500,   -0.4500,   -0.4500,   -0.4500,   -0.4500,   -0.4000,   -0.4000,-0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,
       -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.4000,   -0.2000,   -0.2000,-0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,   -0.2000,
             0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,  0,         0,         0,         0,         0,         0,         0,         0,         0,
        0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,     0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,    0.2000,
        0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,        0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000,    0.4000};

        //add a bit of noise
        ContinuousRandomVariable noise = new GaussianNoise(0.0, 0.01);
        for(int i = 0; i < ywvec.length; i++)
            ywvec[i] += noise.getNoise();

        double[][] yw = VectorFunctions.packRowiseToMatrix(ywvec, M);
        double[][] y = instance.unwrap(yw);
        System.out.println("yw = " + VectorFunctions.print(yw));
        System.out.println("y = " + VectorFunctions.print(y));
    }

    /**
     * Test of setSize method, of class FirstOrderUnwrapper.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int M = 4;
        int N = 4;
        FirstOrderUnwrapper instance = new FirstOrderUnwrapper();
        instance.setSize(M, N);

    }


}
