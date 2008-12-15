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
    protected double tau;

    public DPTEstimator(int a){
        this.a = a;
    }


    public void setSize(int n) {
        z = new Complex[n];
        p = new double[a];
        this.n = n;

        //set the tau parameter for the PPT
        tau = ((double)n)/a;
        if(a >= 4)
            tau = ((double)n)/(a+2);

    }

    public double[] estimate(double[] real, double[] imag) {

        for(int i = 0; i < n; i++){
            z[i] = new Complex(real[i], imag[i]);
        }

        


        return p;

    }

    /**
     * Estimate the parameter of order M from x.
     * @param x : the signal
     * @param M: order of parameter to estimate
     * @return the estimated parameter
     */
    protected double estimateM(Complex[] x, int M){
        double p = 0;



        return p;
    }

    /**
     * Compute the polynomial phase transform of order m of z
     * with lag tau.
     */
    protected Complex[] PPT(int m, Complex[] y){
        Complex[] trans = y;

        for(int i = 2; i <= m; i++){
            trans = PPT2(trans);
        }

        return trans;
    }

    /**
     * Compute the second order PPT.  This is used to
     * compute the PPT of higher orders.
     * @param y
     * @return
     */
    protected Complex[] PPT2(Complex[] y){
        Complex[] trans = new Complex[y.length];
        int t = (int)Math.round(tau);

        //System.out.print("t = " + t);

        for(int i = 0; i < trans.length; i++){
            if(i - t >= 0)
                trans[i] = y[i].times(y[i-t].conjugate());
            else
                trans[i] = new Complex(0,0);
        }

        return trans;
    }

    public double[] error(double[] real, double[] imag, double[] truth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
