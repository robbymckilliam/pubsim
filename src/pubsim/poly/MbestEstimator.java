/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.poly;

import pubsim.lattices.VnmStarSampled;
import pubsim.lattices.decoder.Mbest;

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
        lattice = new VnmStarSampled(m, 0, new int[m]);
        //npalgorithm = new MbestNoLLL(M);
        npalgorithm = new Mbest(M);
        //npalgorithm = new PolySphereDecoder();
        this.m = m;
    }
}
