package pubsim.distributions;

import pubsim.Complex;
import pubsim.distributions.circular.CircularRandomVariable;
import pubsim.distributions.circular.WrappedCircularRandomVariable;

/**
 * A `impulse' random variable, or dirac delta etc.
 * @author Robby McKilliam
 */
public class ImpulseRandomVariable implements RealRandomVariable {

    protected final double dval;

    /**
     * dval is the value that the random variable always returns,
     *  i.e. it is where the impulse is.
     */
    public ImpulseRandomVariable(double dval){
        this.dval = dval;
    }

    @Override
    public Double noise() {
        return dval;
    }

    /**
     * Return 1.0 at the impulse.  This makes plotting it easy and is
     * correct for discrete RV's but isn't strictly correct for continuous RV's.
     */
    @Override
    public double pdf(Double x) {
        if(x == dval) return 1.0;
        else return 0;
    }

    @Override
    public Double icdf(double x) {
        throw new UnsupportedOperationException("The inverse cumulative density function of an impulse is not well defined");
    }

    @Override
    public Double mean() {
        return dval;
    }

    @Override
    public Double variance() {
        return 0.0;
    }

    @Override
    public void randomSeed() {
    }

    @Override
    public void setSeed(long seed) {
    }

    @Override
    public double cdf(Double x) {
        if(x >= dval) return 1.0;
        else return 0;
    }

    /** Default is the return the wrapped version of this random variable */
    @Override
    public CircularRandomVariable wrapped() {
        return new WrappedCircularRandomVariable(this);
    }

    @Override
    public Complex characteristicFunction(Double t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
