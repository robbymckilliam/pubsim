/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder.nearset;

import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import static pubsim.VectorFunctions.dot;
import static pubsim.VectorFunctions.distance_between2;

/**
 * Simple line nearset algorithm that samples the line uniformly.
 * Works for any lattice with a nearest point algorithm.
 * @author Robby McKilliam
 */
public class SampledLine implements NearSetDecoder<Double>{

    protected double lambda;
    protected final double[] u;

    public SampledLine(double[] y, double[] c, double rmin, double rmax,
            int samples, LatticeAndNearestPointAlgorithmInterface lattice){
        if(y.length != c.length)
            throw new RuntimeException("y and c must be the same length");

        final int N = y.length;
        final double[] yp = new double[N];
        double yty = dot(y,y);

        double step = (rmax - rmin)/samples;

        double Dmin = Double.POSITIVE_INFINITY;
        for(double r = rmin; r < rmax; r= samples){
            for(int i = 0; i < N; i++) yp[i] = r*y[i] + c[i];
            lattice.nearestPoint(yp);
            double[] ut = lattice.getLatticePoint();
            double ytz = 0, ztz = 0;
            for(int i = 0; i < N; i++){
                double z = (ut[i] - c[i]);
                ytz += y[i]*z;
                ztz = z*z;
            }

            double rd = ytz/yty;
            double D = rd*rd*yty - 2*rd*ytz + ztz;
            if( D < Dmin ){
                lambda = r;
                Dmin = D;
            }

        }

        for(int i = 0; i < N; i++) yp[i] = lambda*y[i] + c[i];
        lattice.nearestPoint(yp);
        u = lattice.getLatticePoint();

    }

    public double[] getLatticePoint() {
        return u;
    }

    public Double getLambda() {
        return lambda;
    }



}
