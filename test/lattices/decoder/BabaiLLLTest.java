/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import lattices.Anstar;
import lattices.AnstarBucket;
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
public class BabaiLLLTest {

    public BabaiLLLTest() {
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
     * Babai's algorithm should work perfectly for Zn.
     * This tests that it does.
     */
    @Test
    public void returnsCorrectForZn() {
        System.out.println("returnsCorrectForZn");
        double[] y = {1.1, 2.2, 3.9, -4.1, -100.49};
        BabaiLLL babai = new BabaiLLL();
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
     * Babai's algorithm should work perfectly for Zn.
     * This tests that it does.
     */
    @Test
    public void correctForSmallErrorAnStar() {
        System.out.println("correctForSmallErrorAnStar");
        double[] y = {1.1, 2.2, 3.9, -7.1, -1.49, 5.6, 7.8, -4.3, 100};
        BabaiLLL babai = new BabaiLLL();
        //construc the integer lattice
        AnstarBucket lattice = new AnstarBucket();
        lattice.setDimension(y.length-1);
        
        
        //System.out.println(VectorFunctions.print(lattice.getGeneratorMatrix()));
        
        babai.setLattice(lattice);
        
        Anstar.project(y, y);
        
        lattice.nearestPoint(y);
        double[] xtrue = lattice.getLatticePoint();
        double[] utrue = lattice.getIndex();
        
        babai.nearestPoint(y);
        double[] xtest = babai.getLatticePoint();
        double[] utest = babai.getIndex();
        
        System.out.println(VectorFunctions.print(xtest));
        System.out.println(VectorFunctions.print(xtrue));
        
        assertTrue(VectorFunctions.distance_between(xtest, xtrue) < 1);
       
    }

}