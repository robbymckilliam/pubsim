/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions;

import robbysim.distributions.GaussianNoise;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uqrmckil
 */
public class AbstractRandomVariableTest {

    public AbstractRandomVariableTest() {
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
     * Test of icdf method, of class AbstractRandomVariable.
     */
    @Test
    public void testIcdf() {
        System.out.println("icdf");

        GaussianNoise instance = new GaussianNoise(0,1);

        System.out.println(instance.icdf(0.5));
        assertEquals(0.0, instance.icdf(0.5), 0.00001);

        System.out.println(instance.icdf(0.5 + 0.341));
        assertEquals(1.0, instance.icdf(0.5 + 0.341), 0.01);

        System.out.println(instance.icdf(0.5 - 0.341));
        assertEquals(-1.0, instance.icdf(0.5 - 0.341), 0.01);

    }


}