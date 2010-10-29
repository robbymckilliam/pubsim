/*
 * UniformNoise.java
 *
 * Created on 7 May 2007, 12:40
 */

package robbysim.distributions;

import rngpack.Ranlux;

/**
 *
 * @author Robby McKilliam
 */
public class UniformNoise extends AbstractRandomVariable implements RandomVariable {
    protected final double range;
    
    /** Creates a new instance of UniformNoise with specific variance and mean */
    public UniformNoise(double mean , double variance) {
        super(mean, variance);
        range = 2.0 * Math.sqrt( 3.0 * variance );
    }

    /**
     * Creates uniform noise with a specific range,
     * rather than varianac. Third variable is dummy.
     */
    public UniformNoise(double mean, double range, int nothing){
        super(mean, Math.pow(range/2.0 , 2)/3.0);
        this.range = range;
    }

    public double getRange() { return range; };
    
    /** Returns a uniformly distributed value */
    @Override
    public double getNoise(){
        return mean + range * (random.raw() - 0.5);
    }

    public double pdf(double x){
        double h = 1.0/range;
        double min = mean - 0.5*range;
        double max = mean + 0.5*range;
        if( x < min || x > max ) return 0.0;
        return h;
    }
    
}
