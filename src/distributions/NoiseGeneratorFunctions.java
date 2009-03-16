/*
 * NoiseGeneratorFunctions.java
 *
 * Created on 23 October 2007, 15:28
 */

package distributions;

import distributions.NoiseGenerator;
import java.util.Random;

/**
 * Class that contains some standard functions for noise generators
 * @author Robby McKilliam
 */
public abstract class NoiseGeneratorFunctions 
        implements NoiseGenerator {
    
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
    
    
    public NoiseGeneratorFunctions(){
        random = new Random();
    }
    
    /** Randomise the seed for the internal Random */ 
    public void randomSeed(){ random = new Random(); }
    
    /** Set the seed for the internal Random */
    public void setSeed(long seed) { random.setSeed(seed); }
    
    
}
