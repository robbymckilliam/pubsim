/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import lattices.NearestPointAlgorithm;

/**
 * Interface for nearest point algorithms with constraints, i.e. when
 * some elements of the index vector can be forced to particular values.
 * @author Robby McKilliam
 */
public interface ConstrainedNearestPointAlgorithm 
        extends NearestPointAlgorithm{

    /**
     * Set the constraints.  Elements in c that are null are
     * unconstrained.
     * @param c the constraints.
     */
    void setConstraints(Double[] c);

    /**
     * Run this nearest point algorithm with D as square radius squared 
     */
    void nearestPoint(double[] y, double D);

}
