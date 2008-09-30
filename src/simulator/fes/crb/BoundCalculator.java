/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes.crb;

/**
 * Interface for lower bounds for frequency estimation.
 * @author Robby McKilliam
 */
public interface BoundCalculator {
    
    /** Set the signal length */
    void setN(int N);
    
    /** Set the noise variance per real and imaginary part */
    void setVariance(double var);
    
    /** Set the signal amplitude */
    void setAmplitude(double amp);
    
    /** Return the lower bound */
    double getBound();
    
    
}
