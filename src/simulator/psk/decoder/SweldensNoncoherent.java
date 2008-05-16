/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import simulator.Complex;

/**
 * This is an implementation of Sweldens' noncoherent detector
 * for PSK.  This is essentially the O(nlogn) An* algorithm.
 * @author Robby McKilliam
 */
public class SweldensNoncoherent implements PSKReceiver{

    public void setM(int M) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setT(int T) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] decode(Complex[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
