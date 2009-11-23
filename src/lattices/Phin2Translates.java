/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import lattices.Phin2star.Phin2Star;
import simulator.Util;

/**
 * Lattices that lie between the frequency estimation lattice and
 * it's dual.
 * @author Robby McKilliam
 */
public class Phin2Translates extends AbstractLattice {

    protected final int n, j, k;

    public Phin2Translates(int n, int j, int k){
        this.n = n;
        this.j = j;
        this.k = k;
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

    public Matrix getGeneratorMatrix() {
        Matrix Mat = new Phina(2, n).getGeneratorMatrix();

        double[] g = Phin2Star.getgVector(n+2);
        double sg = Phin2Star.sumg2(n+2);

        int f = (int)Math.ceil((n+2.0)/2.0) - 1;

        if(n%2 == 1){
            for(int i = 0; i < n+2; i++){
                Mat.set(i, n-1, -j*1.0/(n+2.0) );
                Mat.set(i, n-2, -k*(1.0/(n+2.0) + g[i]/sg));
            }
            Mat.set(f, n-1, j + Mat.get(f, n-1) );
            Mat.set(f+1, n-2, k + Mat.get(f+1, n-2));
        }
        else{
            for(int i = 0; i < n+2; i++){
                Mat.set(i, n-1, -2.0*j/(n+2.0) );
                Mat.set(i, n-2, -k*(1.0/(n+2.0) + g[i]/sg/2.0));
            }
            Mat.set(f, n-1, j + Mat.get(f, n-1) );
            Mat.set(f+1, n-1, j + Mat.get(f+1, n-1) );
            Mat.set(f+1, n-2, k + Mat.get(f+1, n-2));
        }

        return Mat;
    }

}
