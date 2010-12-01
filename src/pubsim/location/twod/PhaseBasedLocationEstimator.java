/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.location.twod;

import pubsim.Point2;

/**
 *
 * @author Robby McKilliam
 */
public interface PhaseBasedLocationEstimator {

    /** 
     * Estimate the location given phases d.
     * @return the estimated location.
     */
    public Point2 estimateLocation(double[] d);

    /**
     * @return Get the previously computed location
     */
    public Point2 getLocation();


}
