/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import lattices.util.PointEnumerator;
import simulator.Point2;

/**
 * A brute force, test lots of points and run Newton's
 * method approach.  This is not particularly interesting.
 * @author Robby McKilliam
 */
public class BruteLocationEstimator implements PhaseBasedLocationEstimator{

    PointEnumerator points;
    Transmitter[] trans;

    public BruteLocationEstimator(Transmitter[] trans, PointEnumerator points){
        this.points = points;
        this.trans = trans;
    }

    public Point2 computeLocation(double[] d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point2 getLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
