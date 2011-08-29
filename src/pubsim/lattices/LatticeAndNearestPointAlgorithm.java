/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

/**
 *
 * @author Robby McKilliam
 */
public abstract class LatticeAndNearestPointAlgorithm extends AbstractLattice
    implements NearestPointAlgorithm {
    
    @Override
    public double distance() {throw new UnsupportedOperationException();}

}
