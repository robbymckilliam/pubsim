/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.optimisation;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class AutoIntegralFunctionTest {
    
    public AutoIntegralFunctionTest() {
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
     * Test of integral method, of class AutoIntegralFunction.
     */
    @Test
    public void testIntegral() {
        System.out.println("integral");
        double[] min = null;
        double[] max = null;
        
        //check that a constant function integrates correctly.
        AutoIntegralFunction instance = new AutoIntegralFunction() {
            public double value(Matrix x) {
                return 1.0;
            }
        };
        double[] min1 = {0}; double[] max1 = {1};
        double intval = instance.integral(min1, max1);
        assertEquals(1.0,intval, 0.0001);
        System.out.println(intval);
        
        double[] min2 = {0,0}; double[] max2 = {1,1};
        intval = instance.integral(min2, max2);
        assertEquals(1.0,intval, 0.0001);
        System.out.println(intval);
        
        double[] min3 = {0,0,0}; double[] max3 = {1,1,1};
        intval = instance.integral(min3, max3);
        assertEquals(1.0,intval, 0.0001);
        System.out.println(intval);
        
    }


}
