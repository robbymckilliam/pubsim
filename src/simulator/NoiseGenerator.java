/*
 * NoiseGenerator.java
 *
 * Created on 13 April 2007, 15:08
 */

package simulator;

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
    public void setVariance(double mean);
    
    /**
     * Returns a random variable from the noise
     * distribution.
     */
    public double getNoise();
}
