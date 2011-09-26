/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import pubsim.lattices.Lattice;
import pubsim.lattices.NearestPointAlgorithm;

/**
 * Mandar's approximate nearest point algorithm
 * that climbs an approximation of the Jacobi
 * theta function. INCOMPLETE.
 * @author Robby McKilliam
 */
public class JacobiTheta implements NearestPointAlgorithm{

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
    
    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
