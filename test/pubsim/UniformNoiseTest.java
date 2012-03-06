/*
 * UniformNoiseTest.java
 * JUnit based test
 *
 * Created on 7 May 2007, 13:38
 */

package pubsim;

import junit.framework.TestCase;
import pubsim.distributions.UniformNoise;

/**
 *
 * @author Robby McKilliam
 */
public class UniformNoiseTest extends TestCase {
    
    public UniformNoiseTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getNoise method, of class simulator.UniformNoise.
     */
    public void testGetNoise() {
        System.out.println("getNoise");
        
        int iters = 100000;
        double mean = 2.0;
        double var = 4.0/3.0;
        UniformNoise instance = new UniformNoise(mean, var);
        
        
        double sum = 0; double sum2 = 0;
        for( int i = 0; i <= iters; i++)
        {
            double noise = instance.getNoise();
            boolean result = noise >= 0.0 && noise <= 4.0; 
            sum += noise;
            sum2 += (noise - 2)*(noise - 2);
            assertEquals(true, result);
        }
        
        //test the mean and variance
        assertTrue( Math.abs(sum/iters - mean) < 0.01 );
        assertTrue( Math.abs(sum2/iters - var) < 0.01 );
       
    }
    
}
