/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions;

import Jama.Matrix;
import java.io.Serializable;

/**
 *
 * @author harprobey
 */
public interface GenericNoiseGenerator<T> extends Serializable {

    public T getMean();
    public Matrix getVariance();

    /**
     * Returns a random variable from the noise
     * distribution.
     */
    public T getNoise();

    /** Randomise the seed for the internal Random */
    public void randomSeed();

    /** Set the seed for the internal Random */
    public void setSeed(long seed);

    /** Return the pdf evaluate at x */
    public double pdf(T x);

}
