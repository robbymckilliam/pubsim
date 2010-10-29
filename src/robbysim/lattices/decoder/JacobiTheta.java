/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.decoder;

import robbysim.lattices.Lattice;

/**
 * Mandar's approximate nearest point algorithm
 * that climbs an approximation of the Jacobi
 * theta functon
 * @author Robby McKilliam
 */
public class JacobiTheta implements GeneralNearestPointAlgorithm{

    double[] u, v;

    public void setLattice(Lattice G) {
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
