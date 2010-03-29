/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import lattices.VnmStarGlued;
import lattices.decoder.Mbest;

/**
 *
 * @author Robby McKilliam
 */
public class MbestEstimator extends BabaiEstimator {

    /**
     * You must set the polynomial order in the constructor
     * @param m = polynomial order
     */
    public MbestEstimator(int m, int M) {
        lattice = new VnmStarGlued(m);
        npalgorithm = new Mbest(M);
        //npalgorithm = new PolySphereDecoder();
        this.m = m;
    }
}
