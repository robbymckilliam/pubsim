/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.poly;

import Jama.Matrix;
import lattices.VnmStar;
import lattices.VnmStarGlued;
import lattices.decoder.Babai;
import lattices.decoder.GeneralNearestPointAlgorithm;
import simulator.VectorFunctions;

/**
 * Uses the Babai nearest plane algorithm
 * @author Robby
 */
public class BabaiEstimator implements PolynomialPhaseEstimator {

    protected double[] ya,  p;
    protected int n,  m;
    protected VnmStar lattice;
    protected GeneralNearestPointAlgorithm npalgorithm;
    protected Matrix M,  K, ambM;
    protected AmbiguityRemover ambiguityRemover;

    //Here for inheritance purposes.  You can't call this.
    protected BabaiEstimator() {
    }
    
    /** 
     * You must set the polynomial order in the constructor
     * @param m = polynomial order
     */
    public BabaiEstimator(int m) {
        lattice = new VnmStarGlued(m);
        npalgorithm = new Babai();
        this.m = m;
    }

    @Override
    public void setSize(int n) {
        lattice.setDimension(n - m - 1);
        npalgorithm.setLattice(lattice);
        ambiguityRemover = new AmbiguityRemover(m);

        ya = new double[n];
        p = new double[m+1];
        this.n = n;

        M = lattice.getMMatrix();
        Matrix Mt = M.transpose();
        K = Mt.times(M).inverse().times(Mt);
        
    }

    @Override
    public double[] estimate(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }
        for (int i = 0; i < real.length; i++) {
            ya[i] = Math.atan2(imag[i], real[i]) / (2 * Math.PI);
        }
        npalgorithm.nearestPoint(ya);
        double[] u = npalgorithm.getIndex();

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
     * Run the estimator and return the Returns the square error wrapped modulo the the ambiguity
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

    public int getOrder() {
        return m;
    }


}
