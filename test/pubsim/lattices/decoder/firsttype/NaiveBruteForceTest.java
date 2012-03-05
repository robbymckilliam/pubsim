/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import pubsim.lattices.Anstar.AnstarSorted;
import pubsim.VectorFunctions;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
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
public class NaiveBruteForceTest {
    
    public NaiveBruteForceTest() {
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
     * Test of constructor correctly detects and computes obtuse bases.
     */
    @Test
    public void testNearestPointWithAn() {
        System.out.println("test nearest point with An");
        
        int n = 12;
        LatticeAndNearestPointAlgorithm lattice = new AnFastSelect(n);      
        NaiveBruteForce cut =new NaiveBruteForce(lattice);
        
        int iters  = 100;
        for(int i = 0; i < iters; i++){
            double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);
            
            Anstar.project(y, y);
            
            cut.nearestPoint(y);
            lattice.nearestPoint(y);
            
            
            System.out.println(VectorFunctions.print(cut.getIndex()));
            System.out.println(VectorFunctions.print(lattice.getIndex()));
            System.out.println(VectorFunctions.print(cut.getLatticePoint()));
            System.out.println(VectorFunctions.print(lattice.getLatticePoint()));
            System.out.println();
             
             
            
            double dist = VectorFunctions.distance_between2(lattice.getLatticePoint(), cut.getLatticePoint());
            assertTrue(dist < 0.0001);
            
        }
    }
    
    /**
     * Test of constructor correctly detects and computes obtuse bases.
     */
    @Test
    public void testNearestPointWithAnStar() {
        System.out.println("test nearest point with An*");
        
        int n = 12;
        LatticeAndNearestPointAlgorithm lattice = new AnstarSorted(n);      
        NaiveBruteForce cut =new NaiveBruteForce(lattice);
        
        int iters  = 100;
        for(int i = 0; i < iters; i++){
            double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);
            
            Anstar.project(y, y);
            
            cut.nearestPoint(y);
            lattice.nearestPoint(y);
            
            
            System.out.println(VectorFunctions.print(cut.getIndex()));
            System.out.println(VectorFunctions.print(lattice.getIndex()));
            System.out.println(VectorFunctions.print(cut.getLatticePoint()));
            System.out.println(VectorFunctions.print(lattice.getLatticePoint()));
            System.out.println();
             
             
            
            double dist = VectorFunctions.distance_between2(lattice.getLatticePoint(), cut.getLatticePoint());
            assertTrue(dist < 0.0001);
            
        }
    }
    
//    /**
//     * Test of constructor correctly detects and computes obtuse bases.
//     */
//    @Test
//    public void testNearestPointWithAn() {
//        System.out.println("test nearest point with An");
//        
//        int n = 12;
//        LatticeAndNearestPointAlgorithm lattice = new AnFastSelect(n);      
//        NaiveBruteForce cut =new NaiveBruteForce(lattice);
//        
//        Matrix Bs = lattice.getGeneratorMatrix();
//        Matrix Bsinv = (Bs.transpose().times(Bs)).inverse().times(Bs.transpose());
//        Matrix B = cut.getSuperBase();
//        System.out.println(VectorFunctions.print(B));
//        
//        double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);
//
//        Anstar.project(y, y);
//
//        cut.nearestPoint(y);
//        lattice.nearestPoint(y);
//        
//        double[] zr = VectorFunctions.matrixMultVector(Bsinv, lattice.getLatticePoint());
//        double[] z = VectorFunctions.matrixMultVector(Bsinv, y);
//        double[] zdiff = new double[z.length];
//        for(int i = 0; i < z.length; i++) zdiff[i] = Math.round(zr[i] - Math.floor(z[i]));
//        System.out.println("zdiff = " + VectorFunctions.print(zdiff));
//        
//        System.out.println(VectorFunctions.print(cut.getIndex()));
//        System.out.println(VectorFunctions.print(lattice.getIndex()));
//        System.out.println(VectorFunctions.print(cut.getLatticePoint()));
//        System.out.println(VectorFunctions.print(lattice.getLatticePoint()));
//        System.out.println();
//
//        System.out.println(VectorFunctions.distance_between2(y,lattice.getLatticePoint()));
//        System.out.println(VectorFunctions.distance_between2(y,cut.getLatticePoint()));
//        
//        double dist = VectorFunctions.distance_between2(lattice.getLatticePoint(), cut.getLatticePoint());
//        assertTrue(dist < 0.0001);
//            
//    }


}
