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

    /**
     * The dimension of the lattice is n, but it lies in
     * and n+1 dimensional subspace.
     * @param n
     * @param r
     */
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
     * Based on Sloanes Projecting the unit Cube paper.  Really this is
     * obvious and I should have thought of it.
     * @return
     */
    public Matrix getGeneratorMatrix() {
        Matrix P = new Matrix(n+1, n);
        for(int j = 0; j < n; j++){
            P.set(0, j, -Math.pow(j + 2, r));
            P.set(j+1,j, 1.0);
        }
        return P;
    }


}
