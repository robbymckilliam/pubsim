/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;

/**
 * Compute the circularVariance of the argument of the complex mean estimator
 * given a noise distribution.
 * @author Robert McKilliams
 */
public class CircularMeanVariance {

    protected final CircularRandomVariable f;
    protected final double var;
    protected final double mean;

    public CircularMeanVariance(CircularRandomVariable dist){
        f = dist;

        final int INTEGRAL_STEPS = 10000;
        double Ecos = (new Integration(new IntegralFunction() {
            public double function(double x) {
                return Math.cos(2*Math.PI*x)*f.pdf(x);
            }
        }, -0.5, 0.5)).gaussQuad(INTEGRAL_STEPS);
        double Esin = (new Integration(new IntegralFunction() {
            public double function(double x) {
                return Math.sin(2*Math.PI*x)*f.pdf(x);
            }
        }, -0.5, 0.5)).gaussQuad(INTEGRAL_STEPS);

        var = 1 - Math.sqrt( Ecos*Ecos + Esin*Esin);
        mean = Math.atan2(Esin, Ecos)/(2*Math.PI);

    }

    /**
     * Numerically compute the circularVariance of the argument of the complex mean.
     * @return circularVariance of the argument of the complex mean estimator
     */
    public double circularVariance(){
        return var;
    }


    public double circularMean(){
        return mean;
    }


}
