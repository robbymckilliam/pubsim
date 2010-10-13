/*
 * UniformNoise.java
 *
 * Created on 7 May 2007, 12:40
 */

package distributions;

import rngpack.Ranlux;

/**
 *
 * @author Robby McKilliam
 */
public class UniformNoise extends AbstractRandomVariable implements RandomVariable {
    protected double range;
    
    /** Creates a new instance of UniformNoise with specific variance and mean */
    public UniformNoise(double mean , double variance) {
        super();
        this.mean = mean;
        setVariance(variance);
    }
    
    /** Creates UniformNoise with mean = 0.0 and variance = 1.0 */
    public UniformNoise() {
        mean = 0.0;
        setVariance(1.0);
    }

    @Override
    public void setVariance(double variance){
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
        range = 2.0 * Math.sqrt( 3.0 * variance );
    }
    
    /** 
     * The noise is uniformly distributed in
     * [-range*0.5 + mean, range*0.5 + mean]
     */
    public void setRange(double range){
        this.range = range;
        variance =  Math.pow(range/2.0 , 2)/3.0;
        stdDeviation = Math.sqrt(variance);
    }

    public double getRange() { return range; };
    
    /** Returns a uniformly distributed value */
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

    public double icdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
