/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import Jama.Matrix;
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
 * @author harprobey
 */
public class DirectedBruteForceTest {
    
    public DirectedBruteForceTest() {
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

//     /**
//     * Test of constructor correctly detects and computes obtuse bases.
//     */
//    @Test
//    public void testNearestPointWithAn() {
//        System.out.println("test nearest point with An");
//        
//        int n = 12;
//        LatticeAndNearestPointAlgorithm lattice = new AnFastSelect(n);      
//        DirectedBruteForce cut =new DirectedBruteForce(lattice);
//        
//        int iters  = 100;
//        for(int i = 0; i < iters; i++){
//            double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);
//            
//            Anstar.project(y, y);
//            
//            cut.nearestPoint(y);
//            lattice.nearestPoint(y);
//            
//            
//            System.out.println(VectorFunctions.print(cut.getIndex()));
//            System.out.println(VectorFunctions.print(lattice.getIndex()));
//            System.out.println(VectorFunctions.print(cut.getLatticePoint()));
//            System.out.println(VectorFunctions.print(lattice.getLatticePoint()));
//            System.out.println();
//             
//             
//            
//            double dist = VectorFunctions.distance_between2(lattice.getLatticePoint(), cut.getLatticePoint());
//            assertTrue(dist < 0.0001);
//            
//        }
//    }
//    
//    /**
//     * Test of constructor correctly detects and computes obtuse bases.
//     */
//    @Test
//    public void testNearestPointWithAnStar() {
//        System.out.println("test nearest point with An*");
//        
//        int n = 12;
//        LatticeAndNearestPointAlgorithm lattice = new AnstarBucketVaughan(n);      
//        DirectedBruteForce cut =new DirectedBruteForce(lattice);
//        
//        int iters  = 100;
//        for(int i = 0; i < iters; i++){
//            double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);
//            
//            Anstar.project(y, y);
//            
//            cut.nearestPoint(y);
//            lattice.nearestPoint(y);
//            
//            
//            System.out.println(VectorFunctions.print(cut.getIndex()));
//            System.out.println(VectorFunctions.print(lattice.getIndex()));
//            System.out.println(VectorFunctions.print(cut.getLatticePoint()));
//            System.out.println(VectorFunctions.print(lattice.getLatticePoint()));
//            System.out.println();
//             
//             
//            
//            double dist = VectorFunctions.distance_between2(lattice.getLatticePoint(), cut.getLatticePoint());
//            assertTrue(dist < 0.0001);
//            
//        }
//    }
    
    /**
     * Test of constructor correctly detects and computes obtuse bases.
     */
    @Test
    public void testNearestPointWithAn() {
        System.out.println("test nearest point with An");
        
        int n = 12;
        LatticeAndNearestPointAlgorithm lattice = new AnFastSelect(n);      
        DirectedBruteForce cut =new DirectedBruteForce(lattice);
        
        Matrix Bs = lattice.getGeneratorMatrix();
        Matrix Bsinv = (Bs.transpose().times(Bs)).inverse().times(Bs.transpose());
        Matrix B = cut.getSuperBase();
        System.out.println(VectorFunctions.print(B));
        
        double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);

        Anstar.project(y, y);

        cut.nearestPoint(y);
        lattice.nearestPoint(y);
        
        double[] zr = VectorFunctions.matrixMultVector(Bsinv, lattice.getLatticePoint());
        double[] z = VectorFunctions.matrixMultVector(Bsinv, y);
        Anstar.project(z, z);
        Anstar.project(zr, zr);
        double[] zfrac = new double[z.length];
        for(int i = 0; i < z.length; i++) zfrac[i] = pubsim.Util.fracpart(z[i]);
        System.out.println("z     = " + VectorFunctions.print(z));
        System.out.println("zfrac = " + VectorFunctions.print(zfrac));
        double[] zdiff = new double[z.length];
        for(int i = 0; i < z.length; i++) zdiff[i] = z[i] - zr[i];
        System.out.println("zdiff = " + VectorFunctions.print(zdiff));
        
        System.out.println(VectorFunctions.print(cut.getIndex()));
        System.out.println(VectorFunctions.print(lattice.getIndex()));
        System.out.println(VectorFunctions.print(cut.getLatticePoint()));
        System.out.println(VectorFunctions.print(lattice.getLatticePoint()));
        System.out.println();

        System.out.println(VectorFunctions.distance_between2(y,lattice.getLatticePoint()));
        System.out.println(VectorFunctions.distance_between2(y,cut.getLatticePoint()));
        
        double dist = VectorFunctions.distance_between2(lattice.getLatticePoint(), cut.getLatticePoint());
        assertTrue(dist < 0.0001);
            
    }

}
