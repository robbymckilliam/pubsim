/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder.nearset;

import java.util.Map.Entry;
import java.util.TreeMap;
import pubsim.IndexedDouble;
import static pubsim.VectorFunctions.round;

/**
 * Computes the nearest point to a line in the integer lattice.
 * @author Robby McKilliam
 */
public class ZnLine implements NearSetDecoder<Double> {

    protected final double[] u;
    protected double lambda;

    /**
     * Locate nearest lattice point in Zn to line ry + c.
     * @param y line direction vector
     * @param c line translation
     * @param rmin smallest parameter
     * @param rmax largest parameter
     */
    public ZnLine(double[] y, double[] c, double rmin, double rmax){
        if(y.length != c.length)
            throw new RuntimeException("y and c must be the same length");

        final int N = y.length;
        final double[] ut = new double[N];
        u = new double[N];
        TreeMap map = new TreeMap();

        //dot product variables
        double ztz = 0, yty = 0, zty = 0;

        //initialise dot products and sorted map.
        for(int n = 0; n < N; n++){
            ut[n] = Math.round(rmin*y[n] + c[n]);
            double z = ut[n] - c[n];
            ztz += z*z;
            zty += z*y[n];
            yty += y[n]*y[n];
            Double l = (z + Math.signum(y[n])/2.0)/y[n];
            map.put(l, new Integer(n));
        }

        //parameter of the first point.
        double r = zty/yty;
        lambda = r;

        double Dmin = Double.POSITIVE_INFINITY;
        while(r < rmax){

            //test current points distance
            double D = r*r*yty - 2*r*zty + ztz;
            if( D < Dmin ){
                lambda = r;
                Dmin = D;
            }

            //remove and return enrty corresponding to next transition.
            Entry<Double, Integer> entry = map.pollFirstEntry();
            int k = entry.getValue();
            double s = Math.signum(y[k]);
            double z = ut[k] - c[k];
            zty += s*y[k];
            ztz += 2*s*z + 1;
            ut[k] += s;
            z += s;

            //update the map
            Double l = (z + s/2.0)/y[k];
            map.put(l, new Integer(k));

            //update r
            r = zty/yty;

        }

        //set u to the closest lattice point.
        for(int n = 0; n < N; n++) u[n] = Math.round(lambda*y[n] + c[n]);

    }

    public double[] getLatticePoint() {
        return u;
    }

    public Double getLambda() {
        return lambda;
    }

}
