/*
 * NoiseGeneratorFunctions.java
 *
 * Created on 23 October 2007, 15:28
 */

package simulator;

import java.util.Random;

/**
 * Class that contains some standard functions for noise generators
 * @author robertm
 */
public abstract class NoiseGeneratorFunctions implements NoiseGenerator {
    
    protected double mean;
    protected double stdDeviation;
    protected double variance;
    protected Random random;

    public double getMean(){ return mean; }

    public double getVariance(){ return variance; }
   
    public void setMean(double mean){ this.mean = mean; }
    
    public void setVariance(double variance){
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
    }
    
    public abstract double getNoise();
    
    /** {@inheritDoc} */
    public void randomSeed(){ random = new Random(); }
    
}
