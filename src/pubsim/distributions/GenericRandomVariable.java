package pubsim.distributions;

/**
 *
 * @author Robby McKilliam
 */
public interface GenericRandomVariable<T> extends NoiseGenerator<T> {

    public T mean();
    public T variance();

    /** Return the pdf evaluate at x */
    public double pdf(T x);

    /** Return the cumulative distribution function evaluated at x */
    public double cdf(T x);

    /**
     * Return the inverse cumulative distribution function.
     * This allows get noise to work in a standard way by generating
     * uniform noise in [0,1] and applying icdf.
     * However, you don't have to implement this if you have a better
     * way of generating the noise.
     */
    public T icdf(double x);

}
