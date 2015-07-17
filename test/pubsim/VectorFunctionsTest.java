/*
 * VectorFunctionsTest.java
 * JUnit based test
 *
 * Created on 28 June 2007, 14:41
 */

package pubsim;

import Jama.Matrix;
import java.util.Set;
import junit.framework.*;
import static org.junit.Assert.*;
import static pubsim.VectorFunctions.*;

/**
 *
 * @author Robby McKilliam
 */
public class VectorFunctionsTest extends TestCase {
    
    public VectorFunctionsTest(String testName) {
        super(testName);
    }

    /**
     * Asserts that two vector have equal elements.
     * @param tol tolerance for how close doubles must be to each other
     * to be considered equal
     */
    public static void assertVectorsEqual(double[] x, double[] y, double tol){
        if(x.length != y.length)
            fail("Vectors are not the same length!");
        for(int i = 0; i < x.length; i++){
            assertEquals(x[i], y[i], tol);
        }
    }

    /** 
     * Asserts that two vector have equal elements. Sets a
     * default tolerance of  0.0000001.
     */
    public static void assertVectorsEqual(double[] x, double[] y){
        assertVectorsEqual(x, y, 0.0000001);
    }
    
    public void testFilledArrayInt() {
        int n = 10;
        int c = 4;
        int[] a = filledArray(n,c);
        assertEquals(a.length,n);
        for(int i = 0; i < n; i++) assertEquals(a[i],c);
    }

    /**
     * Test of slowFT method, of class simulator.VectorFunctions.
     */
    public void testSlowFT() {
        System.out.println("slowFT");
        
        double[] x = {2, 3, 1, 4};
        double[] Xi = new double[x.length];
        double[] Xr = new double[x.length];
        double[] expXi = {0, 1, 0, -1};
        double[] expXr = {10,1,-4 ,1};
        
        slowFT(x, Xi, Xr);   
        
        for(int i = 0; i < x.length; i++){
            assertEquals(true, Math.abs(expXi[i] - Xi[i]) < 0.00001);
            assertEquals(true, Math.abs(expXr[i] - Xr[i]) < 0.00001);
        }
       
    }

    /**
     * Test of abs2FT method, of class simulator.VectorFunctions.
     */
    public void testAbs2FT() {
        System.out.println("abs2FT");
        
        double[] x = {2, 3, 1, 4};
        
        double[] expResult = {100.0000, 2.0000, 16.0000, 2.0000};
        double[] result = VectorFunctions.abs2FT(x);
        assertEquals(true, VectorFunctions.distance_between(expResult, result) < 0.00001);
       
    }

     /**
     * Test of isZero method, of class simulator.VectorFunctions.
     */
    public void testIsZero() {
        System.out.println("isZero");
        
        double[] x = {2, 3, 1, 4};
        assertFalse(VectorFunctions.isZero(x, 1e-9));
        double[] x1 = {0, 0, 0, 0};
        assertTrue(VectorFunctions.isZero(x1, 1e-9));
       
    }
    
    /**
     * Test of distance_between method, of class simulator.VectorFunctions.
     */
    public void testDistance_between() {
        System.out.println("distance_between");
        
        double[] x = {0, 1, 3};
        double[] s = {0, 4, 1};
        
        double expResult = Math.sqrt(13.0);
        double result = VectorFunctions.distance_between(x, s);
        assertEquals(expResult,result, 1e-9);
  
    }

    public static void testMultiplyInPlace(){
        System.out.println("multiplyInPlace");
        double[] x = {2, 2, 2};
        multiplyInPlace(x, 0.5);
        for(double v : x)
            assertEquals(1.0, v, 0.0000001);
    }

    /**
     * Test of distance_between method, of class simulator.VectorFunctions.
     */
    public void testFirstDifference() {
        System.out.println("testFirstDifference");

        double[] x = {0, 1, 3};

        double[] expResult = {1, 2};
        double[] result = VectorFunctions.firstDifference(x);
        assertTrue(VectorFunctions.distance_between(result, expResult) < 0.00000001);

    }

    /**
     * Test of distance_between method, of class simulator.VectorFunctions.
     */
    public void testmthDifference() {
        System.out.println("testmthDifference");

        //test the second difference
        double[] x = {0, 1, 3, 2};
        //double[] expResult = {1, 2, -1};
        double[] expResult = {1, -3};
        
        double[] result = VectorFunctions.mthDifference(x, 2);
        assertTrue(VectorFunctions.distance_between(result, expResult) < 0.00000001);

    }

