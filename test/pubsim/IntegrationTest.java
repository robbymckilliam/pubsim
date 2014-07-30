package pubsim;

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
public class IntegrationTest {
    
    public IntegrationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of trapezoid method, of class Integration.
     */
    @Test
    public void testTrapezoid() {
        System.out.println("trapezoid");
        double a = -4;
        double b = 3;
        int N = 500;
        Integration instance = new Integration() {
            @Override
            public double f(double x) {
                return x*x;
            }
        };
        double expResult = b*b*b/3 - a*a*a/3;
        double result = instance.trapezoid(a, b, N);
        assertEquals(expResult, result, 0.001);
    }
    
}
