/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;

/**
 * Compute the variance of the argument of the complex mean estimator
 * given a noise distribution.
 * @author Robert McKilliams
 */
public class ArgComplexMeanVariance {

    protected final CircularDistribution f;

    public ArgComplexMeanVariance(CircularDistribution dist){
        f = dist;
    }

    /**
     * Numerically compute the variance of the argument of the complex mean.
     * @return variance of the argument of the complex mean estimator
     */
    public double variance(){

        //do this a nice way if it is the uniform distribution.
        if(f.getClass() == WrappedUniform.Mod1.class)
            return wrappedUniformVariance((WrappedUniform.Mod1)f);

        final int INTEGRAL_STEPS = 10000;
        double Ecos = (new Integration(new IntegralFunction() {
            public double function(double x) {
                return Math.cos(2*Math.PI*x)*f.pdf(x);
            }
        }, -0.5, 0.5)).trapezium(INTEGRAL_STEPS);
        double Esin2 = (new Integration(new IntegralFunction() {
            public double function(double x) {
                double sn = Math.sin(2*Math.PI*x);
                return sn*sn*f.pdf(x);
            }
        }, -0.5, 0.5)).trapezium(INTEGRAL_STEPS);
        
        return Esin2/(Ecos*Ecos)/(4*Math.PI*Math.PI);
        
    }

    /**
     * Computes argument of the complex mean variance in uniform noise.
     * Assumes that the noise is zero mean and the variance is less
     * that 1/12.
     * @param f
     * @return
     */
    public static double wrappedUniformVariance(WrappedUniform.Mod1 f){
        double r = f.getRange()/2.0;
        double pi = Math.PI;
        

        double num = r*pi*(4*pi*r - Math.sin(4*pi*r));
        double den = 2 * Math.sin(2*pi*r)*Math.sin(2*pi*r);

        //System.out.println("r = " + r + ", num = " + num + ", den = " + den);

        return num/den/(4*pi*pi);
    }

}
