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
public class ComplexTest {
    
    public ComplexTest() {
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

    final double tol = 1e-7;
    
    @Test
    public void testPow() {
        System.out.println("pow");
        Complex A = new Complex(1,0);
        assertTrue( (A.minus(A.pow(10))).abs() < tol );
        A = new Complex(2,3).pow(2.2); //testing versus Mathematica output
        assertTrue( (A.minus(new Complex(-9.36631, 13.9481))).abs() < 0.0001);
    }
    
    @Test
    public void testUnitCirclePow() {
        System.out.println("unit circle pow");
        final double phase = 0.4;
        Complex.UnitCircle A = new Complex.UnitCircle(phase);
        assertEquals(phase*3, A.pow(3).phase(), 0.00001);
        Complex B = Complex.polar(1.0, phase);
        assertTrue( (A.subtract(B)).abs() < 0.000001);
        assertTrue( ((A.pow(3.3)).subtract(B.pow(3.3))).abs() < 0.000001);
    }
    
    @Test
    public void testUnitTimes() {
        System.out.println("Unit circle multiplied by unit circle");
        Complex.UnitCircle A = new Complex.UnitCircle(0.4);
        Complex.UnitCircle B = new Complex.UnitCircle(0.6);
        Complex.UnitCircle C = new Complex.UnitCircle(1.0);
        assertTrue( ((A.times(B).subtract(C)) ).abs() < 0.000001);
        Complex Ap = Complex.polar(1,0.4);
        Complex Bp = Complex.polar(1, 0.6);
        Complex Cp = Ap.times(Bp);
        assertTrue( ((Cp.subtract(C))).abs() < 0.000001);
    }
    
}