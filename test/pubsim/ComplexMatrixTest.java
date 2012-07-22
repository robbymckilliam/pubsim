/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim;

import Jama.Matrix;
import org.junit.*;
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
     * Test of toComplex method, of class ComplexMatrix.
     */
    @Test
    public void testKroneckerProduct() {
        System.out.println("test Kronecker product");
        Complex[][] C = new Complex[2][2];
        C[0][0] = new Complex(1,0);
        C[1][0] = new Complex(1,0);
        C[0][1] = new Complex(0,2);
        C[1][1] = new Complex(1,1);
        ComplexMatrix Cmat = new ComplexMatrix(C);
        System.out.println(VectorFunctions.print(Cmat.getComplexArray()));
        ComplexMatrix K = ComplexMatrix.kroneckerProduct(Cmat, Cmat);
        System.out.println(VectorFunctions.print(K.getComplexArray()));
    }

    
    /**
     * Test of toComplex method, of class ComplexMatrix.
     */
    @Test
    public void testScalarMultiply() {
        System.out.println("test scalar multiply");
        Complex[][] C = new Complex[2][2];
        C[0][0] = new Complex(1,0);
        C[1][0] = new Complex(1,1);
        C[0][1] = new Complex(0,2);
        C[1][1] = new Complex(2,1);
        ComplexMatrix Cmat = new ComplexMatrix(C);
        System.out.println(VectorFunctions.print(Cmat.getComplexArray()));
        System.out.println(VectorFunctions.print(Cmat.times(new Complex(0,1)).getComplexArray()));
    }
}
