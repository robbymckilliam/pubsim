/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codec;

import org.junit.*;
import pubsim.VectorFunctions;
//import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class TomlinsonHarashimaShapedTest {
    
    public TomlinsonHarashimaShapedTest() {
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
     * Test of encode method, of class TomlinsonHarashimaShaped.
     */
    @Test
    public void testEncode() {
        System.out.println("encode");
        int[] u = {0,1,1,1,0};
        int n = 5;
        int m = 0;
        int M = 2;
        TomlinsonHarashimaShaped instance = new TomlinsonHarashimaShaped(n,m,M);
        double[] result = instance.encode(u);
        System.out.println(VectorFunctions.print(result));
    }

}
