/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarBucket;
import pubsim.lattices.Anstar.AnstarBucketVaughan;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import pubsim.lattices.Vn2Star.Vn2Star;
import pubsim.lattices.Vn2Star.Vn2StarGlued;
import pubsim.lattices.leech.Leech;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class MinCutFirstTypeTest {
    
    public MinCutFirstTypeTest() {
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
    public void testDetectObtuseBasis() {
        System.out.println("Detect Obtuse Basis");
        
        new MinCutFirstType(new AnstarBucketVaughan(10));
        
        new MinCutFirstType(new AnFastSelect(10));
        
        boolean exc = false;
        try{ 
            new MinCutFirstType(new Vn2StarGlued(20));
        }catch(RuntimeException e){
          exc = true;  
        }
        assertTrue(exc);
        
        exc = false;
        try{ 
            new MinCutFirstType(new Leech());
        }catch(RuntimeException e){
          exc = true;  
        }
        assertTrue(exc);
        
    }
    
     /**
     * Test of constructor correctly detects and computes obtuse bases.
     */
    @Test
    public void testConstructBasicGraph() {
        System.out.println("construct basic graph");
        
        MinCutFirstType cut =new MinCutFirstType(new AnstarBucketVaughan(3));
        
        System.out.println(cut.constructBasicGraph());
        
        cut =new MinCutFirstType(new AnFastSelect(3));
        
        System.out.println(cut.constructBasicGraph());
        
    }
    
         /**
     * Test of constructor correctly detects and computes obtuse bases.
     */
    @Test
    public void testNearestPointWithAn() {
        System.out.println("test nearest point with An");
        
        int n = 3;
        LatticeAndNearestPointAlgorithm lattice = new AnFastSelect(n);      
        MinCutFirstType cut =new MinCutFirstType(lattice);
        
        int iters  = 20;
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
//    @Test
//    public void testNearestPointWithAnStar() {
//        System.out.println("test nearest point with An*");
//        
//        int n = 3;
//        LatticeAndNearestPointAlgorithm lattice = new AnstarBucketVaughan(n);      
//        MinCutFirstType cut =new MinCutFirstType(lattice);
//        
//        int iters  = 20;
//        for(int i = 0; i < iters; i++){
//            double[] y = VectorFunctions.randomGaussian(n+1, 0, 100);
    
//            Anstar.project(y, y);
            
    
//            cut.nearestPoint(y);
//            lattice.nearestPoint(y);
//            
//            /*
//            System.out.println(VectorFunctions.print(cut.getIndex()));
//            System.out.println(VectorFunctions.print(lattice.getIndex()));
//            System.out.println(VectorFunctions.print(cut.getLatticePoint()));
//            System.out.println(VectorFunctions.print(lattice.getLatticePoint()));
//            System.out.println();
//             */
//            
//            double dist = VectorFunctions.distance_between2(lattice.getLatticePoint(), cut.getLatticePoint());
//            assertTrue(dist < 0.0001);
//            
//        }
//    }
    
}
