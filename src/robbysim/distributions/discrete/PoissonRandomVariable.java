/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions.discrete;

import rngpack.RandomElement;
import rngpack.RandomSeedable;
import rngpack.Ranlux;
import robbysim.distributions.RandomVariable;

/**
 * Class representing the discrete Poisson distribution.
 * @author Robby McKilliam
 */
public class PoissonRandomVariable implements RandomVariable {
    private final double l;

    protected RandomElement random = new Ranlux(RandomSeedable.ClockSeed());

    /**
     * Constructor sets the parameter for this geometric distribution.
     * p must be between 0 and 1.
     */
    public PoissonRandomVariable(double l){
        if(l <= 0)
            throw new RuntimeException("l must be > 0.");
        this.l = l;
    }

    /** Knuth's simple method for generating a Poisson r.v. */
    public double getNoise() {
        double L = Math.exp(-l);
        double p = 1.0;
        double k = 0;
        while(p > L){
            k++;
            double u = random.raw();
            p*=u;
        }
        return k - 1;
    }

    public double pdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double icdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getMean() {
        return l;
    }

    public double getVariance() {
        return l;
    }

    /** Randomise the seed for the internal Random */
    public void randomSeed(){ random = new Ranlux(RandomSeedable.ClockSeed()); }


    /** Set the seed for the internal Random */
    public void setSeed(long seed) { random = new Ranlux(seed); }

    public double cdf(double x) {
       throw new UnsupportedOperationException("Not supported yet.");
    }

}
