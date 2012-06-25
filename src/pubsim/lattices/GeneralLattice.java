/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import Jama.Matrix;

/**
 * Class that represents a lattice with arbitrary
 * generator matrix.
 * @author Robby McKilliam
 */
public class GeneralLattice extends AbstractLattice {

    /** The generator matrix for the lattice */
    protected Matrix B;
    
    protected GeneralLattice(){
    }
    
    public GeneralLattice(Matrix B){
        this.B = B;
    }
    
    public GeneralLattice(double[][] B){
        this.B = new Matrix(B);
    }

    @Override
    public Matrix getGeneratorMatrix() {
        return B;
    }

    @Override
    public int getDimension() {
        return B.rank();
    }

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
