/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattice.util.region;

/**
 * Bounding box.
 * @author Robby McKilliam
 */
public interface BoundingBox {

    /** Get minimum value of the nth coordinate. */
    public double minInCoordinate(int n);

    /** Get maximum value of the nth coordinate. */
    public double maxInCoordinate(int n);

}
