/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;

/**
 * Class that represents a lattice with arbitrary
 * generator matrix.
 * @author Robby McKilliam
 */
public class GeneralLattice implements Lattice{

    /** The generator matrix for the lattice */
    protected Matrix B;
    
    public GeneralLattice(){
    }
    
    public GeneralLattice(Matrix B){
        setGeneratorMatrix(B);
    }
    
    public GeneralLattice(double[][] B){
        setGeneratorMatrix(B);
    }
    
    public void setGeneratorMatrix(double[][] B){
        this.B = new Matrix(B);
    }
    
    public void setGeneratorMatrix(Matrix B){
        this.B = B;
    }

    public Matrix getGeneratorMatrix() {
        return B;
    }

    public double volume() {
        return Math.sqrt(B.times(B.transpose()).det());
    }

    public double inradius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double centerDensity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getDimension() {
        return B.rank();
    }

}
