/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.discrete;

import pubsim.distributions.SeedGenerator;
import rngpack.RandomElement;
import rngpack.RandomSeedable;
import rngpack.Ranlux;

/**
 *
 * @author Robby McKilliam
 */
public abstract class AbstractDiscreteRandomVariable implements DiscreteRandomVariable {

    protected RandomElement random = new Ranlux(SeedGenerator.getSeed());

    /** Randomise the seed for the internal Random */
    @Override
    public void randomSeed(){ random = new Ranlux(RandomSeedable.ClockSeed()); }
    
    /** Set the seed for the internal Random */
    @Override
    public void setSeed(long seed) { random = new Ranlux(seed); }
    
}
