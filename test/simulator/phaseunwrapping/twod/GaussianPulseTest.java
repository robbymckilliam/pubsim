/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.twod;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;

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
     * Test of getWrappedIntegers method, of class GaussianPulse.
     */
    @Test
    public void testData() {
        System.out.println("getWrappedIntegers");
        GaussianPulse instance = new GaussianPulse(10,10, 2.0, 0.05);
        instance.generateData();
        double[][] result = instance.getTrueData();
        System.out.println(VectorFunctions.print(instance.getWrappedData()));
        System.out.println(VectorFunctions.print(instance.getTrueData()));
    }


}