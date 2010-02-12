/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import lattices.VnmStarGlued;
import lattices.VnmStarSampledEfficient;

/**
 *
 * @author robertm
 */
public class GluedLatticeEstimator extends BabaiEstimator{

    /**
     * You must set the polynomial order in the constructor
     * @param a = polynomial order
     */
    public GluedLatticeEstimator(int a) {
        lattice = new VnmStarSampledEfficient(a);
        this.a = a;
    }

}
