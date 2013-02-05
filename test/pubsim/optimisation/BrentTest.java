/*
 */
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
public class BrentTest {
    
    public BrentTest() {
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
 public void QuadraticTest() {
    double tol = 1e-7;
    SingleVariateFunction f = new SingleVariateFunction() {
         public double value(double x) {
             return (x-2.0)*(x-2.0);
         }
     };
    Brent opt = new Brent(f, -4.0, 1.0, 5.0);
    assertTrue(Math.abs(0.0 - opt.fmin()) < tol);
    assertTrue(Math.abs(2.0 - opt.xmin()) < tol);

  }
  
    void QuarticTest() {
    double tol = 1e-7;
        SingleVariateFunction f = new SingleVariateFunction() {
         public double value(double x) {
             return x*x*x*x;
         }
     };
    Brent opt = new Brent(f, -2.0, 1.0, 2.0);
    assertTrue(Math.abs(0.0 - opt.fmin()) < tol);
    assertTrue(Math.abs(0.0 - opt.xmin()) < tol);
  }
}
