/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import simulator.Util;
import simulator.VectorFunctions;

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

    protected AmbiguityRemover ambiguityRemover;

    public KitchenEstimator(int a) {
        this.a = a;
        ambiguityRemover = new AmbiguityRemover(a);
    }

    public void setSize(int n) {
        this.N = n;
        y = new double[n];
        p = new double[a];
    }

    public int getOrder() {
        return a;
    }

    public double[] estimate(double[] real, double[] imag) {
        if(N != real.length)
            setSize(real.length);

        for(int i = 0; i < N; i++)
            y[i] = Math.atan2(imag[i], real[i])/(2*Math.PI);

        int m = a - 1;
        for (int i = m; i >= 0; i--) {
            p[i] = estimateM(y, i);
            for (int j = 0; j < y.length; j++) {
                y[j] -= p[i]*Math.pow(j+1, i);
            }
        }
        
        return ambiguityRemover.disambiguate(p);
    }

    /** Estimate the Mth order parameter */
    protected double estimateM(double[] y, int M){

        double[] d = VectorFunctions.mthDifference(y, M);

        //System.out.println("y = " + VectorFunctions.print(y));
        //System.out.println("d = " + VectorFunctions.print(d));

        //compute the denominator
        double dprod = 1.0;
        for(int i = 0; i <= 2*M; i++){
            dprod *= d.length+i;
        }
        double Mfac = Util.factorial(M);
        double fprod = Util.factorial(2*M + 1)/( Mfac*Mfac*dprod );

        //System.out.println("fprod = " + 1/fprod);

        double aM = 0.0;
        for(int j = 0; j < d.length; j++){

            double prod = 1.0;
            for(int i = 1; i <= M; i++)
                prod *= (j + i)*(d.length - j - 1 + i);

            //System.out.println(prod);

            aM += Util.centeredFracPart(d[j])*prod;

        }

        //System.out.println();
        //System.out.println("aM = " + aM);

        return fprod * aM / Mfac;
    }


    public double[] error(double[] real, double[] imag, double[] truth) {
        double[] est = estimate(real, imag);
        double[] err = new double[est.length];

        for (int i = 0; i < err.length; i++) {
            err[i] = est[i] - truth[i];
        }
        err = ambiguityRemover.disambiguate(err);
        for (int i = 0; i < err.length; i++) {
            err[i] = err[i]*err[i];
        }
        return err;
    }

}
