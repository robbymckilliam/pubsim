/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;

/**
 * The hexangonal lattice with genortor matrix
 * [1 1/2; 0 sqrt(3)/2]
 * @author Robby McKilliam
 */
public class Hexagonal implements LatticeAndNearestPointAlgorithm{

    public double volume() {
        throw new UnsupportedOperationException("Not supported yet.");
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

    public int getDimension() {
        return 2;
    }

    public Matrix getGeneratorMatrix() {
        Matrix M = new Matrix(2,2);
        M.set(0, 0, 1.0); M.set(0, 1, 0.5);
        M.set(1, 0, 0.0); M.set(1, 1, 0.5*Math.sqrt(3.0));
        return M;
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
