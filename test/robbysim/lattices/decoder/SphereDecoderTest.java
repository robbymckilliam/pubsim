/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.decoder;

import robbysim.lattices.decoder.Babai;
import robbysim.lattices.decoder.SphereDecoder;
import Jama.Matrix;
import robbysim.lattices.Anstar.Anstar;
import robbysim.lattices.Anstar.AnstarBucketVaughan;
import robbysim.lattices.GeneralLattice;
import robbysim.lattices.Vn2Star.Vn2StarGlued;
import robbysim.lattices.Zn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import robbysim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class SphereDecoderTest {

    public SphereDecoderTest() {
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
        System.out.println("returnsErrorWhenDimensionAreWrong");
        double[] y = {1, 2, 3, 4};
        SphereDecoder decoder = new SphereDecoder();
        GeneralLattice lattice = new GeneralLattice(Matrix.random(6, 5));
        decoder.setLattice(lattice);
        
        boolean caught = false;
        try{
            decoder.nearestPoint(y);
        } catch(RuntimeException e){
            caught = true;
        }
        
        assertTrue(caught);
        
    }
    
    /** 
     * Should return the correct nearest point for Zn.
     */
    @Test
    public void returnsCorrectForZn() {
        System.out.println("returnsCorrectForZn");
        double[] y = {1.49, 1.49, 1.49, 1.49, 1.49};
        SphereDecoder decoder = new SphereDecoder();
        //construc the integer lattice
        Zn lattice = new Zn(y.length);
        
        decoder.setLattice(lattice);
        
        lattice.nearestPoint(y);
        double[] xtrue = lattice.getLatticePoint();
        double[] utrue = lattice.getIndex();
        
        decoder.nearestPoint(y);
        double[] xtest = decoder.getLatticePoint();
        double[] utest = decoder.getIndex();
        
        
        System.out.println(VectorFunctions.print(xtrue));
        System.out.println(VectorFunctions.print(xtest));
        
        assertTrue(VectorFunctions.distance_between(utest, utrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(xtest, xtrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(utest, xtrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(xtest, utrue) < 0.00001);
       
    }
    
    /** 
     * The sphere decoded point should be better than 
     * or equal to the Babai point.
     */
    @Test
    public void returnsBabaiPointOrBetter() {
        System.out.println("returnsBabaiPointOrBetter");
        
        SphereDecoder decoder = new SphereDecoder();
        Babai babai = new Babai();
       
        int iters = 10;
        int n = 5;
        int m = 6;
        for(int t = 0; t < iters; t++){
            
            GeneralLattice lattice = new GeneralLattice(Matrix.random(m, n));
            
            decoder.setLattice(lattice);
            babai.setLattice(lattice);
            
            double[] y = VectorFunctions.randomGaussian(m, 0.0, 100.0);
            decoder.nearestPoint(y);
            babai.nearestPoint(y);
            
            double decdist = VectorFunctions.distance_between2(y, decoder.getLatticePoint());
            double babdist = VectorFunctions.distance_between2(y, babai.getLatticePoint());
            
            //System.out.println(decdist);
            //System.out.println(babdist);
            
            assertTrue(decdist <= babdist);
            
        }
    }
    
    /** 
     * The sphere decoded point should be better than 
     * or equal to the Babai point.
     */
    @Test
    public void correctForAnStar() {
        System.out.println("correctForAnStar");
        
        SphereDecoder decoder = new SphereDecoder();
       
        int iters = 10;
        int n = 7;
        
        Anstar anstar = new AnstarBucketVaughan();
        anstar.setDimension(n);
        
        decoder.setLattice(anstar);
        
        for(int t = 0; t < iters; t++){
            
            double[] y = VectorFunctions.randomGaussian(n+1, 0.0, 100.0);
            decoder.nearestPoint(y);
            anstar.nearestPoint(y);
            
            double decdist = VectorFunctions.distance_between2(anstar.getLatticePoint(), decoder.getLatticePoint());
            
            assertTrue(decdist <= 0.000001);
            
        }
    }
    
    /** 
     * The sphere decoded point should be better than 
     * or equal to the Babai point.
     */
    @Test
    public void correctForPhin2Star() {
        System.out.println("correctForPhin2Star");
        
        SphereDecoder decoder = new SphereDecoder();
       
        int iters = 10;
        int n = 6;
        
        Vn2StarGlued lattice = new Vn2StarGlued();
        lattice.setDimension(n);
        
        decoder.setLattice(lattice);
        
        for(int t = 0; t < iters; t++){
            
            double[] y = VectorFunctions.randomGaussian(n+2, 0.0, 100.0);
            decoder.nearestPoint(y);
            lattice.nearestPoint(y);
            
            double decdist = VectorFunctions.distance_between2(lattice.getLatticePoint(), decoder.getLatticePoint());
            
            assertTrue(decdist <= 0.000001);
            
            System.out.println(decdist);

            
        }
    }
       

}