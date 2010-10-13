/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import distributions.AbstractRandomVariable;
import distributions.RandomVariable;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;

/**
 * @author Robby McKilliam
 */
public abstract class CircularRandomVariable extends AbstractRandomVariable {

    /**
     * Return the unwrapped variance.
     */
    public abstract double unwrappedVariance();

    /**
     * Return the wrapped mean
     */
    public abstract double unwrappedMean();

    /**
     * Return the circular mean
     */
    public abstract double circularMean();

    /**

     */
    public abstract double circularVariance();

    /**
     * Binary search of the cdf to find the inverse cdf.
     * This uses the fact that circular random variable are in
     * [0.5, 0.5].
     */
    @Override
    public double icdf(double x){
        double TOL = 1e-9;
        double high = 0.5;
        double low = -0.5;
        double cdfhigh = cdf(high);
        double cdflow = cdf(low);
        while(Math.abs(high - low) > TOL){

            double half = (high + low)/2.0;
            double cdfhalf = cdf(half);

            //System.out.println("half = " + half + ", cdfhalf = " + cdfhalf);

            if(Math.abs(cdfhalf - x) < TOL ) return half;
            else if(cdfhalf <= x){
                low = half;
                cdflow = cdfhalf;
            }
            else{
               high = half;
               cdfhigh = cdfhalf;
            }

        }
        return (high + low)/2.0;
    }

//    /** Class to compute wrapped variances numerically */
//    public static class WrappedVarianceCalculator implements IntegralFunction {
//
//        RandomVariable distribution;
//        public WrappedVarianceCalculator(RandomVariable dist){
//            distribution = dist;
//        }
//
//        public double function(double x) {
//            return x*x*distribution.pdf(x);
//        }
//
//        public double computeVarianceMod1(){
//            int INTEGRAL_STEPS = 10000;
//            Integration intg = new Integration(this, -0.5, 0.5);
//            return intg.trapezium(INTEGRAL_STEPS);
//        }
//
//        public double computeVarianceMod2pi(){
//            int INTEGRAL_STEPS = 10000;
//            Integration intg = new Integration(this, -Math.PI, Math.PI);
//            return intg.trapezium(INTEGRAL_STEPS);
//        }
//
//    }


}
