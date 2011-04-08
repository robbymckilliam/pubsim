/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.bearing;

import pubsim.distributions.circular.CircularRandomVariable;

/**
 * Interface for a Bearing estimator
 * @author Robby McKilliam
 */
public interface BearingEstimator {
    
    double estimateBearing(double[] y);
    
    /**
     * Compute and estimate and the confidence interval.
     * Returns an double array of length 2. The first element is
     * the estimate and the second element is the confidence interval
     */
    double[] confidenceInterval(double[] y);

    /**
     * Return the asymptotic variance of this estimator for the random
     * variable noise and N observations.
     */
    double asymptoticVariance(CircularRandomVariable noise, int N);
    
}