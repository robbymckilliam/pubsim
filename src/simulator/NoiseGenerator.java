/*
 * NoiseGenerator.java
 *
 * Created on 13 April 2007, 15:08
 */

package simulator;

/**
 *
 * @author Robby McKilliam
 */
public interface NoiseGenerator {
    public void setMean(double mean);
    public void setVariance(double mean);
    public double getNoise();
}
