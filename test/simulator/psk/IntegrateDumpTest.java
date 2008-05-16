/*
 * IntegrateDumpTest.java
 * JUnit based test
 *
 * Created on 13 December 2007, 16:23
 */

package simulator.psk;

import simulator.psk.IntegrateDump;
import junit.framework.*;
import java.util.LinkedList;

/**
 *
 * @author Robby
 */
public class IntegrateDumpTest extends TestCase {
    
    public IntegrateDumpTest(String testName) {
        super(testName);
    }

    /**
     * Test of getSymbols method, of class simulator.qpsk.IntegrateDump.
     */
    public void testCalculateSymbols() {
        System.out.println("getSymbols");
        
        double[] real = null;
        double[] imag = null;
        IntegrateDump instance = new IntegrateDump();
        
        instance.calculateSymbols(real, imag);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
