

/*
 * GaussianNoise.java
 *
 * Created on 13 April 2007, 15:40
 */

package pubsim.distributions;

import pubsim.Complex;
import pubsim.distributions.circular.CircularRandomVariable;
import pubsim.distributions.circular.WrappedGaussian;

/**
 * Creates single Gaussian variables
 * @author Robby McKilliam
 */
public class GaussianNoise extends AbstractRealRandomVariable implements RealRandomVariable {

    protected final double mean;
    protected final double stdDeviation;
    protected final double variance;
    
    /** Creates a new instance of GaussianNoise with specific variance and mean */
    public GaussianNoise(double mean, double variance){
        this.mean = mean;
        this.variance = variance;
        this.stdDeviation = Math.sqrt(variance);
    }

    @Override
    public Double getMean(){ return mean; }

    @Override
    public Double getVariance(){ return variance; }
    
    /** Returns an instance of Gaussian noise */
    @Override
    public Double getNoise(){
        return stdDeviation * random.gaussian() + mean;
    }

    /** Return the Gaussian pdf */
    @Override
    public double pdf(Double x) {
        double s = 1.0/Math.sqrt(2*Math.PI*getVariance());
        double d = x - getMean();
        return s * Math.exp( -(d*d)/(2*getVariance()) );
    }

    /** Return the cdf of the Gaussian evaluated at x */
    @Override
    public double cdf(Double x){
        //just using the Q function from util.
        return 0.5*(1 + pubsim.Util.erf((x - mean)/stdDeviation/Math.sqrt(2)));
    }
    
    /** Default is the return the wrapped version of this random variable */
    @Override
    public CircularRandomVariable getWrapped() {
        return new WrappedGaussian(mean, variance);
    }
    
    @Override
    public Complex characteristicFunction(Double t){
        double m = Math.exp(-variance*t*t/2.0);
        return Complex.constructComplexExp(m, mean*t);
    }


}
