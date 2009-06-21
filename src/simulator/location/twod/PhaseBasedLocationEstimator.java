/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import simulator.Point2;

/**
 *
 * @author Robby McKilliam
 */
public interface PhaseBasedLocationEstimator {

    /** 
     * Estimate the location given phases d.
     * @return the estimated location.
     */
    public Point2 computeLocation(double[] d);

    /**
     * @return Get the previously computed location
     */
    public Point2 getLocation();


}
