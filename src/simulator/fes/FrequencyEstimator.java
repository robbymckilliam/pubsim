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
    
    /** Run the estimator on recieved real and imaginary signal.
        The estimator returns a frequency value in the range
        [-0.5, 0.5].  To obtain the freqency in Hz multiply by
        the sample rate of the signal.*/
    public double estimateFreq(double[] real, double[] imag);
    
}
