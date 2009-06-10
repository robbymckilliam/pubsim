/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import lattices.Lattice;
import lattices.NearestPointAlgorithmInterface;

/**
 * Interface for the general nearest point algorithms in the decoder package
 * @author Robby McKilliam
 */
public interface GeneralNearestPointAlgorithm 
        extends NearestPointAlgorithmInterface {

    /**@deprecated*/
    public void setLattice(Lattice G);
    
}
