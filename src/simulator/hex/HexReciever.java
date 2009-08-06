/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.hex;

/**
 *
 * @author Robby McKilliam
 */
public interface HexReciever {

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


}
