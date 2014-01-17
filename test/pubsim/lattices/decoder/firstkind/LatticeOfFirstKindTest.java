package pubsim.lattices.decoder.firstkind;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.AnstarBucket;

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
        AnstarBucket anlattice = new AnstarBucket(5);
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
    }
    
     /**
     * Test of construct from extended Gram matrix
     */
    @Test
    public void testconstructfromextededGramAnstar() {
        System.out.println("construct from extended Gram matrix with An*");
        AnstarBucket anlattice = new AnstarBucket(6);
        LatticeOfFirstKind extGram = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
        LatticeOfFirstKind instance = LatticeOfFirstKind.constructFromExtendedGram(extGram.extendedGram());
        assertEquals(anlattice.kissingNumber(), instance.kissingNumber());
        assertEquals(anlattice.norm(), instance.norm(), 0.00001);
        assertEquals(anlattice.volume(), instance.volume(), 0.00001);
        assertEquals(anlattice.packingDensity(), instance.packingDensity(), 0.00001);
    }
 
}