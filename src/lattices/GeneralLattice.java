/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;

/**
 * Abstract class that represents a lattice with arbitrary
 * generator matrix.
 * @author Robby McKilliam
 */
public abstract class GeneralLattice extends NearestPointAlgorithm{

    /** The generator matrix for the lattice */
    protected Matrix B;
    
    public void setGeneratorMatrix(double[][] B){
        this.B = new Matrix(B);
    }
    
    public void setGeneratorMatrix(Matrix B){
        this.B = B;
    }

    public Matrix getGeneratorMatrix() {
        return B;
    }

}
