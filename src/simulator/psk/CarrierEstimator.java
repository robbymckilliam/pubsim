/*
 * CarrierEstimator.java
 *
 * Created on 6 December 2007, 11:25
 */

package simulator.psk;

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
    void setSize(int n);
    
    /** Input is the phase of the reqcieved QPSK signal */
    void estimateCarrier(double[] y);
    
    /** Return the estimated phase */
    double getPhase();
    
    /** Return the estiamted freqenucy */
    double getFreqency();
    
    /** Set the maximum allowed frequency */
    void setFmin(double fmin);
    
    /** Set the minimum allowed frequency */
    void setFmax(double fmax);
    
}
