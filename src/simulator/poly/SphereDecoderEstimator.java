/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import lattices.PhinaStarEfficient;
import lattices.decoder.SphereDecoder;

/**
 * Runs the PhinaStarEfficient nearest lattice point algorithm to
 * estimate a polynomial phase signal.
 * @author Robby McKilliam
 */
public class SphereDecoderEstimator extends BabaiEstimator {
    
    /** 
     * You must set the polynomial order in the constructor
     * @param a = polynomial order
     */
    public SphereDecoderEstimator(int a) {
        lattice = new PhinaStarEfficient(a);
        npalgorithm = new SphereDecoder();
        //npalgorithm = new PolySphereDecoder();
        this.a = a;
    }

}