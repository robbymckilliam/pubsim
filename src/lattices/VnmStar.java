/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import simulator.VectorFunctions;
import static simulator.Util.binom;
import static simulator.Util.factorial;
import static simulator.Util.discreteLegendrePolynomial;

/**
 *
 * @author robertm
 */
public abstract class VnmStar extends LatticeAndNearestPointAlgorithm{

    /**
     * Project x into the space of the lattice VnmStarSampled and return
     * the projection into y.  Requires recursion, runs in O(n m) time
     * PRE: x.length = y.length
     */
    public static void project(double[] x, double[] y, int m){
        System.arraycopy(x, 0, y, 0, x.length);
        for(int k = 0; k <= m; k++){
            //add projection down legendre polys.  It's potentially more
            //stable to use a gram-schmidt process?
        }
    }

    /**
     * Return the mth monic discrete Legendre polynomial of length n.
     */
    public static double[] discreteLegendrePolynomialVector(int n, int m){
        if(m < 0 || m > n)
            throw new ArrayIndexOutOfBoundsException("m is out of range");

        double[] p = new double[n];
        double scale = factorial(m)/((double)binom(2*m, m));
        for(int s = 0; s <= m; s++){
            for(int x = 0; x < n; x++)
                p[x] += scale*Math.pow(-1, s+m)*binom(s+m, s)*binom(n-s-1, n-m-1)*binom(x,s);
        }
        return p;
    }


}
