/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import simulator.Complex;

/**
 * Implementation of the estimator based on iteratively maximising
 * the Discrete Polynomial Transform.  See Peleg and Friedlander,
 * Trans. Sig. Proc. Vol 43 August 1995.
 * @author Robby McKilliam
 */
public class DPTEstimator implements PolynomialPhaseEstimator{

    protected Complex[] z;
    protected double[] p;
    protected int a, n;

    public DPTEstimator(int a){
        this.a = a;
    }


    public void setSize(int n) {
        z = new Complex[n];
        p = new double[a];
        this.n = n;
    }

    public double[] estimate(double[] real, double[] imag) {

        for(int i = 0; i < n; i++){
            z[i] = new Complex(real[i], imag[i]);
        }

        


        return p;

    }

    /**
     * Compute the polynomial phase transform of order m of z
     * with lag tor.
     */
    protected Complex[] PPT(int m, int tor, Complex[] y){
        Complex[] trans = new Complex[y.length];

        double prod = 1;
        for(int i = 0; i < a; i++){
            
        }

        return trans;
    }


}
