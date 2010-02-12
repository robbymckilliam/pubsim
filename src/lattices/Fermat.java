/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import lattices.*;
import Jama.Matrix;

/**
 * Fermat lattice is the intersection of Zn with the hyperplane orthogonal
 * to [1,2,3,4,5,...n+1].^r where .^ is elementwise power.  The minimum
 * norm of these lattices is atleast 4. This follows from Fermat's last
 * theorem.
 * @author Robby McKilliam
 */
public class Fermat extends AbstractLattice {
    
    protected final int n, r;

    public Fermat(int n, int r){
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Under construction.  I am not exactly sure how to do this at
     * the moment.
     * @return
     */
    public Matrix getGeneratorMatrix() {
        Matrix P = (new Vnm(r+1, n - r)).getGeneratorMatrix();
        Matrix M = new Matrix(n+1, n);
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < n-r; j++){
                M.set(i, j, P.get(i, j));
            }
        }
        int c = 0;
        for(int j = n-r; j < n; j++){
            for(int i = 0; i < n+1; i++){
                M.set(i, j, Math.pow( i+1, c) );
            }
            c++;
        }
        return M;
    }


}
