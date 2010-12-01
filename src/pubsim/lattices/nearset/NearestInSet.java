/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.nearset;

/**
 *
 * @author Robby McKilliam
 */
interface NearestInSet {

    /** Return the nearest point in the near set */
    public double[] nearestPoint();

    /** Return the nearest parameters in the near set */
    public double[] nearestParams();

}
