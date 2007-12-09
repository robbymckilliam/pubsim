/*
 * CarrierEstimator.java
 *
 * Created on 6 December 2007, 11:25
 */

package simulator.qpsk;

/**
 * Interface for a carrier estimator.  Assumes that the signal
 * is at baseband and has been modulated by the assumed carrier
 * frequency into real and imaginary parts.  The frequency
 * estimated is then the 'skew' from the assumed transmitted
 * frequency.
 * @author Robby McKilliam
 */
public interface CarrierEstimator {
    
    /** Set the number of samples */
    public void setSize(int n);
    
    double estimateCarrierFrequency(double[] real, double[] imag, 
            double fmin, double fmax);
    
}
