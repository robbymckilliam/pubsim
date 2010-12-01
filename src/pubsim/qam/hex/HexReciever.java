/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import pubsim.Complex;

/**
 *
 * @author Robby McKilliam
 */
public interface HexReciever extends java.io.Serializable {

    /**Decode the Hex signal*/
    public void decode(double[] rreal, double[] rimag);

    /**
     * Get the real part of the decoded Hex signal.
     * Call decode first.
     */
    public double[] getReal(); 

    /**
     * Get the imaginary part of the decoded Hex signal.
     * Call decode first.
     */
    public double[] getImag();

    /**
     * @param h complex channel coefficient
     */
    public void setChannel(Complex h);


}
