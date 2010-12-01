/*
 * QAMReceiver.java
 *
 * Created on 17 September 2007, 13:45
 */

package pubsim.qam;

import java.io.Serializable;

import pubsim.Complex;

/**
 * Interface for an QAM receiver.  Should implement
 * some algorithm for decoding.
 * @author Robby McKilliam
 */
public interface QAMReceiver extends Serializable{
    
    /** Set the size of the QAM array */
    public void setQAMSize(int M);
    
    /** 
    * Set number of QAM signals to use for
    * estimating the channel
    */
    public void setT(int T);
    
    /**Decode the QAM signal*/
    public void decode(double[] rreal, double[] rimag);
    
    /** 
     * Get the real part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getReal();
    
    /** 
     * Get the imaginary part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getImag();

    /**
     * @param h complex channel coefficient
     */
    public void setChannel(Complex h);
    
}
