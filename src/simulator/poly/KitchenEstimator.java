/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import simulator.Util;

/**
 * Implementation of Kitchen's polynomial phase estimate.
 *
 * J. Kitchen, "A method for estimating the coefficients of a polynomial
 * phase signal", Signal Processing, vol 37, 1994.
 * 
 * This is essentially a generalisation of Kay's frequency estimator.
 * @author Robby McKilliam
 */
public class KitchenEstimator implements PolynomialPhaseEstimator{

    protected double[] y, p;
    protected int a, N;

    public KitchenEstimator(int a){
        this.a = a;
    }


    public void setSize(int n) {
        this.N = n;
        y = new double[n];
        p = new double[a];
    }

    public double[] estimate(double[] real, double[] imag) {
        if(N != real.length)
            setSize(real.length);

        for(int i = 0; i < N; i++)
            y[i] = Math.atan2(imag[i], real[i]);

        int m = a-1;
        p[m] = estimateM(y, m);

        return p;
    }

    /** Estimate the Mth order parameter */
    protected double estimateM(double[] y, int M){

        //compute the denominator
        double dprod = 1.0;
        for(int i = 0; i <= 2*M; i++){
            dprod *= N+i;
        }
        double Mfac = Util.factorial(M);
        double fprod = Util.factorial(2*M + 1)/Mfac/Mfac / dprod;

        System.out.println("fprod = " + fprod);

        double aM = 0.0;
        for(int j = 0; j < N; j++){

            double prod = 1.0;
            for(int i = 1; i <= M; i++)
                prod *= (j + i - 1)*(N - j + 1);

            aM += y[j]*prod;

        }

        return fprod * aM /2.0/Math.PI;
    }

    public double[] error(double[] real, double[] imag, double[] truth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
