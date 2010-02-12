/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import static simulator.Util.binom;
import static simulator.Util.factorial;

/**
 * Glue vector based algorithm for VnmStar.  UNDER CONSTRUCTION.
 * @author Robby McKilliam
 */
public class VnmStarGlued extends NearestPointAlgorithmStandardNumenclature {


    double[][] p, g;

    public VnmStarGlued(int n, int m){
        p = new double[m][];
        for( int i = 0; i <=m; i++)
            p[i] = discreteLegendrePolynomial(n+m, i);

        g = new double[m][n+m];
        for(int j = 0; j <=m; j++){
            for(int x = 0; x <= j; x++) g[j][x] = Math.pow(-1, j)*binom(j, x);
            for(int i = 0; i <=m; i++){
                for(int x = 0; x < n+m; x++) g[0][x] -= p[i][0]*p[i][x];
            }
        }

    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    /**
     * Return the mth monic discrete Legendre polynomial of length n.
     */
    public static double[] discreteLegendrePolynomial(int n, int m){
        if(m < 0 || m > n)
            throw new ArrayIndexOutOfBoundsException("m is out of range");

        double[] p = new double[n];
        long scale = factorial(m)/binom(2*m, m);
        for(int s = 0; s <= m; s++){
            for(int x = 0; x < n; x++)
                p[x] += scale*Math.pow(-1, s+m)*binom(s+m, s)*binom(n-s-1, n-m-1)*binom(x,s);
        }
        return p;
    }

}
