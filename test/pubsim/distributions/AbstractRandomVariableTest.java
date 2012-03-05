/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import pubsim.Complex;

/**
 *
 * @author Robby McKilliam
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

        GaussianNoise instance = new GaussianNoise(0, 1);

        System.out.println(instance.icdf(0.5));
        assertEquals(0.0, instance.icdf(0.5), 0.00001);

        System.out.println(instance.icdf(0.5 + 0.341));
        assertEquals(1.0, instance.icdf(0.5 + 0.341), 0.01);

        System.out.println(instance.icdf(0.5 - 0.341));
        assertEquals(-1.0, instance.icdf(0.5 - 0.341), 0.01);

    }

    /**
     * Test of characteristic function method, of class AbstractRandomVariable.
     */
    @Test
    public void testCharFunction() {
        System.out.println("characteristic function");
        
        final double mean = 0.2;
        final double var = 1;
        
        GaussianNoise test = new GaussianNoise(mean, var);
        
        AbstractRandomVariable inst = new AbstractRandomVariable() {
            public double getMean() {  return mean; }
            public double getVariance() { return var; }
            public double pdf(double x) {
                double s = 1.0/Math.sqrt(2*Math.PI*var);
                double d = x - mean;
                return s * Math.exp( -(d*d)/(2*var) );
            }
        };

        for(double t = -1; t < 1; t+=0.01){
            Complex ct = test.characteristicFunction(t);
            Complex ci = inst.characteristicFunction(t);
            //System.out.println(ct + "\t" + ci);
            assertTrue( ct.subtract(ci).abs() < 0.001 );
        }

    }


}