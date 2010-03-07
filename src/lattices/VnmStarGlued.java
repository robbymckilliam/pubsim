/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import simulator.Util;
import static simulator.Util.binom;
import static simulator.Util.factorial;
import static simulator.Util.discreteLegendrePolynomial;

/**
 * Glue vector based algorithm for VnmStar.
 * I am using the notation from my thesis.  m is the order of the polynomial
 * and N = n + m + 1 is the dimension that lattice lies in.  n is the
 * dimension of the lattice.
 * 
 * @author Robby McKilliam
 */
public class VnmStarGlued extends NearestPointAlgorithmStandardNumenclature {


    //generator matrix for the dual of the integer valued polynomials
    //assuming they are tranformed to the integer lattice.
    Matrix B;
    
    double[] yt;
    int m, N;

    public VnmStarGlued(int m, int n){
        this.m = m;
        setDimension(n);
    }

    public VnmStarGlued(int m){
        this.m = m;
    }

    public void setDimension(int n) {
        this.n = n;
        N = n + m + 1;

        //compute the matrix mapping the dual to integer valued polys
        Matrix M = new Matrix(m+1, m+1);
        for(int k = 0; k <= m; k++){
            long fk = factorial(k);
            double ldl = fk*fk*binom(N+k, 2*k+1)/((double)binom(2*k,k));
            for(int i = 0; i <= m; i++){
                double l = discreteLegendrePolynomial(k, N, i);
                M.set(i, k, l/ldl);
            }
        }
        Matrix L = new Matrix(m+1, m+1);
        for(int k = 0; k <= m; k++){
            double scale = factorial(k)/((double)binom(2*k,k));
            for(int s = 0; s <= m; s++){
                double l = scale*Math.pow(-1, s+k)*binom(s+k, s)*binom(N-s-1, N-k-1);
                L.set(k, s, l);
            }
        }
        B = M.times(L);

        //allocate working memory
        yt = new double[n+m];

    }

    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void nearestPoint(double[] y) {
        double num_glues = Vnm.volume(m, n);

    }



}
