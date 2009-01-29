

/*
 * GaussianNoise.java
 *
 * Created on 13 April 2007, 15:40
 */

package distributions;

import simulator.*;
import java.util.Random;

/**
 * Creates single gaussian variables
 * @author Robby McKilliam
 */
public class GaussianNoise extends NoiseGeneratorFunctions implements NoiseGenerator {
    
    /** Creates Gaussian noise with mean = 0.0 and variance = 1.0 */
    public GaussianNoise() {
        super();
        mean = 0.0;
        variance = 1.0;
        stdDeviation = 1.0;
    }
    
    /** Creates a new instance of GaussianNoise with specific variance and mean */
    public GaussianNoise(double mean, double variance){
        this.mean = mean;
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
        random = new Random();
    }
    
    @Override
    public void setVariance(double variance){
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
    }

    @Override
    public void setSeed(long seed){
        random.setSeed(seed);
    }
    
    /** Returns an instance of gaussian noise */
    public double getNoise(){
        return stdDeviation * random.nextGaussian() + mean;
    }
}