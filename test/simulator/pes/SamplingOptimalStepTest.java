/*
 * SamplingOptimalStepTest.java
 * JUnit based test
 *
 * Created on 10 May 2007, 11:18
 */

package simulator.pes;

import junit.framework.*;
import simulator.*;

/**
 *
 * @author Robby McKilliam
 */
public class SamplingOptimalStepTest extends TestCase {
    
    public SamplingOptimalStepTest(String testName) {
        super(testName);
    }

    /**
     * Test of calcOptimalStep method, of class simulator.SamplingOptimalStep.
     */
    public void testCalcOptimalStep() {
        System.out.println("calcOptimalStep");
        
        double[] z = {1.0 - 2.0/4, -2.0/4, 1.0 - 2.0/4, -2.0/4 };
        SamplingOptimalStep instance = new SamplingOptimalStep();
        instance.setSize(z.length);
        
        double expResult = 0.5 * VectorFunctions.magnitude(z);
        double result = instance.calcOptimalStep(z);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
