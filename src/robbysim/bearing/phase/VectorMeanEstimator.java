/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.bearing.phase;

import robbysim.Complex;

/**
 *
 * @author Robby McKilliam
 */
public class VectorMeanEstimator implements PhaseEstimator{

    private int n;

    public void setSize(int n) {
        this.n = n;
    }

    public double estimatePhase(Complex[] y) {
        n = y.length;
        
        double real = 0.0, imag = 0.0;
        for(int i = 0; i < n; i++){
            real += y[i].re();
            imag += y[i].im();
        }

        return Math.atan2(imag, real)/(2*Math.PI);

    }

}
