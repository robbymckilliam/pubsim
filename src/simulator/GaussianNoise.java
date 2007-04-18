

/*
 * GaussianNoise.java
 *
 * Created on 13 April 2007, 15:40
 */

package simulator;

import java.util.Random;

/**
 * Creates single gaussian variables
 * @author Robby
 */
public class GaussianNoise implements NoiseGenerator {
    
    private double mean;
    private double variance;
    private double stdDeviation;
    private Random random;
    
    /** Creates Gaussian noise with mean = 0.0 and variance = 1.0 */
    public GaussianNoise() {
        mean = 0.0;
        variance = 1.0;
        stdDeviation = 1.0;
        random = new Random();
    }
    
    /** Creates a new instance of GaussianNoise with specific variance and mean */
    public GaussianNoise(double mean, double variance){
        this.mean = mean;
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
        random = new Random();
    }
    
    public void setMean(double mean){ this.mean = mean; }
    public void setVariance(double variance){
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
    }
    
    /** Returns an instance of gaussian noise */
    public double getNoise(){
        return stdDeviation * random.nextGaussian() + mean;
    }
}
