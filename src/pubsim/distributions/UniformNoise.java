/*
 * UniformNoise.java
 *
 * Created on 7 May 2007, 12:40
 */

package pubsim.distributions;

import pubsim.distributions.circular.CircularRandomVariable;
import pubsim.distributions.circular.WrappedCircularRandomVariable;
import pubsim.distributions.circular.WrappedUniform;
import rngpack.Ranlux;

/**
 *
 * @author Robby McKilliam
 */
public class UniformNoise extends AbstractRandomVariable implements ContinuousRandomVariable {
    protected final double range;
    protected final double mean;
    protected final double stdDeviation;
    protected final double variance;

    /** Creates a new instance of GaussianNoise with specific variance and mean */
    public UniformNoise(double mean, double variance){
        this.mean = mean;
        this.variance = variance;
        this.stdDeviation = Math.sqrt(variance);
        range = 2.0 * Math.sqrt( 3.0 * variance );
    }

    /**
     * Creates uniform noise with a specific range,
     * rather than variance. Third variable is dummy.
     */
    public UniformNoise(double mean, double range, int nothing){
        this.mean = mean;
        this.variance = Math.pow(range/2.0 , 2)/3.0;
        this.stdDeviation = Math.sqrt(variance);
        this.range = range;
    }

    @Override
    public double getMean(){ return mean; }

    @Override
    public double getVariance(){ return variance; }

    public double getRange() { return range; }
    
    /** Returns a uniformly distributed value */
    @Override
    public Double getNoise(){
        return mean + range * (random.raw() - 0.5);
    }

    @Override
    public double pdf(double x){
        double h = 1.0/range;
        double min = mean - 0.5*range;
        double max = mean + 0.5*range;
        if( x < min || x > max ) return 0.0;
        return h;
    }
    
    @Override
    public double cdf(double x){
        double h = 1.0/range;
        double min = mean - 0.5*range;
        double max = mean + 0.5*range;
        if(x < min) return 0.0;
        if(x > max) return 1.0;
        else return h*(x - min);
    }
    
    @Override
    public CircularRandomVariable getWrapped() {
        return new WrappedUniform(mean, variance);
    }
    
}
