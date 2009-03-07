/*
 * NoiseGenerator.java
 *
 * Created on 13 April 2007, 15:08
 */

package distributions;

/**
 * Interface for the generation of noise with
 * different distributions. <p>
 * Future variations may generate noise with
 * coloured distributions by setting a covariance
 * matrix.
 * @author Robby McKilliam
 */
public interface NoiseGenerator {
    public void setMean(double mean);
    public void setVariance(double variance);
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
    
}
