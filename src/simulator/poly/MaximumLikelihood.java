/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import optimisation.FunctionAndDerivatives;

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

    public static class PolynomialPhaseLikelihood implements FunctionAndDerivatives{

        double[] yr, yi;
        int N;

        protected PolynomialPhaseLikelihood(){}

        /**
         * @param y  is the recieved signal
         */
        public PolynomialPhaseLikelihood(double[] yr, double[] yi){
            this.yr = yr;
            this.yi = yi;
            N = yr.length;
        }

        /**
         * x is a column vector containing the polynomial phase
         * parameter.  x = [p0, p1, p2, ... ]
         * @return Value of the likelihood function for these parameters
         */
        public double value(Matrix x) {
            int M = x.getRowDimension();
            double val = 0.0;
            for(int n = 0; n < N; n++){
                double phase = 0.0;
                for(int m = 0; m < M; m++){
                    double p = x.get(m, 0);
                    phase += p * Math.pow(n+1, m);
                }
                double real = Math.cos(2*Math.PI*phase);
                double imag = Math.sin(2*Math.PI*phase);
                val += (yr[n] - real)*(yr[n] - real);
                val += (yi[n] - imag)*(yi[n] - imag);
            }
            return val;
        }

        public Matrix hessian(Matrix x) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Matrix gradient(Matrix x) {
            int M = x.getRowDimension();
            Matrix g = new Matrix(N, 1);
            for(int i = 0; i < N; i++){
                double val = 0.0;
                for(int n = 0; n < N; n++){
                    double phase = 0.0;
                    for(int m = 0; m < M; m++){
                        double p = x.get(m, 0);
                        phase += p * Math.pow(n+1, m);
                    }
                    double real = Math.cos(2*Math.PI*phase);
                    double imag = Math.sin(2*Math.PI*phase);
                    val += (yr[n] - real);
                    val += (yi[n] - imag);
                }
            }
            return g;
        }

    }

}
