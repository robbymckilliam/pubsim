/*
 * WrappedGaussian.java
 *
 * Created on 1 November 2007, 12:24
 */

package pubsim.distributions.circular;

import pubsim.distributions.GaussianNoise;

/**
 * Gaussian noise wrapped into the interval [-0.5, 0.5]
 * @author Robby McKilliam
 */
public class WrappedGaussian extends WrappedCircularRandomVariable{

    private final double thismean, thisvar;

    public WrappedGaussian(double mean, double var){
        super(new GaussianNoise(mean, var));
        thismean = mean;
        thisvar = var;
    }

    @Override
    public double unwrappedVariance(){
        double csum = 0.0;
        double sgn = -1.0;
        int k = 1;
        double psum = Double.POSITIVE_INFINITY;
        double TOL = 1e-15;
        while(psum > TOL){
            psum = Math.exp(-thisvar*k*k*Math.PI*Math.PI*2);
            csum += sgn/(k*k) * psum;
            sgn *= -1;
            k++;
        }
        return 1.0/12.0 + csum/(Math.PI*Math.PI);
    }
    
    @Override
    public double unwrappedVariance(double truemean){
        return unwrappedVariance();
    }
  
}
