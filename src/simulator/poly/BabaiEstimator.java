/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.poly;

import Jama.Matrix;
import lattices.GeneralLattice;
import lattices.Lattice;
import lattices.PhinaStarEfficient;
import lattices.decoder.Babai;
import lattices.decoder.GeneralNearestPointAlgorithm;
import lattices.decoder.SphereDecoder;
import simulator.VectorFunctions;
import simulator.Util;

/**
 * Uses the Babai nearest plane algorithm
 * @author Robby
 */
public class BabaiEstimator implements PolynomialPhaseEstimator {

    protected double[] ya,  p;
    protected int n,  a;
    protected PhinaStarEfficient lattice;
    protected GeneralNearestPointAlgorithm npalgorithm;
    protected Matrix M,  K, ambM;
    protected AmbiguityRemover ambiguityRemover;

    //Here for inheritance purposes.  You can't call this.
    protected BabaiEstimator() {
    }
    
    /** 
     * You must set the polynomial order in the constructor
     * @param a = polynomial order
     */
    public BabaiEstimator(int a) {
        lattice = new PhinaStarEfficient(a);
        npalgorithm = new Babai();
        this.a = a;
    }

    @Override
    public void setSize(int n) {
        lattice.setDimension(n - a);
        npalgorithm.setLattice(lattice);
        ambiguityRemover = new AmbiguityRemover(a);

        ya = new double[n];
        p = new double[a];
        this.n = n;

        M = lattice.getMMatrix();
        Matrix Mt = M.transpose();
        K = Mt.times(M).inverse().times(Mt);
        
    }

    /** 
     *  This is not complete.  I am only returning the parameter of
     *  largest order.  Need to fill the parameter array.
     */
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
        //double[] pa = ambiguityRemover.disambiguate(p);
        
        //System.out.println("est = " + VectorFunctions.print(est));
        //System.out.println("est = " + VectorFunctions.print(pa));
        
        //Round the parameters back to
        //allowable ranges.  Care needs to be taken
        //here as the parameters are not independent.
//        for(int i = a-1; i > 0; i--){
//            double val = Math.IEEEremainder(est[i], 1.0/Util.factorial(i));
//            est[i-1] -= est[i] - val;
//            est[i] = val;
//            //est[j] *= 2*Math.PI;
//        }
//        est[0] = Math.IEEEremainder(est[0], 1.0);
        
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


}
