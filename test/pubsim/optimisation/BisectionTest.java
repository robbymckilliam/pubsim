package pubsim.optimisation;

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
public class BisectionTest {
    
    public BisectionTest() {
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

    @Test
    public void testLinearZero() {
        System.out.println("zero");
        SingleVariateFunction f = new SingleVariateFunction() {
            @Override
            public double value(double x) {
               return x;
            }
        };
        double tol = 1e-7;
        double x = Bisection.zero(f,-11.0,8.0,tol);
        assertEquals(0.0, x, tol);
    }
    
    @Test
    public void testCubicZero() {
        System.out.println("zero");
        SingleVariateFunction f = new SingleVariateFunction() {
            @Override
            public double value(double x) {
               return x*x*(x-1);
            }
        };
        double tol = 1e-7;
        double x = Bisection.zero(f,0.5,1.7,tol);
        assertEquals(1.0, x, tol);
    }
    
}
