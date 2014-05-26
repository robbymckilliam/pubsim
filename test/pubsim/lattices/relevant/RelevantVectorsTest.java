package pubsim.lattices.relevant;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Zn;
import pubsim.lattices.util.PointEnumerator;

/**
 *
 * @author Robby McKilliam
 */
public class RelevantVectorsTest {
    
    public RelevantVectorsTest() {
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
     * Test with the integer lattice.
     */
    @Test
    public void testWithIntegerLattice() {
        System.out.println("test with integer lattice");
        PointEnumerator rvs = new RelevantVectors(new Zn(2));
        while(rvs.hasMoreElements())
            System.out.println(VectorFunctions.print(rvs.nextElement()));
            
    }
    
    /**
     * Test with the integer lattice.
     */
    @Test
    public void testWithAn() {
        System.out.println("test with An");
        PointEnumerator rvs = new RelevantVectors(new AnFastSelect(2));
        while(rvs.hasMoreElements())
            System.out.println(VectorFunctions.print(rvs.nextElement()));
            
    }

    
}
