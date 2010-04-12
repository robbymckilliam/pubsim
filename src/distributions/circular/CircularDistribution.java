/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import distributions.NoiseGenerator;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;

/**
 *
 * @author harprobey
 */
public interface CircularDistribution extends NoiseGenerator{

    /**
     * Return the wrapped variance i.e. the variance
     * computed in the standard way assume that the distribution
     * @return
     */
    public double getWrappedVariance();

    /** Class to compute wrapped variances numerically */
    public static class VarianceCalculator implements IntegralFunction {

        NoiseGenerator distribution;
        public VarianceCalculator(NoiseGenerator dist){
            distribution = dist;
        }

        public double function(double x) {
            return x*x*distribution.pdf(x);
        }

        public double computeVarianceMod1(){
            int INTEGRAL_STEPS = 10000;
            Integration intg = new Integration(this, -0.5, 0.5);
            return intg.trapezium(INTEGRAL_STEPS);
        }

        public double computeVarianceMod2pi(){
            int INTEGRAL_STEPS = 10000;
            Integration intg = new Integration(this, -Math.PI, Math.PI);
            return intg.trapezium(INTEGRAL_STEPS);
        }



    }

}