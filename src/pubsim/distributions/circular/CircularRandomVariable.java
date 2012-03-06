/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import pubsim.Complex;
import pubsim.distributions.ContinuousRandomVariable;
import pubsim.distributions.SeedGenerator;
import rngpack.RandomElement;
import rngpack.RandomSeedable;
import rngpack.Ranmar;

/**
 * Abstract class for circular random variables.  Automates computation
 * of unwrapped and circular means.
 * @author Robby McKilliam
 */
public abstract class CircularRandomVariable implements ContinuousRandomVariable {

    protected UnwrappedMeanAndVariance unwrped;
    protected CircularMeanVariance circ;

    protected RandomElement random;

    public CircularRandomVariable(){
        random = new Ranmar(SeedGenerator.getSeed());
    }
    
    /**
     * Return the unwrapped variance.
     */
    public double unwrappedVariance(){
        if(unwrped == null) unwrped = new UnwrappedMeanAndVariance(this);
        return unwrped.getUnwrappedVariance();
    }

    /**
     * Return the unwrapped variance assuming that the mean is true mean.
     * This is much faster and more accurate if you know the mean in advance.
     */
    public double unwrappedVariance(double truemean){
        if(unwrped == null || unwrped.getUnwrappedMean() != truemean)
            unwrped = new UnwrappedMeanAndVariance(this,truemean);
        return unwrped.getUnwrappedVariance();
    }

    /**
     * Return the wrapped mean
     */
    public double unwrappedMean(){
        if(unwrped == null) unwrped = new UnwrappedMeanAndVariance(this);
        return unwrped.getUnwrappedMean();
    }

    /**
     * Return the circular mean
     */
    public double circularMean(){
        if(circ == null) circ = new CircularMeanVariance(this);
        return circ.circularMean();
    }

    /**
     * Return the circular variance
     */
    public double circularVariance(){
        if(circ == null) circ = new CircularMeanVariance(this);
        return circ.circularVariance();
    }

    /** Randomise the seed for the internal Random */
    @Override
    public void randomSeed(){ random = new Ranmar(new java.util.Date()); }


    /** Set the seed for the internal Random */
    @Override
    public void setSeed(long seed) { random = new Ranmar(seed); }

    /**
     * Binary search of the cdf to find the inverse cdf (i.e. bisection method).
     * This uses the fact that circular random variable are in
     * [0.5, 0.5].
     */
    @Override
    public double icdf(double x){
        double TOL = 1e-8;
        int maxiters = 50, iters = 0;
        double high = 0.5;
        double low = -0.5;
        double cdfhigh = cdf(high);
        double cdflow = cdf(low);
        while(Math.abs(high - low) > TOL && iters < maxiters){

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
            
            iters++;

        }
        return (high + low)/2.0;
    }

    /**
     * Take standard inverse cumulative density function approach
     * by default.
     */
    @Override
    public Double getNoise(){
        return icdf(random.raw());
    };

    /**
     * integrate the pdf by default
     */
    @Override
    public double cdf(double x){
        double startint = -0.5;
        final int INTEGRAL_STEPS = 1000;
        double cdfval = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    return pdf(x);
                }
            }, startint, x)).trapezium(INTEGRAL_STEPS);
        return cdfval;
    }

    /**
     * This returns the `mean' i.e. the expected value of the circular
     * random variable.  This does not necessarily correspond to the
     * `mean direction.  See my thesis or papers.
     */
    @Override
    public double getMean(){
        final int INTEGRAL_STEPS = 1000;
        double tmean = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    return x*pdf(x);
                }
            }, -0.5, 0.5)).trapezium(INTEGRAL_STEPS);
        return tmean;
    }

    /**
     * This returns the `variance' i.e. the expected value of the circular
     * random variable squared subtract it's mean.  This does not necessarily correspond to the
     * circular variance or the unwrapped variance.  See my thesis or papers.
     */
    @Override
    public double getVariance(){
        final int INTEGRAL_STEPS = 1000;
        double tvar = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    return x*x*pdf(x);
                }
            }, -0.5, 0.5)).trapezium(INTEGRAL_STEPS);
            double tmean = getMean();
        return tvar - tmean*tmean;
    }

    /** Default is the return the wrapped version of this random variable */
    @Override
    public CircularRandomVariable getWrapped() { return this; }
    
     /** 
     * Numerical integration to compute characteristic function.
     * Apart from very strange circular distributions, this should be reasonably accurate,
     */
    @Override
    public Complex characteristicFunction(final double t){
        int integralsteps = 5000;
        double rvar = (new Integration(new IntegralFunction() {
            public double function(double x) {
                return Math.cos(t*x)*pdf(x);
            }
        }, -0.5, 0.5)).gaussQuad(integralsteps);
        double cvar = (new Integration(new IntegralFunction() {
            public double function(double x) {
                return Math.sin(t*x)*pdf(x);
            }
        }, -0.5, 0.5)).gaussQuad(integralsteps);
           
        return new Complex(rvar, cvar);
    }
     
}
