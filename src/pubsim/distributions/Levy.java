package pubsim.distributions;

/**
 * Class implements a Levy distribution.  This distribution is heavy tailed and a member of
 * the class of alpha-Stable distributions.
 * @author Robby McKilliam
 */
public class Levy extends AbstractRealRandomVariable {

    /** The location parameter. */
    public final double mu;
    
    /** The scale parameter. */
    public final double c;
    
    /** 
     * By default the location parameter is zero.
     * @param c scale parameter 
     */
    public Levy(double c){
        this(0.0,c);
    }
    
    /** Construct a Levy distribution with location parameter mu and scale parameter c. */
    public Levy(double mu, double c){
        if(c <= 0.0) throw new RuntimeException("Scale parameter c must be greater than 0.");
        this.mu = mu;
        this.c = c;
    }
    
    /** 
     * The Levy distribution has infinite mean.
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
        double DELTA = c/30;
        if(x <= mu + DELTA) return 0.0;
        double p1 = Math.sqrt(c/2/Math.PI);
        double p2 = Math.exp(-c/2/(x-mu));
        double p3 = Math.pow(x - mu, 3.0/2.0);
        return p1*p2/p3;
    }
    
    @Override
    public double cdf(Double x){
        if(x <= mu) return 0.0;
        double p = Math.sqrt(c/2/(x-mu));
        return pubsim.Util.erfc(p);
    }
    
}
