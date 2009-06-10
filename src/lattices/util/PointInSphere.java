/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import lattices.Lattice;

/**
 *  Returns all the points of a lattice in a sphere of given
 * radius.  The sphere does not have to be centered at
 * the origin.
 * @author Robby McKilliam
 */
public class PointInSphere implements PointEnumerator{

    Lattice L;
    double[] y;

    public double[] nextElementDouble() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double percentageComplete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasMoreElements() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Matrix nextElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
