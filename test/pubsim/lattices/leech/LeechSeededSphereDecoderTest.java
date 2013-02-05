/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.leech;

import org.junit.*;
import static org.junit.Assert.*;
import pubsim.VectorFunctions;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;

/**
 *
 * @author harprobey
 */
public class LeechSeededSphereDecoderTest {
    
    public LeechSeededSphereDecoderTest() {
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
     * Test of nearestPoint method, of class LeechSeededSphereDecoder.
     */
    @Test
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int iters = 100;
        int n = 24;
        
        LeechSeededSphereDecoder instance = new LeechSeededSphereDecoder();
        LatticeAndNearestPointAlgorithm tester = new LatticeAndNearestPointAlgorithm(instance.getGeneratorMatrix());
        
        for(int t = 0; t < iters; t++){

            double[] y = VectorFunctions.randomGaussian(n, 0.0, 100.0);
            instance.nearestPoint(y);
            tester.nearestPoint(y);

            double decdist = VectorFunctions.distance_between2(instance.getLatticePoint(), tester.getLatticePoint());
            
            //System.out.println(decdist);

            assertTrue(decdist <= 0.000001);
        }
      
    }
}
