/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class QRDecompositionTest {

    public QRDecompositionTest() {
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
     * Test of getR method, of class QRDecomposition.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        
        int n = 11;
        int m = 15;
        Matrix B = Matrix.random(m, n);
        
        QRDecomposition instance = new QRDecomposition(B);
        Matrix R = instance.getR();
        
        for(int i = 0; i<n; i++){
            assertTrue(R.get(i, i) >= 0);
        }
    }   

    /**
     * Test of getQ method, of class QRDecomposition.
     */
    @Test
    public void testGetQ() {
        System.out.println("getQ");
        int n = 11;
        int m = 12;
        Matrix B = Matrix.random(m, n);
        
        QRDecomposition instance = new QRDecomposition(B);
        Matrix R = instance.getR();
        
        for(int i = 0; i<n; i++){
            assertTrue(R.get(i, i) >= 0);
        }
        
        Matrix Q = instance.getQ();
        Matrix Bt = Q.times(R);
        assertTrue(Bt.minus(B).normF() < 0.00001);
        
        //System.out.println(Q.getColumnDimension());
        //System.out.println(Q.getRowDimension());
        
        for(int i = 1; i < Q.getColumnDimension(); i++){
            double mag = VectorFunctions.magnitude(Q.getMatrix(0, m-1, i, i).getColumnPackedCopy());
            assertTrue(Math.abs(mag - 1.0) < 0.00001);
        }
        
        assertTrue(Q.transpose().times(Q).minus(Matrix.identity(n, n)).normF() < 0.00001);
        
        
    }

}