    /**
     * Test of angle_between method, of class simulator.VectorFunctions.
     */
    public void testAngle_between() {
        System.out.println("angle_between");
        
        double[] x = {0,1};
        double[] y = {1,1};
        
        double expResult = Math.PI/4;
        double result = VectorFunctions.angle_between(x, y);
        assertEquals(true, Math.abs(expResult - result) < 0.000001);

    }

    /**
     * Test of subtract method, of class simulator.VectorFunctions.
     */
    public void testSubtract() {
        System.out.println("subtract");
        
        double[] x = {1,2,3};
        double[] y = {1,-1,-1};
        
        double[] expResult = {0,3,4};
        double[] result = VectorFunctions.subtract(x, y);
        assertEquals(0.0, VectorFunctions.distance_between(expResult,result), 1e-9);
    }

    /**
     * Test of reflect
     */
    public void testReflect() {
        System.out.println("reflect");

        double[] x = {1,2,3};
        double[] r = {2,0,0};
        double[] expResult = {-1,2,3};
        double[] result = VectorFunctions.reflect(r, x);
        VectorFunctionsTest.assertVectorsEqual(expResult,result);

        double[] x1 = {1,0};
        double[] r1 = {1,1};
        double[] expResult1 = {0,-1};
        double[] result1 = VectorFunctions.reflect(r1, x1);
        VectorFunctionsTest.assertVectorsEqual(expResult1,result1);
        
        //test reflect u into v
        double[] u = {2,0,0};
        double[] v = {1,2,3};
        double um = magnitude(u);
        double vm = magnitude(v);
        double[] r3 = new double[3];
        for(int i = 0; i < r.length; i++) r3[i] = u[i]*vm - v[i]*um;
        //test reflection of u is v
        double[] ru = VectorFunctions.reflect(r3, u);
        for(int i = 0; i < u.length; i++) assertEquals(ru[i]/um, v[i]/vm, 0.000001);
        //test reflection of v is u
        double[] rv = VectorFunctions.reflect(r3, v);
        for(int i = 0; i < u.length; i++) assertEquals(rv[i]/vm, u[i]/um, 0.000001);
    }

    /**
     * Test of add method, of class simulator.VectorFunctions.
     */
    public void testAdd() {
        System.out.println("add");
        
        double[] x = {1,2,3};
        double[] y = {-1,1,1};
        
        double[] expResult = {0,3,4};
        double[] result = VectorFunctions.add(x, y);
        assertEquals(0.0, VectorFunctions.distance_between(expResult,result), 1e-9);
       
    }

    /**
     * Test of add method, of class simulator.VectorFunctions.
     */
    public void testAllElementsEqual() {
        System.out.println("allElementsEqual");

        double[] x = {1,2,3};
        assertFalse(allElementsEqual(x));
        double[] x1 = {1,1,1};
        assertTrue(allElementsEqual(x1));

    }

    /**
     * Test of sum method, of class simulator.VectorFunctions.
     */
    public void testSum() {
        System.out.println("sum");
        
        double[] x = {1,1,4};
        
        double expResult = 6;
        double result = VectorFunctions.sum(x);
        assertEquals(expResult, result, 1e-9);
       
    }
    
    public void testProd() {
        System.out.println("prod");
        
        {
        double[] x = {1,2,4};
        double expResult = 8;
        double result = VectorFunctions.prod(x);
        assertEquals(expResult, result, 1e-9);
        }
        
        {
        int[] x = {1,2,4};
        long expResult = 8;
        long result = VectorFunctions.prod(x);
        assertEquals(expResult, result);
        }
       
    }

    /**
     * Test of sum2 method, of class simulator.VectorFunctions.
     */
    public void testSum2() {
        System.out.println("sum2");
        
        double[] x = {1,2,3};
        
        double expResult = 14;
        double result = VectorFunctions.sum2(x);
        assertEquals(expResult, result, 1e-9);

    }

    /**
     * Test of mean method, of class simulator.VectorFunctions.
     */
    public void testMean() {
        System.out.println("mean");
        
        double[] x = {1,2,3};
        
        double expResult = 2;
        double result = VectorFunctions.mean(x);
        assertEquals(expResult, result, 1e-9);

    }

    /**
     * Test of magnitude method, of class simulator.VectorFunctions.
     */
    public void testMagnitude() {
        System.out.println("magnitude");
        
        double[] x = {1,2,3};
        
        double expResult = Math.sqrt(14.0);
        double result = VectorFunctions.magnitude(x);
        assertEquals(expResult, result, 1e-9);
        
    }

