/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import simulator.Util;
import static simulator.Util.binom;
import static simulator.Util.factorial;

/**
 * Glue vector based algorithm for VnmStar.  UNDER CONSTRUCTION.
 * @author Robby McKilliam
 */
public class VnmStarGlued extends NearestPointAlgorithmStandardNumenclature {


    double[] yt;
    double[][] g;
    int m;

    public VnmStarGlued(int m, int n){
        this.m = m;
        setDimension(n);
    }

    public VnmStarGlued(int m){
        this.m = m;
    }

    public void setDimension(int n) {

        //generate the glue vectors.
        double[][] p = new double[m][];
        for( int i = 0; i <=m; i++)
            p[i] = discreteLegendrePolynomialVector(n+m, i);

        g = new double[m][n+m];
        for(int j = 0; j <=m; j++){
            for(int x = 0; x <= j; x++) g[j][x] = Math.pow(-1, j)*binom(j, x);
            for(int i = 0; i <=m; i++){
                double dot = 0.0;
                for(int x = 0; x <= j; x++) dot += p[i][x]*Math.pow(-1, j)*binom(j, x);
                for(int x = 0; x < n+m; x++) g[0][x] -= dot*p[i][x];
            }
        }

        //allocate working memory
        yt = new double[n+m];

    }

    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void nearestPoint(double[] y) {
        double num_glues = Vnm.volume(m, n);

        for( long c = 0; c < num_glues; c++){
            System.arraycopy(y, 0, yt, 0, n+m);
            for( int i = 0; i <= m; i++){
                long d = binom(n+m+i, 2*i+1)/binom(2*i, i);
                long k = Util.mod(c, d);
                for(int t = 0; t < n+m; t++)
                    yt[t] += k*g[i][t];
            }
            c++;
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
