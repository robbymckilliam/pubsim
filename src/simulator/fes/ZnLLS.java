/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes;

import lattices.Phin2star.Phin2StarZnLLS;

/**
 * O(N^3log(N)) version of the LSPU estimator.
 * @author Robby McKilliam
 */
public class ZnLLS extends LatticeEstimator implements FrequencyEstimator{

    public ZnLLS(){
        lattice = new Phin2StarZnLLS();
    }

}