    /**
     * Test of max method, of class simulator.VectorFunctions.
     */
    public void testMax() {
        System.out.println("max");
        
        double[] x = {1,4,2,3,-5};
        
        double expResult = 4.0;
        double result = VectorFunctions.max(x);
        assertEquals(expResult, result, 1e-9);
    }

    public void testmoduloParalellepiped() {
        System.out.println("moduloParalellepiped");

        double[] x = {1.1,4.2,2.1,3.6,-5.1};
        double[] exp = VectorFunctions.fracpart(x);
        Matrix P = Matrix.identity(x.length, x.length);
        double[] res = moduloParallelepiped(x, P);
        assertVectorsEqual(exp, res);
    }

    /**
     * Test of increasing method, of class simulator.VectorFunctions.
     */
    public void testIncreasing() {
        System.out.println("increasing");
        
        double[] x1 = {1,4,2,3,-5};
        double[] x2 = {1,2,4,7};
        
        assertEquals(false, VectorFunctions.increasing(x1));
        assertEquals(true, VectorFunctions.increasing(x2));
       
    }

    /**
     * Test of dot method, of class simulator.VectorFunctions.
     */
    public void testDot() {
        System.out.println("dot");
        
        double[] x = {1,2,3};
        double[] y = {1,-1,4};
        
        double expResult = 11.0;
        double result = VectorFunctions.dot(x, y);
        assertEquals(expResult, result, 1e-9);
       
    }
    
     /**
     * Test of dot method for Matrix
     */
    public void testDotMatrix() {
        System.out.println("dot with Matrix");
        
        double[] x = {1,2,3};
        double[] y = {1,-1,4};
        Matrix xr = VectorFunctions.rowMatrix(x);
        Matrix xc = VectorFunctions.columnMatrix(x);
        Matrix yr = VectorFunctions.rowMatrix(y);
        Matrix yc = VectorFunctions.columnMatrix(y);
        
        double expResult = 11.0;
        double result = VectorFunctions.dot(x, y);
        assertEquals(expResult, result, 1e-9);
        result = VectorFunctions.dot(xr, yr);
        assertEquals(expResult, result, 1e-9);
       result = VectorFunctions.dot(xc, yr);
        assertEquals(expResult, result, 1e-9);
        result = VectorFunctions.dot(xc, yc);
        assertEquals(expResult, result, 1e-9);
        result = VectorFunctions.dot(xr, yc);
        assertEquals(expResult, result, 1e-9);
    }

    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testMin() {
        System.out.println("min");
        
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        
        double expResult = -11.0;
        double result = VectorFunctions.min(x);
        assertEquals(expResult, result, 1e-9);
        
    }

    /**
     * Test of maxDistance method, of class simulator.VectorFunctions.
     */
    public void testMax_distance() {
        System.out.println("max_distance");
        
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        
        double expResult = 20.0;
        double result = VectorFunctions.maxDistance(x);
        assertEquals(expResult, result, 1e-9);
    }

    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testgetSubVector() {
        System.out.println("subVector");

        int start = 1;
        int end = 4;
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        double[] exp = {-11.0, -11.0, 1.0, 2.0 };

        double[] res = VectorFunctions.getSubVector(x, start, end);
        System.out.println(VectorFunctions.print(res));
        assertVectorsEqual(exp, res);

    }

    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testFillVector() {
        System.out.println("fillVector");

        int start = 1;
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        double[] f = {3.0, 3.0, 3.0 };
        double[] exp = {-1.0, 3.0, 3.0, 3.0, 2.0, 9.0, 1.0};

        VectorFunctions.fillVector(x, f, start);
        //System.out.println(VectorFunctions.print(x));
        assertVectorsEqual(exp, x);

        int start1 = 4;
        double[] x1 = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        double[] f1 = {3.0, 3.0, 3.0, 3.0 };
        double[] exp1 = {-1.0, -11.0, -11.0, 1.0, 3.0, 3.0, 3.0};

        VectorFunctions.fillVector(x1, f1, start1);
        //System.out.println(VectorFunctions.print(x1));
        assertVectorsEqual(exp1, x1);

        int start2 = 3;
        Double[] x2 = new Double[20];
        Double[] exp2 = new Double[20];
        Double[] f2 = new Double[3];
        for(int i = 0; i < f.length; i++){
            f2[i] = new Double(1.0);
            exp2[i+start2] = new Double(1.0);
        }
        VectorFunctions.fillVector(x2, f2, start2);
        assertArrayEquals(exp2, x2);

    }

