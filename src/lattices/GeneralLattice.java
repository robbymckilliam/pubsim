/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import lattices.decoder.ShortestVector;
import simulator.Util;
import simulator.VectorFunctions;

/**
 * Class that represents a lattice with arbitrary
 * generator matrix.
 * @author Robby McKilliam
 */
public class GeneralLattice extends AbstractLattice{

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

    public Matrix getGeneratorMatrix() {
        return B;
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDimension() {
        return B.rank();
    }

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
