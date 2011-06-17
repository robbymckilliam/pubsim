/*
 * AbstractRandomVariable.java
 *
 * Created on 23 October 2007, 15:28
 */

package pubsim.distributions;

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
    
    protected RandomElement random = new Ranlux(RandomSeedable.ClockSeed());

    /**
     * Take standard inverse cumulative density function approach
     * by default.
     */
    public double getNoise(){
        return icdf(random.raw());
    };

    /**
     * integrate the pdf by default.  This is highly non-optimised.
     */
    public double cdf(double x){
        double startint = getMean() - 20*Math.sqrt(getVariance());
        final int INTEGRAL_STEPS = 1000;
        double cdfval = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    return pdf(x);
                }
            }, startint, x)).gaussQuad(INTEGRAL_STEPS);
        return cdfval;
    }

    /**
     * Default is a binary search of the cdf to find the inverse cdf.
     * This might fail for really weird looking cdfs and is highly non
     * optimised.
     */
    public double icdf(double x){
        double TOL = 1e-8;
        double mean = getMean(); 
        double stdDeviation = Math.sqrt(getVariance());
        double high = mean + 10*stdDeviation + 0.5;
        double low = mean - 10*stdDeviation - 0.5;
        double cdfhigh = cdf(high);
        double cdflow = cdf(low);
        while(Math.abs(high - low) > TOL){
         
            double half = (high + low)/2.0;
            double cdfhalf = cdf(half);

            //System.out.println("half = " + half + ", cdfhalf = " + cdfhalf);

            if(Math.abs(cdfhalf - x) < TOL ) return half;
            else if(cdfhalf <= x){
                low = half;
                cdflow = cdfhalf;
            }
            else{
               high = half;
               cdfhigh = cdfhalf;
            }
            
        }
        return (high + low)/2.0;
    }
    
    /** Randomise the seed for the internal Random */ 
    public void randomSeed(){ random = new Ranlux(RandomSeedable.ClockSeed()); }

    
    /** Set the seed for the internal Random */
    public void setSeed(long seed) { random = new Ranlux(seed); }

    
}
