/*
 * ContinuousRandomVariable.java
 *
 * Created on 13 April 2007, 15:08
 */

package pubsim.distributions;

import pubsim.distributions.circular.CircularRandomVariable;

/**
 * Interface for the generation of noise with
 * different distributions. <p>
 * Future variations may generate noise with
 * coloured distributions by setting a covariance
 * matrix.
 * @author Robby McKilliam
 */
public interface ContinuousRandomVariable extends NoiseGenerator<Double> {

    public double getMean();
    public double getVariance();

    /** Return the probability density function evaluated at x */
    public double pdf(double x);

    /** Return the cumulative distribution function evaluated at x */
    public double cdf(double x);

    /**
     * Return the inverse cumulative distribution function.
     * This allows getNoise to work in a standard way by generating
     * uniform noise in [0,1] and applying icdf.
     * However, you don't have to implement this if you have a better
     * way of generating the noise.
     */
    public double icdf(double x);
    
    /** 
     * Return the circular random variable that results from wrapping
     * this random variable modulo 1 into [-1/2, 1/2).
     * @return 
     */
    public CircularRandomVariable getWrapped();
    
}
