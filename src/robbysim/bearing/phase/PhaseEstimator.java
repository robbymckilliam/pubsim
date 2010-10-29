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
public interface PhaseEstimator {

    void setSize(int n);

    double estimatePhase(Complex[] y);

}
