/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import pubsim.VectorFunctions;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.lattices.Anstar.AnstarBucketVaughan;
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
        
        int n = 4;
        LatticeAndNearestPointAlgorithm lattice = new AnFastSelect(n);      
        NaiveBruteForce cut =new NaiveBruteForce(lattice);
        
        int iters  = 100;
        for(int i = 0; i < iters; i++){
            double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);
            
            //Anstar.project(y, y);
            
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
        
        int n = 10;
        LatticeAndNearestPointAlgorithm lattice = new AnstarBucketVaughan(n);      
        NaiveBruteForce cut =new NaiveBruteForce(lattice);
        
        int iters  = 100;
        for(int i = 0; i < iters; i++){
            double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);
            
            //Anstar.project(y, y);
            
            cut.nearestPoint(y);
            lattice.nearestPoint(y);
            
            
            /*System.out.println(VectorFunctions.print(cut.getIndex()));
            System.out.println(VectorFunctions.print(lattice.getIndex()));
            System.out.println(VectorFunctions.print(cut.getLatticePoint()));
            System.out.println(VectorFunctions.print(lattice.getLatticePoint()));
            System.out.println();*/
             
             
            
            double dist = VectorFunctions.distance_between2(lattice.getLatticePoint(), cut.getLatticePoint());
            assertTrue(dist < 0.0001);
            
        }
    }

}
