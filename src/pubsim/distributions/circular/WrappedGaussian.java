/*
 * WrappedGaussian.java
 *
 * Created on 1 November 2007, 12:24
 */
package pubsim.distributions.circular;

import pubsim.distributions.Gaussian;

/**
 * Gaussian noise wrapped into the interval [-0.5, 0.5]
 *
 * @author Robby McKilliam
 */
public class WrappedGaussian extends WrappedCircularRandomVariable {

    private final double thismean, thisvar;

    /** 
     * Construct a wrapped Gaussian (or wrapped normal) circular random variable.
     * @param mean      mean of the underlying Gaussian random variable
     * @param var       variance of the underlying Gaussian random variable
     * 
     * You can obtain the intrinsic mean and intrinsic variance using the intrinsicMean() and
     * intrinsicVariance() member function.
     */
    public WrappedGaussian(double mean, double var) {
        super(new Gaussian(mean, var));
        thismean = mean;
        thisvar = var;
    }

    @Override
    public Double intrinsicMean() {
        return thismean - Math.round(thismean);
    }

    @Override
    public Double intrinsicVariance() {
        
        //if the variance is really small, wrapping does change the variance much
        //this is just a guess threshold value
        if (thisvar < 1e-7) return thisvar;

        double csum = 0.0;
        double sgn = -1.0;
        int k = 1;
        double psum = Double.POSITIVE_INFINITY;
        double TOL = 1e-15;
        while (psum > TOL) {
            psum = Math.exp(-thisvar * k * k * Math.PI * Math.PI * 2);
            csum += sgn / (k * k) * psum;
            sgn *= -1;
            k++;
        }
        return 1.0 / 12.0 + csum / (Math.PI * Math.PI);
        
    }

    @Override
    public Double intrinsicVariance(double truemean) {
        return intrinsicVariance();
    }
  
}
