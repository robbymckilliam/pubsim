/*
 * DummySymbolTimingEstimatorTest.java
 * JUnit based test
 *
 * Created on 13 December 2007, 14:40
 */

package simulator.psk;

import simulator.psk.DummySymbolTimingEstimator;
import junit.framework.*;

/**
 *
 * @author Robby McKilliam
 */
public class DummySymbolTimingEstimatorTest extends TestCase {
    
    public DummySymbolTimingEstimatorTest(String testName) {
        super(testName);
    }

    /**
     * Test of estimateTiming method, of class simulator.qpsk.DummySymbolTimingEstimator.
     */
    public void testEstimateTiming() {
        System.out.println("estimateTiming");
        
        double[] s = null;
        DummySymbolTimingEstimator instance = new DummySymbolTimingEstimator();
        instance.setFrequency(1/0.4);
        instance.setPhase(0.3);
        instance.setSize(5);
        
        instance.estimateTiming(s);
        assertEquals(0.1, instance.getPhase());
        
        instance.estimateTiming(s);
        assertEquals(0.3, instance.getPhase());
    }
    
}
