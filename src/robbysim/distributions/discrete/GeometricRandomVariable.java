/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions.discrete;

import robbysim.distributions.RandomVariable;
import rngpack.RandomElement;
import rngpack.RandomSeedable;
import rngpack.Ranlux;

/**
 * Implementation of the geometric distribution.
 * This is the variant that can not return 0 i.e. the pdf is
 * P(k) = (1-p)^(k-1)p
 * @author Robby McKilliam
 */
public class GeometricRandomVariable implements RandomVariable {
    private final double p;

    protected RandomElement random = new Ranlux(RandomSeedable.ClockSeed());

    /** 
     * Constructor sets the parameter for this geometric distribution.
     * p must be between 0 and 1.
     */
    public GeometricRandomVariable(double p){
        if(p <= 0 || p > 1)
            throw new RuntimeException("p must be between 0 and 1.");
        this.p = p;
    }

    @Override
    public double getNoise() {
        double v = 1;
        while(random.raw() > p) v++;
        return v;
    }

    public double pdf(double x) {
        return p*Math.pow(1-p,x-1);
    }

    public double icdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getMean() {
        return 1.0/p;
    }

    public double getVariance() {
        return (1-p)/(p*p);
    }

    /** Randomise the seed for the internal Random */
    public void randomSeed(){ random = new Ranlux(RandomSeedable.ClockSeed()); }


    /** Set the seed for the internal Random */
    public void setSeed(long seed) { random = new Ranlux(seed); }

    public double cdf(double x) {
        return 1.0 - Math.pow(1-p,x);
    }

}
