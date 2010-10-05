/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import distributions.SumsOfDistributions;
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
public class UnwrappedMeanAndVarianceTest {

    public UnwrappedMeanAndVarianceTest() {
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

        UnwrappedMeanAndVariance test =
                new UnwrappedMeanAndVariance(
                new WrappedGaussianNoise.Mod1(0.1, 0.1));

        assertEquals(0.1, test.getUnwrappedMean(), 0.001);


    }

    /**
     * Test of getUnwrappedVariance method, of class UnwrappedMeanAndVariance.
     */
    @Test
    public void testAdditiveDist() {
        System.out.println("getUnwrappedMeanAndVariance");
    
        SumsOfDistributions dist = new SumsOfDistributions();
        dist.addDistribution(new WrappedGaussianNoise.Mod1(0.25, 0.02), 0.5);
        dist.addDistribution(new WrappedGaussianNoise.Mod1(-0.25, 0.02), 0.5);

        UnwrappedMeanAndVariance test = new UnwrappedMeanAndVariance(dist);

        System.out.println(test.getUnwrappedMean());
        System.out.println(test.getUnwrappedVariance());

        //test.computeAndPrint(200);
    
    }

}