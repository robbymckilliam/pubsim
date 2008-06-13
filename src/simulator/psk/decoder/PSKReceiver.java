/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import simulator.Complex;

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
     * Calculate the bit errors between this the recieved
     * signal and a transmitted signal for this reciever.
     */
    int bitErrors(double[] x);
    
    /** 
     * Calculate the symbol errors between this the recieved
     * signal and a transmitted signal for this reciever.
     */
    int symbolErrors(double[] x);
    
    /** 
     * Return true is the codeword is not equal to x
     */
    boolean codewordError(double[] x);
     
    /** 
     * Return the number of bits transmitted per codeword for
     * this reciever
     */
    int bitsPerCodeword();
    
    /**
     * Set the channel that the reciever should use.
     */
    void setChannel(Complex h);
   
    
}
