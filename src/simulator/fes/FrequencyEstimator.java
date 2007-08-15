/*
 * FrequencyEstimator.java
 *
 * Created on 12 August 2007, 12:08
 */

package simulator.fes;

/**
 * Interface that describes the functions of a
 * freqency estimator
 * @author Robby McKilliam
 */
public interface FrequencyEstimator {
    
    /** Set the number of samples */
    public void setSize(int n);
    
    /** Run the estimator on recieved real and imaginary signal */
    public double estimateFreq(double[] real, double[] imag);
    
}