        /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testFillEnd() {
        System.out.println("fillVector");

        int start = 1;
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        double[] f = {3.0, 3.0, 3.0 };
        double[] exp =  {-1.0, -11.0, -11.0, 1.0, 3.0, 3.0, 3.0};

        VectorFunctions.fillEnd(x, f);
        System.out.println(VectorFunctions.print(x));
        assertVectorsEqual(exp, x);

    }
    
    /**
     * Test of increasing method, of class simulator.VectorFunctions.
     */
    public void testConv() {
        System.out.println("conv");
        
        double[] x1 = {1, -1};
        double[] x2 = {1, -1};
        double[] exp = {1, -2, 1};
        
        double[] res = VectorFunctions.conv(x1, x2);
        System.out.println(VectorFunctions.print(res));
        assertEquals(true, VectorFunctions.distance_between2(exp, res) < 0.00001);
        
        double[] x12 = {1,-1, 2};
        double[] x22 = {1,-1, 3, 4};
        double[] exp2 = {1, -2, 6, -1, 2, 8};
        
        double[] res2 = VectorFunctions.conv(x12, x22);
        System.out.println(VectorFunctions.print(res2));
        assertEquals(true, VectorFunctions.distance_between2(exp2, res2) < 0.00001);
       
    }



    /**
     * Test of maxDistance method, of class simulator.VectorFunctions.
     */
    public void testMatrixMultVector() {
        System.out.println("matrixMultVector");
        
        double[] x = {1, 2, 3};
        double[][] M = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        double[] y = new double[M.length];
        
        double[] expResult =  {14, 32, 50, 68};
        VectorFunctions.matrixMultVector(M, x, y);
        assertEquals(true, 
                VectorFunctions.distance_between(expResult,y)<0.00001);
    }
    
    /**
     * Test of stable determinant method, of class simulator.VectorFunctions.
     */
//    public void testStableDet() {
//        System.out.println("stableDet");
//        
//        int m = 10;
//        
//        Matrix mat = Matrix.identity(m,m);
//        assertEquals(true, Math.abs(mat.det() - VectorFunctions.stableDet(mat)) < 0.00001);
//        
//        mat = Matrix.random(m,m);
//        assertEquals(true, Math.abs(mat.det() - VectorFunctions.stableDet(mat)) < 0.00001);
//
//    }
    
        /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testSwapColumns() {
        System.out.println("testSwapColumns");
        
        Matrix mat = Matrix.identity(4, 3);
        Matrix matCopy = mat.copy();
        VectorFunctions.swapColumns(mat, 0,1);
        
        //System.out.println(VectorFunctions.print(mat));
        //System.out.println(VectorFunctions.print(matCopy));
        
        int j0 = 0;
        int j1 = 1;
        for(int i = 0; i < mat.getRowDimension(); i++){
            assertEquals(mat.get(i, j0), matCopy.get(i, j1), 1e-9);
            assertEquals(mat.get(i, j1), matCopy.get(i, j0), 1e-9);
        }
        
    }
    
    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testSwapRows() {
        System.out.println("testSwapRows");
        
        Matrix mat = Matrix.identity(3, 4);
        Matrix matCopy = mat.copy();
        VectorFunctions.swapRows(mat, 0,1);
        
        System.out.println(VectorFunctions.print(mat));
        System.out.println(VectorFunctions.print(matCopy));
        
        int j0 = 0;
        int j1 = 1;
        for(int i = 0; i < mat.getColumnDimension(); i++){
            assertEquals(mat.get(j0, i), matCopy.get(j1, i), 1e-9);
            assertEquals(mat.get(j1, i), matCopy.get(j0, i), 1e-9);
        }
        
    }
    
    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testGivensRotation() {
        System.out.println("testGivensRotation");
        
        Matrix M = Matrix.random(5, 5);
        double pdet = M.det();
        
        System.out.println(VectorFunctions.print(M));
        
        
        int m1 = 0, m2 = 1;
        int n = 0;
        VectorFunctions.givensRotate(M, m1, m2, n);
        
         System.out.println(VectorFunctions.print(M));
        
        assertTrue(Math.abs(M.get(m2, n)) < 0.00000001);
        assertTrue(Math.abs(M.det() - pdet)< 0.00000001);
        
    }

