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
public class WrappedGaussianNoise extends GaussianNoise implements CircularDistribution {
    
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

    /** tolerance for pdf error */
    protected final static double PDF_TOLERANCE = 0.00000001;
    
    @Override
    public double pdf(double x){
        double pdf = 0.0;
        int n = 1;
        double tolc = Double.POSITIVE_INFINITY;
        pdf += super.pdf(x);
        while( tolc > PDF_TOLERANCE && n < 10000){
            double v1 = super.pdf(x + 2*Math.PI*n);
            double v2 = super.pdf(x - 2*Math.PI*n);
            tolc = v1 + v2;
            pdf += tolc;
            n++;
        }
        return pdf;
    }

    public double getWrappedVariance() {
        WrappedVarianceCalculator vcalc = new WrappedVarianceCalculator(this);
        return vcalc.computeVarianceMod2pi();
    }

    /**
     * Gaussian noise but wrapped mod1
     */
    public static class Mod1 extends GaussianNoise implements CircularDistribution {
        
        /** Creates Gaussian noise with mean = 0.0 and variance = 1.0 */
        public Mod1() {
            super();
        }

        /** Creates a new instance of GaussianNoise with specific variance and mean */
        public Mod1(double mean, double variance){
            super(mean, variance);
            mean = Math.IEEEremainder(mean, 1.0);
        }
        
        /** Returns an instance of wrapped Gaussian noise */
        @Override
        public double getNoise(){
            double gauss = stdDeviation * random.gaussian() + mean;
            return Math.IEEEremainder(gauss, 1.0);
        }

        /** tolerance for pdf error */
        protected final static double PDF_TOLERANCE = 0.0000000001;

        @Override
        public double pdf(double x){
            double pdf = 0.0;
            int n = 1;
            double tolc = Double.POSITIVE_INFINITY;
            pdf += super.pdf(x);
            while( tolc > PDF_TOLERANCE && n < 10000){
                double v1 = super.pdf(x + n);
                double v2 = super.pdf(x - n);
                tolc = v1 + v2;
                pdf += tolc;
                n++;
            }
            return pdf;
        }

        @Override
        public double getWrappedVariance() {
            WrappedVarianceCalculator vcalc = new WrappedVarianceCalculator(this);
            return vcalc.computeVarianceMod1();
        }
        
    }
  
}
