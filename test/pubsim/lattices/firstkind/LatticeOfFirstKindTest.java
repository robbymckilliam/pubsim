package pubsim.lattices.firstkind;

import pubsim.lattices.firstkind.LatticeOfFirstKind;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.distributions.Uniform;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarLinear;
import pubsim.lattices.Lattice;

/**
 *
 * @author Robby McKilliam
 */
public class LatticeOfFirstKindTest {
    
    public LatticeOfFirstKindTest() {
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
     * Test of superbasis method, of class LatticeOfFirstKind.
     */
    @Test
    public void testSuperbasisWithAn() {
        System.out.println("superbasis with An");
        AnFastSelect anlattice = new AnFastSelect(5);
        LatticeOfFirstKind instance = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
        //System.out.println(VectorFunctions.print(instance.superbasis()));
        //System.out.println(VectorFunctions.print(instance.extendedGram()));
    }
    
     /**
     * Test of superbasis method, of class LatticeOfFirstKind.
     */
    @Test
    public void testSuperbasisWithAnStar() {
        System.out.println("superbasis with An*");
        Anstar anlattice = new AnstarLinear(5);
        LatticeOfFirstKind instance = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
        //System.out.println(VectorFunctions.print(instance.superbasis()));
        //System.out.println(VectorFunctions.print(instance.extendedGram()));
    }
    
     /**
     * Test of construct from extended Gram matrix
     */
    @Test
    public void testconstructfromextededGramAn() {
        System.out.println("construct from extended Gram matrix with An");
        AnFastSelect anlattice = new AnFastSelect(5);
        LatticeOfFirstKind extGram = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
        LatticeOfFirstKind instance = LatticeOfFirstKind.constructFromExtendedGram(extGram.extendedGram());
        assertEquals(anlattice.kissingNumber(), instance.kissingNumber());
        assertEquals(anlattice.norm(), instance.norm(), 0.00001);
        assertEquals(anlattice.volume(), instance.volume(), 0.00001);
        assertEquals(anlattice.packingDensity(), instance.packingDensity(), 0.00001);
        assertEquals(anlattice.centerDensity(), instance.centerDensity(), 0.00001);
    }
    
     /**
     * Test of construct from extended Gram matrix
     */
    @Test
    public void testconstructfromextededGramAnstar() {
        System.out.println("construct from extended Gram matrix with An*");
        Anstar anlattice = new AnstarLinear(6);
        LatticeOfFirstKind extGram = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
        LatticeOfFirstKind instance = LatticeOfFirstKind.constructFromExtendedGram(extGram.extendedGram());
        assertEquals(anlattice.kissingNumber(), instance.kissingNumber());
        assertEquals(anlattice.norm(), instance.norm(), 0.00001);
        assertEquals(anlattice.volume(), instance.volume(), 0.00001);
        assertEquals(anlattice.packingDensity(), instance.packingDensity(), 0.00001);
        assertEquals(anlattice.centerDensity(), instance.centerDensity(), 0.00001);
        assertEquals(anlattice.logCenterDensity(), instance.logCenterDensity(), 0.00001);
    }
    
     /**
     * Test of construct random first kind lattice
     */
    @Test
    public void testconstructrandom() {
        System.out.println("construct random");
        for(int n = 1; n <= 24; n++){
            LatticeOfFirstKind lattice = LatticeOfFirstKind.randomLatticeOfFirstKind(n, Uniform.constructFromMeanAndRange(-0.5,1));
            //System.out.println(VectorFunctions.print(lattice.extendedGram()));
        }
    }
 
    /**
     * Test of construct random first kind lattice
     */
    @Test
    public void testconstructnorm() {
        System.out.println("test norm");
        for(int n = 2; n <= 24; n++){
            LatticeOfFirstKind lattice = LatticeOfFirstKind.randomLatticeOfFirstKind(n, Uniform.constructFromMeanAndRange(-0.5,1));
            Lattice blat = new Lattice(lattice.getGeneratorMatrix());
            double sdnorm = blat.norm();
            double mincutnorm = lattice.norm();
            //System.out.println(n + ", " + sdnorm + ", " + mincutnorm + ", " + lattice.getGeneratorMatrix().cond());
            //System.out.println(VectorFunctions.print(lattice.extendedGram()));
            assertEquals(sdnorm,mincutnorm,0.00001);
        }
    }


}