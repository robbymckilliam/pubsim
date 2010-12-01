/*
 * RandomVariable.java
 *
 * Created on 13 April 2007, 15:08
 */

package pubsim.distributions;

/**
 * Interface for the generation of noise with
 * different distributions. <p>
 * Future variations may generate noise with
 * coloured distributions by setting a covariance
 * matrix.
 * @author Robby McKilliam
 */
public interface RandomVariable {

    public double getMean();
    public double getVariance();
    
    /**
     * Returns a random variable from the noise
     * distribution.
     */
    public double getNoise();
    
    /** Randomise the seed for the internal Random */ 
    public void randomSeed();
    
    /** Set the seed for the internal Random */
    public void setSeed(long seed);

    /** Return the probability density function evaluated at x */
    public double pdf(double x);

    /** Return the cumulitive distribution function evaluated at x */
    public double cdf(double x);

    /**
     * Return the inverse cumulitive distribution function.
     * This allows get noise to work in a standard way by generating
     * uniform noise in [0,1] and applying icdf.
     * However, you don't have to implement this if you have a better
     * way of generating the noise.
     */
    public double icdf(double x);
    
}
