/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim;

import pubsim.Complex;
import pubsim.ComplexMatrix;
import pubsim.VectorFunctions;
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
public class ComplexMatrixTest {

    public ComplexMatrixTest() {
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
     * Test of complexToDouble method, of class ComplexMatrix.
     */
    @Test
    public void testComplexToDouble() {
        System.out.println("complexToDouble");
        double[] hr = {2,1,1,0,0,0};
        double[] hi = {1,0,0,0,0,0};
        Complex[] h = ComplexMatrix.toComplexArray(hr,hi);
        Complex[][] H = pubsim.quantisedFTs.Util.circulantChannelMatrix(h);
        double[][] D = ComplexMatrix.complexToDouble(H);
        System.out.println(VectorFunctions.print(H));
        System.out.println(VectorFunctions.print(D));
        System.out.println(VectorFunctions.print(ComplexMatrix.doubleToComplex(D)));
    }

    /**
     * Test of toDoubleMatrix method, of class Complex.
     */
    @Test
    public void testToDoubleMatrix() {
        System.out.println("toDoubleMatrix");
        Complex c = new Complex(1.0,1.0);
        double[][] D = ComplexMatrix.toDoubleMatrix(c);
        System.out.println(VectorFunctions.print(D));
    }

    /**
     * Test of toDoubleMatrix method, of class Complex.
     */
    @Test
    public void testTimes() {
        System.out.println("test Times");
        double[] hr = {2,1,1,0,0,0};
        double[] hi = {0,0,0,0,0,0};
        Complex[] h = ComplexMatrix.toComplexArray(hr,hi);
        ComplexMatrix H = new ComplexMatrix(
                pubsim.quantisedFTs.Util.circulantChannelMatrix(h));
        System.out.println(H.times(H));
    }


}