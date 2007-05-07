/*
 * UniformNoiseTest.java
 * JUnit based test
 *
 * Created on 7 May 2007, 13:38
 */

package simulator;

import junit.framework.*;
import java.util.Random;

/**
 *
 * @author Robby
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
        
        UniformNoise instance = new UniformNoise(2.0, 4.0/3.0);
        
        boolean result;
        double noise;
        for( int i = 0; i <= 100; i++)
        {
            noise = instance.getNoise();
            result = noise >= 0.0 && noise <= 4.0; 
            assertEquals(true, result);
        }
       
    }
    
}
