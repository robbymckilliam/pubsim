/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.nearset;

import java.util.TreeMap;
import lattices.Dn;

/**
 * Computes the nearest point to a line in the Dn lattice.
 * @author Robby McKilliam
 */
public class DnLine implements NearSetDecoder<Double>{

    protected final double[] u;
    protected double lambda;

    /**
     * Locate nearest lattice point in Zn to line ry + c.
     * @param y line direction vector
     * @param c line translation
     * @param rmin smallest parameter
     * @param rmax largest parameter
     */
    public DnLine(double[] y, double[] c, double rmin, double rmax){
        if(y.length != c.length)
            throw new RuntimeException("y and c must be the same length");

        final int N = y.length;
        u = new double[N];
        TreeMap map = new TreeMap();

        //dot product variables
        double ztz = 0, yty = 0, zty = 0;

        //compute the first point in the lattice
        double[] yp = new double[N];
        for(int n = 0; n < N; n++) yp[n] = rmin*y[n] + c[n];
        Dn lattice = new Dn(N);
        lattice.nearestPoint(yp);
        double[] ut = lattice.getLatticePoint();

        //initialise dot products
        for(int n = 0; n < N; n++){
            double z = ut[n] - c[n];
            ztz += z*z;
            zty += z*y[n];
            yty += y[n]*y[n];
        }

        

    }


    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Double getLambda() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
