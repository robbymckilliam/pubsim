/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions;

/**
 *
 * @author Robby McKilliam
 */
public interface NoiseGenerator {
    
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
