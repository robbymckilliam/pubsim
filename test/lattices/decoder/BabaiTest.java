/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import Jama.Matrix;
import lattices.Anstar;
import lattices.AnstarBucket;
import lattices.GeneralLattice;
import lattices.Lattice;
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
 * @author Robby
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
        GeneralLattice lattice = new GeneralLattice(Matrix.random(5, 6));
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
        Zn lattice = new Zn();
        lattice.setDimension(y.length);
        
        babai.setLattice(lattice);
        
        lattice.nearestPoint(y);
        double[] xtrue = lattice.getLatticePoint();
        double[] utrue = lattice.getIndex();
        
        babai.nearestPoint(y);
        double[] xtest = babai.getLatticePoint();
        double[] utest = babai.getIndex();
        
        assertTrue(VectorFunctions.distance_between(utest, utrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(xtest, xtrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(utest, xtrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(xtest, utrue) < 0.00001);
       
    }
    
    /** 
     * Test a column matrix.
     */
    @Test
    public void testOneColumn() {
        System.out.println("testOneColumn");
        double[] y = {1.1, 2.2, 3.9};
        double[] g = {2, 2, 2};
        Babai babai = new Babai();
        
        Matrix G = new Matrix(g, 3);
        
        System.out.println(VectorFunctions.print(G));
        
        GeneralLattice lattice = new GeneralLattice(G);
        
        babai.setLattice(lattice);
        
        babai.nearestPoint(y);
        double[] utest = babai.getIndex();
        
        double uvals = Math.round(2*(1.1 + 2.2 + 3.9)/(2.0*(2.0 + 2.0 + 2.0)));
        
        assertEquals(utest[0], uvals);
       
    }

}