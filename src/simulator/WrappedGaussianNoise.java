/*
 * WrappedGaussianNoise.java
 *
 * Created on 1 November 2007, 12:24
 */

package simulator;

/**
 * Gaussian noise mod 2 pi
 * @author Robby McKilliam
 */
public class WrappedGaussianNoise extends GaussianNoise implements NoiseGenerator {
    
    /** Returns an instance of wrapped Gaussian noise */
    public double getNoise(){
        double gauss = stdDeviation * random.nextGaussian() + mean;
        return Math.IEEEremainder(gauss, 2*Math.PI);
    }
    
    /**
     * Gaussian noise but wrapped mod1
     */
    public static class Mod1 extends GaussianNoise implements NoiseGenerator{
        
        /** Returns an instance of wrapped Gaussian noise */
        public double getNoise(){
            double gauss = stdDeviation * random.nextGaussian() + mean;
            return Math.IEEEremainder(gauss, 1.0);
        }
        
    }
  
}
