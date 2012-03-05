/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.psk.decoder;

import pubsim.Complex;

/**
 * @author Robby McKilliam
 */
public interface PSKReceiver {
    
    /** Set the number of symbols in the PSK constellation.  Ie M-PSK */
    void setM(int M);
    
    /** 
    * Set number of PSK signals to use for
    * estimating the channel
    */
    void setT(int T);
    
    /**Decode the PSK signal*/
    double[] decode(Complex[] y);
    
    /** 
     * Calculate the bit errors between this the received
     * signal and a transmitted signal for this receiver.
     */
    int bitErrors(double[] x);
    
    /** 
     * Calculate the symbol errors between this the received
     * signal and a transmitted signal for this receiver.
     */
    int symbolErrors(double[] x);
    
    /** 
     * Return true is the codeword is not equal to x
     */
    boolean codewordError(double[] x);
     
    /** 
     * Return the number of bits transmitted per codeword for
     * this receiver
     */
    int bitsPerCodeword();
    
    /**
     * Set the channel that the receiver should use.
     */
    void setChannel(Complex h);
   
    
}
