/*
 * @author Robby McKilliam
 */

package pubsim.distributions.circular;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import static pubsim.Util.fracpart;
import pubsim.distributions.RealRandomVariable;

/**
 * Computes the intrinsic (wrapped) mean and variance of a circular distribution
 * @author Robby McKilliam
 */
public class InstrinsicMeanAndVariance {

    protected final RealRandomVariable dist;
    protected double mean;
    protected double var;
    protected int numsamples = 1000;

    /** Input is a distribution */
    public InstrinsicMeanAndVariance( RealRandomVariable tdist ){
        this.dist = tdist;

        var = Double.POSITIVE_INFINITY;
        mean = 0;

        for(double t = -0.5; t < 0.5; t += 1.0/numsamples){
            double tvar = computeIntrinsicVarianceAbout(t, dist, 1000);
            if( tvar < var ){
                var = tvar;
                mean = t;
            }
        }

    }

    /**
     * Compute the unwrapped variance assuming that the mean is truemean,
     * i.e. this allows you to specify the mean ahead of time. This is much
     * faster if you do know the mean.
     */
    public InstrinsicMeanAndVariance( RealRandomVariable tdist, double truemean ){
        this.dist = tdist;
        var = computeIntrinsicVarianceAbout(truemean, dist, 10000);
        mean = truemean;

    }

    /** Compute the wrapped variance after applying a rotaton of phi */
    public static double computeIntrinsicVarianceAbout(final double phi, final RealRandomVariable dist, int integralsteps){
        double tvar = (new Integration(new IntegralFunction() {
            public double function(double x) {
                double rot = fracpart(x-phi);
                return rot*rot*dist.pdf(x);
            }
        }, -0.5, 0.5)).gaussQuad(integralsteps);
        return tvar;
    }

    public double getIntrinsicVariance(){
        return var;
    }

    /**
     * This is only accurate the the 3rd decimal place.
     * This could be improved by an optimisation routine but
     * I have not implemented it.
     */
    public double getIntrinsicMean(){
        return mean;
    }

    public void computeAndPrint(int numsamples){
        for(double t = -0.5; t < 0.5; t += 1.0/numsamples){
            final double ft = t;
            final int INTEGRAL_STEPS = 1000;
            double tvar = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    double rot = fracpart(x-ft);
                    return rot*rot*dist.pdf(x);
                }
            }, -0.5, 0.5)).trapezium(INTEGRAL_STEPS);
            System.out.println(ft + "\t" + tvar);
        }
    }
    

}
