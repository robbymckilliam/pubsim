/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.bearing;

/**
 * Interface for a Bearing estimator
 * @author Robby McKilliam
 */
public interface BearingEstimator {
    
    double estimateBearing(double[] y);
    
    /**
     * Compute the confidence interval for the previous data.
     * You have to call estimateBearing first.
     */
    double confidenceInterval();


    
}
