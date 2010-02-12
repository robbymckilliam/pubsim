/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import Jama.Matrix;
import java.util.Random;
import lattices.GeneralLattice;
import lattices.VnmStarSampledEfficient;
import lattices.Zn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class BabaiTest {

    public BabaiTest() {
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

    @Test
    public void returnsErrorWhenDimensionAreWrong() {
        System.out.println("nearestPoint");
        double[] y = {1, 2, 3, 4};
        Babai instance = new Babai();
        GeneralLattice lattice = new GeneralLattice(Matrix.random(6, 5));
        instance.setLattice(lattice);
        
        boolean caught = false;
        try{
            instance.nearestPoint(y);
        } catch(RuntimeException e){
            caught = true;
        }
        
        assertTrue(caught);
        
    }
    
    /** 
     * Babai's algorithm should work perfectly for Zn.
     * This tests that it does.
     */
    @Test
    public void returnsCorrectForZn() {
        System.out.println("returnsCorrectForZn");
        double[] y = {1.1, 2.2, 3.9, -4.1, -100.49};
        Babai babai = new Babai();
        //construc the integer lattice
        Zn lattice = new Zn(y.length);
        
        babai.setLattice(lattice);
        
        lattice.nearestPoint(y);
        double[] xtrue = lattice.getLatticePoint();
        double[] utrue = lattice.getIndex();
        
        babai.nearestPoint(y);
        double[] xtest = babai.getLatticePoint();
        double[] utest = babai.getIndex();
        
        
        System.out.println(VectorFunctions.print(xtrue));
        System.out.println(VectorFunctions.print(xtest));
        
        assertTrue(VectorFunctions.distance_between(utest, utrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(xtest, xtrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(utest, xtrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(xtest, utrue) < 0.00001);
       
    }
    
    /** 
     * Test a column matrix.
     */
    @Test
    public void testSmallDeviations() {
        System.out.println("testSmallDeviations");
    
        //run nearest point test by making small deviations (del) to lattice points.
        int iters = 10;
        Random r = new Random();
        int a = 3;
        double del = 0.0001;
        for(int t = 0; t < iters; t++){
            int n = r.nextInt(10) + 5;
            VnmStarSampledEfficient pna = new VnmStarSampledEfficient(a, n-a);
            Matrix G = pna.getGeneratorMatrix();
            
            Babai babai = new Babai();
            babai.setLattice(pna);
            
           // System.out.println("G is " + G.getRowDimension() + " by " + G.getColumnDimension());
            double[] x = new double[G.getRowDimension()];
            double[] xdel = new double[G.getRowDimension()];
            double[] u = VectorFunctions.randomIntegerVector(n-a, 1000);
            
            //System.out.println("u is length " + u.length + ", x is length"  + x.length);
            
            VectorFunctions.matrixMultVector(G, u, x);
            for(int i = 0; i < x.length; i++){
                xdel[i] = x[i] +  r.nextGaussian()*del;
            }
            
            babai.nearestPoint(xdel);
            double dist = VectorFunctions.distance_between(babai.getLatticePoint(), x);
            System.out.println(dist);
            assertEquals(true, dist<0.00001);
            
        }
    }
    

}