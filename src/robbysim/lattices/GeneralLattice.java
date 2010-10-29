/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices;

import Jama.Matrix;
import robbysim.lattices.decoder.ShortestVector;
import robbysim.Util;
import robbysim.VectorFunctions;

/**
 * Class that represents a lattice with arbitrary
 * generator matrix.
 * @author Robby McKilliam
 */
public class GeneralLattice extends LatticeAndNearestPointAlgorithm {

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

    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
