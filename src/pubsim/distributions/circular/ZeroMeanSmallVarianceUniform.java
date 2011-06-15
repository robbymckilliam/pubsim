/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.circular;

 /**
 * Circular uniform random variable with zero mean and variance less than 1/12 so
 * it does not 'wrap'. This make some computations easier.
 */
public class ZeroMeanSmallVarianceUniform extends CircularRandomVariable {

    protected final double range;
    protected final double stdDeviation;
    protected final double variance;

    /** Creates a new instance of GaussianNoise with specific variance and mean */
    public ZeroMeanSmallVarianceUniform(double variance){
        if(variance > 1.0/12) throw new RuntimeException("Can't have variance greater than 1/12");
        this.variance = variance;
        this.stdDeviation = Math.sqrt(variance);
        range = 2.0 * Math.sqrt( 3.0 * variance );
    }

    /**
     * Creates uniform noise with a specific range,
     * rather than variance. Third variable is dummy.
     */
    public ZeroMeanSmallVarianceUniform(double range, int nothing){
        this.variance = Math.pow(range/2.0 , 2)/3.0;
        this.stdDeviation = Math.sqrt(variance);
        this.range = range;
    }

    @Override
    public double pdf(double x){
        double h = 1.0/range;
        double min =  -0.5*range;
        double max =  0.5*range;
        if( x < min || x > max ) return 0.0;
        return h;
    }

    @Override
    public double cdf(double x){
        double h = 1.0/range;
        double min = -0.5*range;
        double max = 0.5*range;
        if(x < min) return 0.0;
        if(x > max) return 1.0;
        else return h*(x - min);
    }

}
