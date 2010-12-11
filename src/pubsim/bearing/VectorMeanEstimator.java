/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.bearing;

import pubsim.distributions.circular.CircularRandomVariable;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;

/**
 * Assumes that angles are measure in interval [-1/2, 1/2).
 * @author Robby McKilliam
 */
public class VectorMeanEstimator implements BearingEstimator {

    public double estimateBearing(double[] y) {
        
        double csum = 0.0, ssum = 0.0;
        double twopi = 2.0 * Math.PI;
        for(int i = 0; i < y.length; i++){
            csum += Math.cos(twopi*y[i]);
            ssum += Math.sin(twopi*y[i]);
        }
        
        return Math.atan2(ssum, csum)/twopi;
                
    }

    public static double asymptoticVariance(final CircularRandomVariable noise, int N){

        final int INTEGRAL_STEPS = 1000;
        double Esin2 = (new Integration(new IntegralFunction() {
            public double function(double x) {
                double sinx = Math.sin(2*Math.PI*x);
                return sinx*sinx*noise.pdf(x);
            }
        }, -0.5, 0.5)).trapezium(INTEGRAL_STEPS);
        double sigma2 = 1 - noise.circularVariance();
        return Esin2/(N*sigma2*sigma2*4*Math.PI*Math.PI);
    }

    public double confidenceInterval() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
