/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import lattices.util.region.NSphere;
import static simulator.Util.solveQuadratic;

/**
 *
 * @author Robby McKilliam
 */
public class NSphereForLines extends NSphere
                                implements RegionForLines {

    protected double min;
    protected double max;

    /** Sphere of dimension N centered at the origin */
    public NSphereForLines(double radius, int N){
        super(radius, N);
    }

    /** Sphere of dimension center.length centered at center */
    public NSphereForLines(double radius, double[] center){
        super(radius, center);
    }

    public boolean linePassesThrough(double[] m, double[] c) {

        //quadratic coefficients
        double c1 = 0.0; double c2 = 0.0; double c3 = -radius*radius;
        for(int n = 0; n < c.length; n++){
            c1 += m[n]*m[n];
            c2 += 2*m[n]*c[n];
            c3 += c[n]*c[n];
        }

        double[] res = solveQuadratic(c1, c2, c3);
        min = res[0];
        max = res[1];

        return true;

    }

    public double minParam() {
        return min;
    }

    public double maxParam() {
        return max;
    }

}
