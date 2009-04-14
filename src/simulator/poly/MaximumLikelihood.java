/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import lattices.util.PointInParallelepiped;
import optimisation.AutoDerivativeFunction;
import optimisation.NewtonRaphson;
import simulator.VectorFunctions;

/**
 * Implements a (approximate) maximum likelihood estimator for
 * polynomial phase signals.  This samples the identifiable region
 * performing Newton's method a whole lot of times.
 * @author Robby McKilliam
 */
public class MaximumLikelihood implements PolynomialPhaseEstimator{

    int a;
    int N;
    int samples = 100;
    protected AmbiguityRemover ambiguityRemover;

    //Here for inheritance purposes.  You can't call this.
    protected MaximumLikelihood() {
    }
    
    /**
     * @param a : polynomail order
     */
    public MaximumLikelihood(int a){
        this.a = a;
        ambiguityRemover = new AmbiguityRemover(a);
    }

    /**
     * @param a : polynomail order
     * @param samples : number of samples used per parameter in ML search.
     * Deafult samples = 100
     */
    public MaximumLikelihood(int a, int samples){
        this.a = a;
        this.samples = samples;
        ambiguityRemover = new AmbiguityRemover(a);
    }
    
    /**
     * @param samples : number of samples used per parameter in ML search.
     */
    public void setSamples(int samples){
        this.samples = samples;
    }

    @Override
    public void setSize(int n) {
        N = n;
    }

    public int getOrder() {
        return a;
    }

    public double[] estimate(double[] real, double[] imag) {
        if (N != real.length) {
            setSize(real.length);
        }

        PolynomialPhaseLikelihood func
                = new PolynomialPhaseLikelihood(real, imag);
        NewtonRaphson newtonRaphson
                = new NewtonRaphson(func);

        //System.out.println(ambiguityRemover.getBasisMatrix()==null);

        PointInParallelepiped points
                = new PointInParallelepiped(ambiguityRemover.getBasisMatrix(),
                                            samples);

        Matrix p = null;
        double D = Double.NEGATIVE_INFINITY;
        while(points.hasMoreElements()){
            //Matrix pt = newtonRaphson.maximise(points.nextElement());
            //System.out.println("here");
            Matrix pt = points.nextElement();
            double dist = func.value(pt);
            if(dist > D){
                D = dist;
                p = pt.copy();
                //System.out.println(VectorFunctions.print(pt));
            }
        }
        //double[] parray = {0.1,0.1};
        //double dist = func.value(VectorFunctions.columnMatrix(parray));
        //p = VectorFunctions.columnMatrix(parray);
        p = newtonRaphson.maximise(p);
        //System.out.println(dist);
        //System.out.println(D);
        //System.out.println(VectorFunctions.print(p));
        return ambiguityRemover.disambiguate(VectorFunctions.unpackRowise(p));
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

    public static class PolynomialPhaseLikelihood extends AutoDerivativeFunction {

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
            interval = 1e-6;
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
            return -val;
        }

//        public Matrix hessian(Matrix x) {
//            throw new UnsupportedOperationException("Not supported yet.");
//        }
//
//        public Matrix gradient(Matrix x) {
//            int M = x.getRowDimension();
//            Matrix g = new Matrix(N, 1);
//            for(int i = 0; i < N; i++){
//                double val = 0.0;
//                for(int n = 0; n < N; n++){
//                    double phase = 0.0;
//                    for(int m = 0; m < M; m++){
//                        double p = x.get(m, 0);
//                        phase += p * Math.pow(n+1, m);
//                    }
//                    double real = Math.cos(2*Math.PI*phase);
//                    double imag = Math.sin(2*Math.PI*phase);
//                    val += (yr[n] - real);
//                    val += (yi[n] - imag);
//                }
//            }
//            return g;
//        }

    }


}
