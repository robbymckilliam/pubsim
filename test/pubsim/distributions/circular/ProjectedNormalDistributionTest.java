/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robbysim.distributions.circular;

import pubsim.distributions.circular.ProjectedNormalDistribution;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class ProjectedNormalDistributionTest {

    public ProjectedNormalDistributionTest() {
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
     * Test of setMean method, of class ProjectedNormalDistribution.
     */
    @Test
    public void plotPdf() {
        //System.out.println("plotPdf");
        double mean = 0.0;
        double var = 0.01;
        ProjectedNormalDistribution instance = new ProjectedNormalDistribution(mean, var);

        double step = 0.001;
        for (double x = -0.5; x <= 0.5; x += step) {
            System.out.println(instance.pdf(x));
        }

    }
}
