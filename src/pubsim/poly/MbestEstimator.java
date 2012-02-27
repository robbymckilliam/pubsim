/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.poly;

import Jama.Matrix;
import pubsim.lattices.VnmStarSampled;
import pubsim.lattices.decoder.Babai;
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
    public MbestEstimator(int m, int n, int M) {
        lattice = new VnmStarSampled(m, n - m - 1, new int[m]);
        this.m = m;
        npalgorithm = new Mbest(lattice,M);
        ambiguityRemover = new AmbiguityRemover(m);

        ya = new double[n];
        p = new double[m+1];
        this.n = n;

        this.M = lattice.getMMatrix();
        Matrix Mt = this.M.transpose();
        K = Mt.times(this.M).inverse().times(Mt);
    }
}
