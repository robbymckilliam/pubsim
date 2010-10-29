/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions;

/**
 * A `impulse' random variable, or dirac delta etc.
 * @author harprobey
 */
public class ImpulseRandomVariable implements RandomVariable {

    protected final double dval;

    /**
     * dval is the value that the random variable always returns,
     *  i.e. it is where the impulse is.
     */
    public ImpulseRandomVariable(double dval){
        this.dval = dval;
    }

    @Override
    public double getNoise() {
        return dval;
    }

    /**
     * Return 1.0 at the impulse.  This makes plotting it easy and is
     * correct for discrete rv's but isn't strictly correct for continous rv's.
     */
    public double pdf(double x) {
        if(x == dval) return 1.0;
        else return 0;
    }

    public double icdf(double x) {
        throw new UnsupportedOperationException("The inverse cumulative density function of an impulse is not well defined");
    }

    public double getMean() {
        return dval;
    }

    public double getVariance() {
        return 0;
    }

    public void randomSeed() {
    }

    public void setSeed(long seed) {
    }

    public double cdf(double x) {
        if(x >= dval) return 1.0;
        else return 0;
    }

}
