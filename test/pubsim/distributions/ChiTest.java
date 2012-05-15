/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author Robby McKilliam
 */
public class ChiTest {
    
    public ChiTest() {
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
     * Test of getVariance method, of class ChiSquared.
     */
    @Test
    public void testGetVariance() {
        System.out.println("getVariance");
        Chi instance = new Chi(4, 0.5);
        int iters = 50000;
        double sum = 0.0;
        for(int i = 0; i < iters; i++){
            double val = instance.getNoise() - instance.getMean();
            sum += val*val;
        }
        double sampvar = sum/iters;
        assertEquals(instance.getVariance(), sampvar,0.05);

    }

    /**
     * Test of pdf method, of class ChiSquared.
     */
    @Test
    public void testPdf() {
        System.out.println("pdf");
        Double x = null;
        final Chi instance = new Chi(5,0.4);
        final int INTEGRAL_STEPS = 5000;
        
        //check mean numerically
        double meanint = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    return x*instance.pdf(x);
                }
            }, 0, 500)).gaussQuad(INTEGRAL_STEPS);
        assertEquals(instance.getMean(), meanint,0.001);
        
        //check variance numerically
        double varint = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    double xm = x - instance.getMean();
                    return xm*xm*instance.pdf(x);
                }
            }, 0, 500)).gaussQuad(INTEGRAL_STEPS);
        assertEquals(instance.getVariance(), varint,0.001);
        
        
        final Chi instance2 = new Chi.Chi2(0.333);
        
        //check mean numerically
        double meanint2 = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    return x*instance2.pdf(x);
                }
            }, 0, 500)).gaussQuad(INTEGRAL_STEPS);
        assertEquals(instance2.getMean(), meanint2,0.001);
        
        //check variance numerically
        double varint2 = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    double xm = x - instance2.getMean();
                    return xm*xm*instance2.pdf(x);
                }
            }, 0, 500)).gaussQuad(INTEGRAL_STEPS);
        assertEquals(instance2.getVariance(), varint2,0.001);
        
    }
}
