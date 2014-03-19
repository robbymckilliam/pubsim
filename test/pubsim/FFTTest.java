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
public class FFTTest {
    
    public FFTTest() {
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

    static double tolerance = 1e-6;
  
  @Test
  public void testVersusMatlabTest() {
    System.out.println("Testing FFT versus Matlab");
    final Complex[] x = {new Complex(1,0), new Complex(2,0), new Complex(3,0) };
    final FFT fft = new FFT(x.length);
    final Complex[] fftx = fft.forward(x);
    final Complex[] expected = {new Complex(6,0), new Complex(-1.5,0.866025403784439), new Complex(-1.5,-0.866025403784439) };
    for( int i = 0; i < fftx.length; i++ ) {
      assertEquals(expected[i].re(), fftx[i].re(), tolerance);
      assertEquals(expected[i].im(), fftx[i].im(), tolerance);
    }
    
    final Complex[]  ifftx = fft.inverse(fftx);
    for( int i = 0; i < x.length; i++) {
      assertEquals(x[i].re(), ifftx[i].re(), tolerance);
      assertEquals(x[i].im(), ifftx[i].im(), tolerance);
    }
    
  }
  
    @Test
   public void convTest() {
     System.out.println("Testing conv versus Matlab");
    Complex[] a = {new Complex(1,0), new Complex(2,1)};
    Complex[] b = {new Complex(3,0), new Complex(2,0), new Complex(1,0)};
    Complex[] c = FFT.conv(a,b);
    Complex[] expected = {new Complex(3,0), new Complex(8,3), new Complex(5,2), new Complex(2,1) };
    //for( i <- c ) println(i)
    for( int i = 0; i < c.length; i++ ) {
      assertEquals(expected[i].re, c[i].re, tolerance);
      assertEquals(expected[i].im, c[i].im, tolerance);
    }
    Complex[] cvalid = FFT.conv_valid(a,b);
    Complex[] expvalid = {new Complex(8,3), new Complex(5,2)};
    //for( i <- cvalid ) println(i)
    for( int i = 0; i < expvalid.length; i++ ) {
      assertEquals(expvalid[i].re, cvalid[i].re, tolerance);
    }
    
  }
    
    
    
}