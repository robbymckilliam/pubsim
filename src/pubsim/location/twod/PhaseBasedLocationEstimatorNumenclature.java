/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.location.twod;

import pubsim.Point2;

/**
 * Standard numenclature for phase based location 
 * @author Robby McKilliam
 */
public abstract class PhaseBasedLocationEstimatorNumenclature implements
                                            PhaseBasedLocationEstimator{

    /** Array of transmitters */
    protected Transmitter[] trans;

    /** Estimated location */
    protected Point2 loc;

    /** Compute the unwrapping at a location x */
    protected double[] computeUnwrapping(Point2 x){
        int N = trans.length;
        double[] u = new double[N];
        for( int n = 0; n < N; n++){
            Transmitter t = trans[n];
            Point2 p = t.point();
            double T = t.wavelength();
            double dist = p.minus(x).normF();
            u[n] = Math.rint(dist/T);
        }
        return u;
    }

    public Point2 getLocation() {
        return loc;
    }

    /** Compute the distance of the furthest transmitters from x */
    public static double maxDistanceToTransmitters(Point2 x, Transmitter[] trans){
        double D = Double.NEGATIVE_INFINITY;
        for(Transmitter t : trans){
            double d = t.point().minus(x).normF();
            if(d > D)
                D = d;
        }
        return D;
    }

}
