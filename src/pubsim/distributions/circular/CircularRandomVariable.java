/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import pubsim.distributions.RandomVariable;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import rngpack.RandomElement;
import rngpack.RandomSeedable;
import rngpack.Ranlux;

/**
 * Abstract class for circular random variables.  Automates computation
 * of unwrapped and circular means.
 * @author Robby McKilliam
 */
public abstract class CircularRandomVariable implements RandomVariable {

    private UnwrappedMeanAndVariance unwrped;
    private CircularMeanVariance circ;

    protected RandomElement random = new Ranlux(RandomSeedable.ClockSeed());

    /**
     * Return the unwrapped variance.
     */
    public double unwrappedVariance(){
        if(unwrped == null) unwrped = new UnwrappedMeanAndVariance(this);
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
    public void randomSeed(){ random = new Ranlux(RandomSeedable.ClockSeed()); }


    /** Set the seed for the internal Random */
    public void setSeed(long seed) { random = new Ranlux(seed); }

    /**
     * Binary search of the cdf to find the inverse cdf.
     * This uses the fact that circular random variable are in
     * [0.5, 0.5].
     */
    @Override
    public double icdf(double x){
        double TOL = 1e-9;
        double high = 0.5;
        double low = -0.5;
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
     * `mean direction'.  See my thesis or papers.
     */
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
     * random variable squared substract it's mean.  This does not necessarily correspond to the
     * `mean direction'.  See my thesis or papers.
     */
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

}
