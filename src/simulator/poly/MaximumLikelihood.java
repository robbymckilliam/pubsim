/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

/**
 * Implements a (approximate) maximum likelihood estimator for
 * polynomial phase signals.  This samples the identifiable region
 * performing Newton's method a whole lot of times.
 * @author Robby McKilliam
 */
public class MaximumLikelihood implements PolynomialPhaseEstimator{

    int a;
    int N;

    //Here for inheritance purposes.  You can't call this.
    protected MaximumLikelihood() {
    }

    public MaximumLikelihood(int a){
        this.a = a;
    }

    public void setSize(int n) {
        N = n;
    }

    public double[] estimate(double[] real, double[] imag) {     
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] error(double[] real, double[] imag, double[] truth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
