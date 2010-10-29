/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim;

import robbysim.VectorFunctions;
import robbysim.SubMatrix;
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
public class SubMatrixTest {

    public SubMatrixTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        M = Jama.Matrix.random(n,n);
        System.out.println(VectorFunctions.print(M));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    static protected Jama.Matrix M;
    static int n = 6;

    @Before
    public void setUp() {
       
    }

    @After
    public void tearDown() {
    }


    /**
     * Test of get method, of class SubMatrix.
     */
    @Test
    public void testGetWorksOnFullMatrix() {
        System.out.println("testGetWorksOnFullMatrix");
        SubMatrix instance = new SubMatrix(M);
        double expResult = M.get(0, 0);
        double result = instance.get(0, 0);
        assertEquals(expResult, result, 0.0000001);
        expResult = M.get(1, 1);
        result = instance.get(1, 1);
        assertEquals(expResult, result, 0.0000001);
        expResult = M.get(1, 2);
        result = instance.get(1, 2);
        assertEquals(expResult, result, 0.0000001);
    }

    /**
     * Test of get method, of class SubMatrix.
     */
    @Test
    public void testGetWorksOnSubMatrix() {
        System.out.println("testGetWorksOnSubMatrix");
        int offm = 1;
        int offn = 1;
        int lastm = n-2;
        int lastn = n-2;
        SubMatrix full = new SubMatrix(M);
        SubMatrix instance = full.getSubMatrix(offm, lastm, offn, lastn);
        System.out.println(VectorFunctions.print(instance.getJamaMatrix()));

        try{
            instance.get(6, 6);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e);
        }

        SubMatrix inst2 = instance.getSubMatrix(1, instance.getRowDimension()-2,
                1, instance.getColumnDimension()-2);
        System.out.println(VectorFunctions.print(inst2.getJamaMatrix()));

        SubMatrix inst3 = instance.getSubMatrix(0, instance.getRowDimension()-1,
                1, instance.getColumnDimension()-2);
        System.out.println(VectorFunctions.print(inst3.getJamaMatrix()));

         try{
            instance.getSubMatrix(1, instance.getRowDimension(),
                1, instance.getColumnDimension()-2);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e);
        }

        try{
            instance.getSubMatrix(-1, instance.getRowDimension()-1,
                1, instance.getColumnDimension()-2);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e);
        }

    }

}