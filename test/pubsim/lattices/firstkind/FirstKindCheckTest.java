/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.firstkind;

import Jama.Matrix;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.AnstarLinear;
import pubsim.lattices.Dn;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;

/**
 *
 * @author Robby McKilliam
 */
public class FirstKindCheckTest {
    
    public FirstKindCheckTest() {
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
     * Test that the lattice An is of first kind.
     */
    @Test
    public void testFirstKindCheckAn() {
        System.out.println("An is first kind");
        int n = 4;
        assertTrue(new FirstKindCheck(new AnFastSelect(n)).isFirstKind);       
    }

    /**
     * Test that the lattice An is of first kind.
     */
    @Test
    public void testFirstKindCheckAnstar() {
        System.out.println("Anstar is first kind");
        int n = 4;
        assertTrue(new FirstKindCheck(new AnstarLinear(n)).isFirstKind);       
    }
    
    @Test
    public void testRandom1DisfirstKind() {
        System.out.println("random 1-dimensional lattice is first kind");
        Matrix B = Matrix.random(4, 1);
        assertTrue(new FirstKindCheck(new LatticeAndNearestPointAlgorithm(B)).isFirstKind);    
    }
    
    @Test
    public void testRandom2DisfirstKind() {
        System.out.println("random 2-dimensional lattice is first kind");
        Matrix B = Matrix.random(4, 2);
        assertTrue(new FirstKindCheck(new LatticeAndNearestPointAlgorithm(B)).isFirstKind);    
    }
    
//    @Test
//    public void testRandom3DisfirstKind() {
//        System.out.println("random 3-dimensional lattice is first kind");
//        Matrix B = Matrix.random(4, 3);
//        assertTrue(new FirstKindCheck(new LatticeAndNearestPointAlgorithm(B)).isFirstKind);    
//    }
    
    @Test
    public void testD3isfirstKind() {
        System.out.println("Test that the root lattice D3 is first kind");
        assertTrue(new FirstKindCheck(new Dn(3)).isFirstKind);    
    }
    
    @Test
    public void testD4isfirstKind() {
        System.out.println("Test that the root lattice D4 is not first kind");
        FirstKindCheck fkc = new FirstKindCheck(new Dn(5));
        assertTrue(fkc.isFirstKind);
        for(Matrix v : fkc.obtuseSuperbase() ) System.out.println(VectorFunctions.print(v));
    }
    
    /**
     * Test of isObtuse method, of class FirstKindCheck.
     */
    @Test
    public void testIsObtuse() {
        System.out.println("is Obtuse");
        int n = 5;
        Matrix B = new AnFastSelect(n).getGeneratorMatrixBig();
        Set<Matrix> S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheck.isObtuse(S));
        
        B = new AnstarLinear(n).getGeneratorMatrixBig();
        S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheck.isObtuse(S));
        
        B = Matrix.identity(n, n);
        S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheck.isObtuse(S));
        
        B.set(1,0,1.0);
        S = VectorFunctions.splitColumns(B);
        assertFalse(FirstKindCheck.isObtuse(S));
    }

    /**
     * Test of isSuperbase method, of class FirstKindCheck.
     */
    @Test
    public void testIsSuperbase() {
        System.out.println("is Superbase");
        int n = 5;
        Matrix B = new AnFastSelect(n).getGeneratorMatrixBig();
        Set<Matrix> S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheck.isSuperbase(S));
        
        B = new AnstarLinear(n).getGeneratorMatrixBig();
        S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheck.isSuperbase(S));
        
        B = Matrix.identity(n, n);
        S = VectorFunctions.splitColumns(B);
        assertFalse(FirstKindCheck.isSuperbase(S));
    }
    
}
