/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.leech;

import pubsim.lattices.LatticeAndNearestPointAlgorithm;

/**
 * Nearest point algorithm for the Leech lattice based on the Turyn
 * construction of the Golay code. This algorithm was devised by 
 * Conway and Sloane in:
 * "Soft decoding techniques for codes and lattices, including the
 * Golay code and the Leech lattice", IEEE Trans. Info. Th., vol. 32, 1986.
 * 
 * @author Robby McKilliam
 */
public class LeechTuryn extends Leech implements LatticeAndNearestPointAlgorithm {
    
    protected static final double[][] aTable
            = { {0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0},
                {2,2,2,2,0,0,0,0},
                {-2,2,2,2,0,0,0,0},
                {2,2,0,0,2,2,0,0},
                {-2,2,0,0,2,2,0,0},
                {2,2,0,0,0,0,2,2},
                {-2,2,0,0,0,0,2,2},
                {2,0,2,0,2,0,2,0},
                {-2,0,2,0,2,0,2,0},
                {2,0,2,0,0,2,0,2},
                {-2,0,2,0,0,2,0,2},
                {2,0,0,2,2,0,0,2},
                {-2,0,0,2,2,0,0,2},
                {2,0,0,2,0,2,2,0},
                {-2,0,0,2,0,2,2,0} };
    

    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
