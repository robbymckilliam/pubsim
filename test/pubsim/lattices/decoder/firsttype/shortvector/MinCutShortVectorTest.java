/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype.shortvector;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.VectorFunctions;
import pubsim.lattices.An.AnSorted;
import pubsim.lattices.Anstar.AnstarSorted;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;

/**
 *
 * @author Robby McKilliam
 */
public class MinCutShortVectorTest {
    
    public MinCutShortVectorTest() {
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
     * Test of getShortestVector method, of class MinCutShortVector.
     */
    @Test
    public void testWithAnStar() {
        System.out.println("testWithAnStar");
        int n = 50;
        LatticeInterface lattice = new AnstarSorted(n);
        MinCutShortVector instance = new MinCutShortVector(lattice);
        ShortVectorSphereDecoded tester = new ShortVectorSphereDecoded(lattice);
        
        double td = VectorFunctions.sum2(instance.getShortestVector());
        double id = VectorFunctions.sum2(tester.getShortestVector());
        
        //System.out.println(VectorFunctions.print(instance.getShortestVector()));
        //System.out.println(VectorFunctions.print(tester.getShortestVector()));
        
        assertEquals(td, id, 0.000001);
        
    }
    
        /**
     * Test of getShortestVector method, of class MinCutShortVector.
     */
    @Test
    public void testWithAn() {
        System.out.println("testWithAn");
        int n = 50;
        LatticeInterface lattice = new AnSorted(n);
        MinCutShortVector instance = new MinCutShortVector(lattice);
        ShortVectorSphereDecoded tester = new ShortVectorSphereDecoded(lattice);
        
        double td = VectorFunctions.sum2(instance.getShortestVector());
        double id = VectorFunctions.sum2(tester.getShortestVector());
        
        //System.out.println(VectorFunctions.print(instance.getShortestVector()));
        //System.out.println(VectorFunctions.print(tester.getShortestVector()));
        
        assertEquals(td, id, 0.000001);
        
    }

}
