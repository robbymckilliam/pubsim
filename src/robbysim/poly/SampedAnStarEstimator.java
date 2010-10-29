/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.poly;

import Jama.Matrix;
import robbysim.lattices.VnmStarSampled;
import robbysim.VectorFunctions;

/**
 * Estimator that just samples along the legendre polynomials solving
 * the nearest point in An* along the way.
 * @author Robby McKilliam
 */
public class SampedAnStarEstimator implements PolynomialPhaseEstimator {

    protected double[] ya,  p;
    protected int n,  m;
    protected VnmStarSampled lattice;
    int[] samples;
    protected Matrix M,  K, ambM;
    protected AmbiguityRemover ambiguityRemover;

    //Here for inheritance purposes.  You can't call this.
    protected SampedAnStarEstimator() {
    }

    /**
     * You must set the polynomial order in the constructor
     * @param m = polynomial order
     */
    public SampedAnStarEstimator(int m, int[] samples) {
        this.samples = samples;
        this.m = m;
    }

    @Override
    public void setSize(int n) {
        lattice = new VnmStarSampled(m, n - m - 1, samples);
        ambiguityRemover = new AmbiguityRemover(m);

        ya = new double[n];
        p = new double[m+1];
        this.n = n;

        M = lattice.getMMatrix();
        Matrix Mt = M.transpose();
        K = Mt.times(M).inverse().times(Mt);

    }

    public int getOrder() {
        return m;
    }

    @Override
    public double[] estimate(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }
        for (int i = 0; i < real.length; i++) {
            ya[i] = Math.atan2(imag[i], real[i]) / (2 * Math.PI);
        }
        lattice.nearestPoint(ya);
        double[] u = lattice.getIndex();

        double[] ymu = new double[ya.length];
        for (int i = 0; i < u.length; i++) {
            ymu[i] = ya[i] - u[i];
        }
        for (int i = u.length; i < ya.length; i++) {
            ymu[i] = ya[i];
        }

        //compute the parameters
        VectorFunctions.matrixMultVector(K, ymu, p);

        return ambiguityRemover.disambiguate(p);
    }

    /**
     * Run the estimator and return the square error wrapped modulo the the ambiguity
     * region between the estimate and the truth.
     */
    public double[] error(double[] real, double[] imag, double[] truth){

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
