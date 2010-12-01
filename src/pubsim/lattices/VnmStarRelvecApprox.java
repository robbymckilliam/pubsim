/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

/**
 * This is an approximate decoder based on  moving through relavant vectors
 * in the lattice.  It doesn't garauntee that the best relavent vector is
 * found but does operate in O(nlogn) operations.
 * @author Robby McKilliam
 */
public class VnmStarRelvecApprox extends VnmStar {



    public void setDimension(int n) {
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
