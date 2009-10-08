/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import lattices.Phin2star.Phin2Star;

/**
 * Lattices that lie between the frequency estimation lattice and
 * it's dual.
 * @author Robby McKilliam
 */
public class Phin2Translates implements Lattice {

    protected final int n, j, k;

    public Phin2Translates(int n, int j, int k){
        this.n = n;
        this.j = j;
        this.k = k;
    }

    public double volume() {
        Matrix B = getGeneratorMatrix();
        return Math.sqrt((B.transpose().times(B)).det());
    }

    public double inradius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double centerDensity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDimension() {
        return n;
    }

    public Matrix getGeneratorMatrix() {
        Matrix Mat = new Phina(2, n).getGeneratorMatrix();

        double[] g = Phin2Star.getgVector(n+2);
        double sg = Phin2Star.sumg2(n+2);

        int f = (int)Math.ceil((n+2.0)/2.0);

        for(int i = 0; i < n+2; i++){
            Mat.set(i, n-1, k*(-1.0/(n+2.0) - g[i]*g[f]/sg));
            Mat.set(i, n-2, j*(-1.0/(n+2.0) - g[i]*g[f-1]/sg));
        }
        Mat.set(f, n-1, k*(1.0 - 1.0/(n+2.0) - g[f]*g[f]/sg));
        Mat.set(f-1, n-2, j*(1.0 - 1.0/(n+2.0) - g[f-1]*g[f-1]/sg));

        return Mat;
    }

}
