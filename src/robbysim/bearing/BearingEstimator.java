/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.bearing;

/**
 * Interface for a Bearing estimator
 * @author Robby McKilliam
 */
public interface BearingEstimator {

    void setSize(int n);
    
    double estimateBearing(double[] y);
    
}
