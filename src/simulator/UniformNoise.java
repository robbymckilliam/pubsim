/*
 * UniformNoise.java
 *
 * Created on 7 May 2007, 12:40
 */

package simulator;

import java.util.Random;

/**
 *
 * @author Robby McKilliam
 */
public class UniformNoise extends NoiseGeneratorFunctions implements NoiseGenerator {
    protected double range;
    
    /** Creates a new instance of UniformNoise with specific variance and mean */
    public UniformNoise(double mean , double variance) {
        this.mean = mean;
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
        range = 2.0 * Math.sqrt( 3.0 * variance );
        random = new Random();
    }
    
    /** Creates UniformNoise with mean = 0.0 and variance = 1.0 */
    public UniformNoise() {
        mean = 0.0;
        variance = 1.0;
        stdDeviation = 1.0;
        range = Math.pow(3*variance/2.0, 1.0/3);
        random = new Random();
    }

    public void setVariance(double variance){
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
        range = Math.pow(3*variance/2.0, 1.0/3);
    }
    
    /** 
     * The noise is uniformly distributed in
     * [-range*0.5 + mean, range*0.5 + mean]
     */
    public void setRange(double range){
        this.range = range;
        variance = 2.0/3*Math.pow(range, 3);
        stdDeviation = Math.sqrt(variance);
    }
    
    public void setSeed(long seed){
        random.setSeed(seed);
    }
    
    /** Returns a uniformly distributed value */
    public double getNoise(){
        return mean + range * (random.nextDouble() - 0.5);
    }
    
}
