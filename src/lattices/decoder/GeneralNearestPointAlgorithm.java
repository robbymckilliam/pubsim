/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import lattices.Lattice;
import lattices.NearestPointAlgorithmInterface;

/**
 * INterface for the general nearest point algorithms in the decoder package
 * @author Robby McKilliam
 */
public interface GeneralNearestPointAlgorithm 
        extends NearestPointAlgorithmInterface {
    
    public void setLattice(Lattice G);
    
}
