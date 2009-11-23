/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.An;

import lattices.*;
import Jama.Matrix;
import simulator.Util;

/**
 * Abstract for the lattice An.  Protected variable u and n
 * must be set by the extending class.  u is the nearest point
 * and n is the dimension.
 * @author Robby McKilliam
 */
public abstract class An extends LatticeAndNearestPointAlgorithm {

    protected double[] u;
    protected int n;

    protected An() {}

    @Override
    public double[] getLatticePoint() {
        return u;
    }

    @Override
    public double[] getIndex() {
        return u;
    }

    @Override
    public double volume() {
        return Math.sqrt(n+1);
    }

    @Override
   public double inradius() {
        return Math.sqrt(2)/2.0;
    }

    public int getDimension() {
        return n;
    }

    /**
     * Return the n+1 by n generator matrix for An.
     */
    public Matrix getGeneratorMatrix() {
        return getGeneratorMatrixBig().getMatrix(0, n, 0, n-1);
    }

    /**
     * Return the n+1 by n+1 matrix that contains the generator
     * matrix for An but is not linear independent.
     */
    public Matrix getGeneratorMatrixBig() {
        Matrix B = Matrix.identity(n+1, n+1);
        for(int i = 0; i < n+1; i++)
            B.set(Util.mod(i+1, n+1), Util.mod(i, n+1), -1.0);
        return B;
    }

    /**
     * Returns glue vector [i] for An.
     * See SPLAG pp109.  This is not an
     * efficient implementation.  It
     * allocates memory.  This is
     * here for testing purposes.
     */
    public double[] getGlueVector(double i){
        double[] g = new double[n+1];
        double j = n + 1 - i;
        for(int k = 0; k < j; k++)
            g[k] = i/(n+1);
        for(int k = (int)j; k < n + 1; k++)
            g[k] = -j/(n+1);
        return g;
    }

    /**
     * Return the convering radius for this lattice
     */
    @Override
    public double coveringRadius(){
        throw new UnsupportedOperationException("Covering radius not supported");
    }

    @Override
    public double kissingNumber() {
        return n*(n+1);
    }

}
