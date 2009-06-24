/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import simulator.Point2;

/**
 * Teunissen's sphere decoder estimator.  This
 * performs a linearisation and then a sphere
 * decoder to find the best unwrapping.  Newton's
 * method is the used to climb to the best result.
 * @author Robby McKilliam
 */
public class SphereDecoderLocationEstimator implements PhaseBasedLocationEstimator{

    public Point2 computeLocation(double[] d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point2 getLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