    public void testPackRowiseToMatrix(){
        System.out.println("packRowiseToMatrix");
        double[] y = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[][] testu = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        double[][] u = VectorFunctions.packRowiseToMatrix(y, 2);

        for(int m = 0; m < 2; m++){
            for(int n = 0; n < 5; n++){
                assertEquals(testu[m][n], u[m][n], 0.0000001);
            }
        }

    }
    
    public void testPackRowiseToArray(){
        System.out.println("packRowiseToArray");
        double[] expy = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] y = new double[expy.length];
        double[][] testu = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        VectorFunctions.packRowiseToArray(new Matrix(testu), y);

        for(int m = 0; m < y.length; m++){
            assertEquals(y[m], expy[m], 0.00000001);
        }

    }

    public void testunpackRowise(){
        System.out.println("testunpackRowise");
        double[][] testB = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        double[] expres = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Matrix B = Matrix.constructWithCopy(testB);
        double[] res = VectorFunctions.unpackRowise(B);
        for(int n = 0; n < res.length; n++){
            assertEquals(res[n],expres[n] , 0.0000001);
        }

    }

    public void testunpackColumnwise(){
        System.out.println("testunpackColumnwise");
        double[][] testB = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        double[] expres = { 1, 6, 2, 7, 3, 8, 4, 9, 5, 10};
        Matrix B = Matrix.constructWithCopy(testB);
        double[] res = VectorFunctions.unpackColumnwise(B);
        for(int n = 0; n < res.length; n++){
            assertEquals(res[n],expres[n] , 0.0000001);
        }

    }

    public void testGetColumn(){
        System.out.println("getColumn");
        double[] y = {2, 7};
        double[][] testu = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        double[] u = getColumn(1, new Matrix(testu));

        System.out.println(print(u));

        assertVectorsEqual(u, y);

    }

    public void testGetRow(){
        System.out.println("getRow");
        double[] y = {6, 7, 8, 9, 10};
        double[][] testu = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        double[] u = getRow(1, new Matrix(testu));

        System.out.println(print(u));

        assertVectorsEqual(u, y);

    }

    public void testAddMultipleOfColiToColj(){
        System.out.println("addMultipleOfColiToColj");
        int n = 4;
        Matrix M = Matrix.random(n, n);

        System.out.println(print(M));
        addMultipleOfColiToColj(M, 2, 0, 2);
        System.out.println(print(M));

    }

    public void testminColumnNorm(){
        System.out.println("minColumnNorm");
        double[][] testu = { {1, 1, 1}, {2,1,3} };
        Matrix M = new Matrix(testu);
        double exp = minColumnNorn(M);
        System.out.println(exp);

        assertEquals(2.0, exp, 0.0000001);

    }

    public void testOrthogonalise(){
        System.out.println("orthogonalise");
        double[][] testu = { {1, 1, 1, 1, 1}, {1, 2, 3, 4, 5}};
        Matrix A = new Matrix(testu).transpose();
        //System.out.println("A = \n" + print(A));
        A = orthogonalise(A);
        //System.out.println("Ap = \n" + print(A));
        double[][] exp = { {1, 1, 1, 1, 1}, {-2, -1, 0, 1, 2}};
        Matrix Aexp = new Matrix(exp).transpose();
        //System.out.println("Aexp = \n" + print(Aexp));
        assertTrue(A.minus(Aexp).normF() < 0.0000001);

        double[][] testu2 = { {1, 1, 1, 1, 1}, 
                                        {1, 2, 3, 4, 5},
                                        {1, 1, 2, 2, 3}};
        Matrix A2 = new Matrix(testu2).transpose();
        System.out.println("A2 = \n" + print(A2));
        A2 = orthogonalise(A2);
        System.out.println("A2p = \n" + print(A2));
        double[][] exp2 = { {1, 1, 1, 1, 1}, {-2, -1, 0, 1, 2}};
        Matrix Aexp2 = new Matrix(exp2).transpose();
        //System.out.println("Aexp = \n" + print(Aexp));
        //assertTrue(A2.minus(Aexp2).normF() < 0.0000001);
    }

    public void testColumnSquareSum(){
        System.out.println("columnSquareSum");
        double[][] testu = { {1, 1, 1, 1, 1}, {1, 2, 3, 4, 5}};
        Matrix A = new Matrix(testu);
        double[] out = columnSquareSum(A);
        double[] exp = { 1+1, 1+2*2, 1+3*3,1+4*4,1+5*5};
        assertVectorsEqual(exp, out);
    }


    /**
     * Returns polynomials of the for 1 - x^d
     * @param d
     * @return
     */
    public double[] diffPoly(int d){
        if(d < 1)
            throw new RuntimeException("d must be greater than 1");
        double[] v = new double[d+1];
        v[0] = 1;
        v[d] = -1;
        return v;
    }

    /**
     * Test of increasing method, of class simulator.VectorFunctions.
     */
    public void testPolyDiffConv() {
        System.out.println("PolyDiffConv");

        double[] v = diffPoly(1);
        System.out.print(print(v));
        System.out.println("  norm = " + sum2(v));
        v = conv(v, diffPoly(2));
        System.out.print(print(v));
        System.out.println("  norm = " + sum2(v));
        v = conv(v, diffPoly(3));
        System.out.print(print(v));
        System.out.println("  norm = " + sum2(v));
        v = conv(v, diffPoly(5));
        System.out.print(print(v));
        System.out.println("  norm = " + sum2(v));
        v = conv(v, diffPoly(7));
        System.out.print(print(v));
        System.out.println("  norm = " + sum2(v));
        v = conv(v, diffPoly(8));
        System.out.print(print(v));
        System.out.println("  norm = " + sum2(v));
        for(int i = 1; i < 30; i++){
            double[] vv = conv(v, diffPoly(i));
            System.out.print(print(vv));
            System.out.println("  norm = " + sum2(vv));
        }
    }
    
    /**
     * Test of slowFT method, of class simulator.VectorFunctions.
     */
    public void testpreppendColumn() {
        System.out.println("preppend columnn");
        
        Matrix M = new Matrix(3,1);
        M.set(0,0,1); M.set(1,0,2); M.set(2,0,3);
        System.out.println(print(M));
        System.out.println(print(prependColumnMatrix(M, 0)));
       
    }

    public void testsplitColumns() {
        System.out.println("test split matrix into set of columns");
        
        Matrix M = Matrix.identity(3, 3);
        System.out.println(print(M));
        Set<Matrix> S = VectorFunctions.splitColumns(M);
        for(Matrix v : S) System.out.println(print(v));
       
    }
        
    public void testIsUnimodular() {
        Matrix I = Matrix.identity(5,5);
        assertTrue(isUnimodular(I)); //test identity is unimodular
        
        //construct lower triangular matrix with unit diagonal
        Matrix A = Matrix.identity(5,5);
        A.set(1,0, 10);
        A.set(2,1, -3);
        A.set(2,0, 4);
        A.set(4,3, -2);
        assertTrue(isUnimodular(A)); //test identity is unimodular
        assertTrue(isUnimodular(A.inverse())); //assert invere is unimodular
        
        //construct upper triangular matrix with unit diagonal
        Matrix B = Matrix.identity(5,5);
        B.set(0,2, 10);
        B.set(0,2, -3);
        B.set(1,3, 4);
        B.set(2,4, -2);
        assertTrue(isUnimodular(B)); //test identity is unimodular
        assertTrue(isUnimodular(B.inverse())); //assert invere is unimodular
        
        //check product of A and B
        assertTrue(isUnimodular(A.times(B)));
         assertTrue(isUnimodular(B.times(A)));
    }
    
    public void testreadPARIGPFormat() {
        System.out.println("test read PARI/GP");
        {
            String mat = "[1,2;3,4]";
            Matrix M = readPARIGPFormat(mat);
            double tol = 1e-9;
            assertEquals(M.get(0, 0), 1, tol);
            assertEquals(M.get(0, 1), 2, tol);
            assertEquals(M.get(1, 0), 3, tol);
            assertEquals(M.get(1, 1), 4, tol);
            M.print(10, 10);
        }
        {
            Matrix M = readPARIGPFormatFromFile("tdata/parimattest");
            double tol = 1e-9;
            M.print(10, 10);
            assertTrue(M.getColumnDimension() == 3);
            assertTrue(M.getRowDimension() == 2);
            assertEquals(M.get(0, 0), 0.5, tol);
            assertEquals(M.get(0, 1), 1.0, tol);
            assertEquals(M.get(0, 2), 1.5, tol);
            assertEquals(M.get(1, 0), 2.3, tol);
            assertEquals(M.get(1, 1), 1, tol);      
            assertEquals(M.get(1, 2), 5, tol); 
        }
    }
    
}
