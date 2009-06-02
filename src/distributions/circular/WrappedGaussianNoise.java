/*
 * WrappedGaussianNoise.java
 *
 * Created on 1 November 2007, 12:24
 */

package distributions.circular;

import distributions.NoiseGenerator;
import simulator.*;
import distributions.GaussianNoise;

/**
 * Gaussian noise mod 2 pi
 * @author Robby McKilliam
 */
public class WrappedGaussianNoise extends GaussianNoise implements NoiseGenerator {
    
    /** Creates Gaussian noise with mean = 0.0 and variance = 1.0 */
    public WrappedGaussianNoise() {
        super();
    }
    
    /** Creates a new instance of GaussianNoise with specific variance and mean */
    public WrappedGaussianNoise(double mean, double variance){
        super(mean, variance);
    }
    
    /** Returns an instance of wrapped Gaussian noise */
    @Override
    public double getNoise(){
        double gauss = stdDeviation * random.gaussian() + mean;
        return Math.IEEEremainder(gauss, 2*Math.PI);
    }
    
    /**
     * Gaussian noise but wrapped mod1
     */
    public static class Mod1 extends GaussianNoise implements NoiseGenerator{
        
        /** Creates Gaussian noise with mean = 0.0 and variance = 1.0 */
        public Mod1() {
            super();
        }

        /** Creates a new instance of GaussianNoise with specific variance and mean */
        public Mod1(double mean, double variance){
            super(mean, variance);
        }
        
        /** Returns an instance of wrapped Gaussian noise */
        @Override
        public double getNoise(){
            double gauss = stdDeviation * random.gaussian() + mean;
            return Math.IEEEremainder(gauss, 1.0);
        }
        
    }
  
}
