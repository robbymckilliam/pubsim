/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.location.twod;

import pubsim.Point2;

/**
 * Teunissen's sphere decoder estimator.  This
 * performs a linearisation and then a sphere
 * decoder to find the best unwrapping.  Newton's
 * method is the used to climb to the best result.
 * @author Robby McKilliam
 */
public class SphereDecoderLocationEstimator implements PhaseBasedLocationEstimator{

    public Point2 estimateLocation(double[] d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point2 getLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
