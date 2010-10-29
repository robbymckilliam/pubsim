/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.phaseunwrapping.oned;

import robbysim.phaseunwrapping.oned.GaussianPulse;
import robbysim.distributions.RandomVariable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import robbysim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class GaussianPulseTest {

    public GaussianPulseTest() {
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
     * Test of getWrappedData method, of class GaussianPulse.
     */
    @Test
    public void testData() {
        System.out.println("getWrappedData");
        GaussianPulse instance = new GaussianPulse(10, 1.0, 0.5);
        instance.generateData();
        System.out.println(VectorFunctions.print(instance.getWrappedData()));
        System.out.println(VectorFunctions.print(instance.getTrueData()));

    }
    

}