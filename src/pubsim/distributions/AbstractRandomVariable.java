/*
 * AbstractRandomVariable.java
 *
 * Created on 23 October 2007, 15:28
 */

package pubsim.distributions;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import pubsim.Complex;
import pubsim.distributions.circular.CircularRandomVariable;
import pubsim.distributions.circular.WrappedCircularRandomVariable;
import rngpack.RandomElement;
import rngpack.Ranmar;

/**
 * Class that contains some standard functions for noise generators
 * @author Robby McKilliam
 */
public abstract class AbstractRandomVariable
        implements ContinuousRandomVariable {
    
    protected RandomElement random;
    
    public AbstractRandomVariable(){
        random = new Ranmar(SeedGenerator.getSeed());
    }

    /**
     * Take standard inverse cumulative density function approach
     * by default.
     */
    @Override
    public Double getNoise(){
        return icdf(random.raw());
    }

    /**
     * integrate the pdf by default.  This is highly non-optimised.
     */
    @Override
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
    @Override
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
    @Override
    public void randomSeed(){ random = new Ranmar(new java.util.Date()); }

    
    /** Set the seed for the internal Random */
    @Override
    public void setSeed(long seed) { random = new Ranmar(seed); }
    
    /** Default is the return the wrapped version of this random variable */
    @Override
    public CircularRandomVariable getWrapped() {
        return new WrappedCircularRandomVariable(this);
    }
    
    /** 
     * Numerical integration to compute characteristic function.
     * This is very approximate, as it guesses an interval to integrate over.
     */
    @Override
    public Complex characteristicFunction(final double t){
        int integralsteps = 5000;
        double startint = getMean() - 30*Math.sqrt(getVariance());
        double endint = getMean() + 30*Math.sqrt(getVariance());
        double rvar = (new Integration(new IntegralFunction() {
            public double function(double x) {
                return Math.cos(t*x)*pdf(x);
            }
        }, startint, endint)).gaussQuad(integralsteps);
        double cvar = (new Integration(new IntegralFunction() {
            public double function(double x) {
                return Math.sin(t*x)*pdf(x);
            }
        }, startint, endint)).gaussQuad(integralsteps);
        
        return new Complex(rvar, cvar);
    }

    
}
