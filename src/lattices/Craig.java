/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import lattices.*;
import Jama.Matrix;
import lattices.An.AnSorted;

/**
 *
 * @author Robby McKilliam
 */
public class Craig extends AbstractLattice{

    protected final int n, r;

    /**
     * Craig's difference lattices.  Craig(n,0) = Z^(n+1).
     * Craig(n,1) = An.
     * @param n dimension
     * @param r difference order.
     */
    public Craig(int n, int r){
        this.n = n;
        this.r = r;
    }

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDimension() {
        return n;
    }

    /**
     * This is only true if p = n+1 is prime and if
     * p = 3 mod 4.
     * @return
     */
    @Override
    public long kissingNumber(){
        return n*(n+1);
    }

    /**
     * This is only true if p = n+1 is prime.
     */
    @Override
    public double inradius(){
         return Math.sqrt(2*r)/2;
    }

    @Override
    public double volume(){
        if(r == 0) return 1.0;
        return Math.pow(n+1, r - 0.5);
    }

    /**
     * This is not efficient way of constructing the generator matrix.
     * A better way would be to use the cyclic nature of the matrix.
     * This is simple to code though.
     */
    public Matrix getGeneratorMatrix() {
        if(r == 0) return Matrix.identity(n+1, n+1);

        Matrix D = (new AnSorted(n)).getGeneratorMatrixBig();
        Matrix A = (new AnSorted(n)).getGeneratorMatrix();
        for(int i = 1; i < r; i++){
            A = D.times(A);
        }
        return A;

    }

}
