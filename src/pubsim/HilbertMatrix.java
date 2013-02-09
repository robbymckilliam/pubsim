/*
 */
package pubsim;

import bignums.BigInteger;
import bignums.BigRational;
import Jama.Matrix;
import bignums.RoundingMode;

/**
 * A class for efficiently and stably dealing with Hilbert matrices and their inverses.
 * @author Robby McKilliam
 */
public class HilbertMatrix {
    
    final BigRational[][] qmem;
    final BigRational[][] Pmem;
    final BigRational[][] Finvmem;
    final int m;
    final int N;
    
    /** M by M Hilbert matrix */
    public HilbertMatrix(int M, int N){
        m = M-1;
        this.N = N;
        qmem = new BigRational[m+1][m+1];
        Pmem = new BigRational[m+1][m+1];
        Finvmem = new BigRational[m+1][m+1];
    }
    
    /** 
     * Coefficients of the integer valued polynomials.  Memoizes numbers we will need 
     * regularly for this Hilbert matrix.
     */
    public BigRational q(int s, int i) {
        if(i < 0 || s < 0) return BigRational.ZERO;
        else if(i == 0 && s==0) return BigRational.ONE;
        else if(s == 0) return BigRational.ZERO;
        else if(i <= m && s <= m && qmem[s][i] != null) return qmem[s][i];
        BigRational num = (q(s-1,i-1) / new BigRational(s,1)) - q(s-1,i);
        if(i <= m && s <= m) qmem[s][i] = num;
        return num;
    }
    
    /** 
     * Coefficients of the discrete orthogonal polynomials.  Memoizes numbers we will need 
     * regularly for this Hilbert matrix.
     */
    public BigRational P(int k, int i) {
        if(i < 0 || k < 0) return BigRational.ZERO;
        if(i <= m && k <= m && Pmem[k][i] != null) return Pmem[k][i];
        BigRational sum = BigRational.ZERO;
        for(int s = 0; s <= k; s++){
            BigInteger v = BigInteger.ONE.negate().pow(s+k) * binom(s+k,s) * binom(N-s-1,N-k-1);
            sum = sum + (q(s,i) * new BigRational(v));
        }
        if(i <= m && k <= m && Pmem[k][i] != null) Pmem[k][i] = sum;
        return sum;
    }
    
    /**
     * Calculate the binomial coefficient
     * using a recursive procedure.
     */
    public static BigInteger binom(int n, int m) {
        if(m > n) return BigInteger.ZERO;
        if(n==m || m==0) return BigInteger.ONE;
        if(n/2 > m) return binom(n,n-m);
        return binom(n-1, m-1) + binom(n-1, m);
    }
    
     /** 
     * Elements of the inverse Matrix.  Memoizes elements
     */
    public BigRational Finv(int i, int j){
        if(i < 0 || j < 0) return BigRational.ZERO;
        if(i <= m && j <= m && Finvmem[i][j] != null) return Finvmem[i][j];
        BigRational sum = BigRational.ZERO;
        for(int k = 0; k <= m; k++){
            BigInteger b = binom(2*k, k) * binom(N+k, 2*k+1);
            sum = sum + (P(k,i) * P(k,j) * new BigRational(BigInteger.ONE,b));
        }
        if(i <= m && j <= m) Finvmem[i][j] = sum;
        return sum;
    }
    
    /** Rounds the values of the inverse to doubles and returns a matrix */
    public Matrix Hinverse() {
        Matrix w = new Matrix(m+1,m+1);
        for(int i = 0; i <= m; i++)
            for(int j = 0; j <= m; j++)
                w.set(i,j, Finv(i,j).doubleValue());
        return w;
    }
    
}
