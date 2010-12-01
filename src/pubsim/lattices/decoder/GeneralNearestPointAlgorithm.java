/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import pubsim.lattices.Lattice;
import pubsim.lattices.NearestPointAlgorithm;

/**
 * Interface for the general nearest point algorithms in the decoder package
 * @author Robby McKilliam
 */
public interface GeneralNearestPointAlgorithm 
        extends NearestPointAlgorithm {

    /**
     * Preferably set the lattice in the constructor not with setLattice.
     * @deprecated
     */
    public void setLattice(Lattice G);
    
}
