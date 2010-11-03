/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes;

import robbysim.lattices.Vn2Star.Vn2StarZnLLS;

/**
 * O(N^3log(N)) version of the LSPU estimator.
 * @author Robby McKilliam
 */
public class ZnLLS extends LatticeEstimator implements FrequencyEstimator{

    public ZnLLS(int N){
        super(N);
        lattice = new Vn2StarZnLLS(N-2);
    }

}
