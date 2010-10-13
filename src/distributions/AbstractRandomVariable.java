/*
 * AbstractRandomVariable.java
 *
 * Created on 23 October 2007, 15:28
 */

package distributions;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import rngpack.RandomElement;
import rngpack.RandomSeedable;
import rngpack.Ranlux;

/**
 * Class that contains some standard functions for noise generators
 * @author Robby McKilliam
 */
public abstract class AbstractRandomVariable
        implements RandomVariable {
    
    protected double mean;
    protected double stdDeviation;
    protected double variance;
    protected RandomElement random;

    public double getMean(){ return mean; }

    public double getVariance(){ return variance; }
   
    public void setMean(double mean){ this.mean = mean; }
    
    public void setVariance(double variance){
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
    }

    /**
     * Take standard inverse cumulative density function approach
     * by default.
     */
    public double getNoise(){
        return icdf(random.raw());
    };

    /**
     * integrate the pdf by default
     */
    public double cdf(double x){
        double startint = mean - 100*stdDeviation;
        final int INTEGRAL_STEPS = 1000;
        double cdfval = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    return pdf(x);
                }
            }, startint, x)).trapezium(INTEGRAL_STEPS);
        return cdfval;
    }
    
    public AbstractRandomVariable(){
        random = new Ranlux();
        randomSeed();
    }
    
    /** Randomise the seed for the internal Random */ 
    public void randomSeed(){ random = new Ranlux(RandomSeedable.ClockSeed()); }

    
    /** Set the seed for the internal Random */
    public void setSeed(long seed) { random = new Ranlux(seed); }

    
}
