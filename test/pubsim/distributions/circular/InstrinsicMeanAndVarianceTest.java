/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import pubsim.distributions.circular.WrappedGaussian;
import pubsim.distributions.circular.InstrinsicMeanAndVariance;
import pubsim.distributions.SumsOfDistributions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class InstrinsicMeanAndVarianceTest {

    public InstrinsicMeanAndVarianceTest() {
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
     * Test on wrapped normal
     */
    @Test
    public void testWrappedNormal() {
        System.out.println("getUnwrappedMeanAndVariance");

        InstrinsicMeanAndVariance test =
                new InstrinsicMeanAndVariance(
                new WrappedGaussian(0.1, 0.1));

        assertEquals(0.1, test.getIntrinsicMean(), 0.001);


    }

    /**
     * Test of getIntrinsicVariance method, of class InstrinsicMeanAndVariance.
     */
    @Test
    public void testAdditiveDist() {
        System.out.println("getUnwrappedMeanAndVariance");
    
        SumsOfDistributions dist = new SumsOfDistributions();
        dist.addDistribution(new WrappedGaussian(0.25, 0.02), 0.5);
        dist.addDistribution(new WrappedGaussian(-0.25, 0.02), 0.5);

        InstrinsicMeanAndVariance test = new InstrinsicMeanAndVariance(dist);

        System.out.println(test.getIntrinsicMean());
        System.out.println(test.getIntrinsicVariance());

        //test.computeAndPrint(200);
    
    }

}