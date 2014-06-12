package pubsim.distributions;

/**
 * Class implements a Levy distribution.  This distribution is heavy tailed and a member of
 * the class of alpha-Stable distributions.
 * @author Robby McKilliam
 */
public class Levy extends AbstractRealRandomVariable {

    /** The location parameter */
    public final double mu;
    
    /** The scale parameter */
    public final double c;
    
    /** Construct a Levy distribution with location parameter mu and scale parameter c */
    public Levy(double mu, double c){
        this.mu = mu;
        this.c = c;
    }
    
    /** 
     * The Levy distribution has infinite mean
     * @return  Double.POSITIVE_INFINITY
     */
    @Override
    public Double mean() {
        return Double.POSITIVE_INFINITY;
    }

    /** 
     * The Levy distribution has infinite variance
     * @return  Double.POSITIVE_INFINITY
     */
    @Override
    public Double variance() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double pdf(Double x) {
        double p1 = Math.sqrt(c/2/Math.PI);
        double p2 = Math.exp(-c/2/(x-mu));
        double p3 = Math.pow(x - mu, 3.0/2.0);
        return p1*p2/p3;
    }
    
    @Override
    public double cdf(Double x){
        double p = Math.sqrt(c/2/(x-mu));
        return pubsim.Util.erfc(p);
    }
    
}
