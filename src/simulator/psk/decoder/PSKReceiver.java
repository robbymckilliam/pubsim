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
    public void setM(int M);
    
    /** 
    * Set number of PSK signals to use for
    * estimating the channel
    */
    public void setT(int T);
    
    /**Decode the PSK signal*/
    public double[] decode(Complex[] y);
    
}
