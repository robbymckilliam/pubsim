/*
 * SymbolTimingEstimator.java
 *
 * Created on 13 December 2007, 14:15
 */

package simulator.qpsk;

/**
 * Interface for a Symbol Timing estimator from the corrected
 * phase of the symbols.
 * @author Robby McKilliam
 */
public interface SymbolTimingEstimator {
    void estimateTiming(double[] s);

    double getFrequency();

    double getPhase();
    
    void setSize(int n);
    
}
