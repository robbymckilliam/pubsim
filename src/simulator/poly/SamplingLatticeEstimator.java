/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import lattices.PhinaStar;
import lattices.PhinaStarEfficient;
import simulator.VectorFunctions;
import simulator.Util;

/**
 * Runs the PhinaStarEfficient nearest lattice point algorithm to
 * estimate a polynomial phase signal.
 * This uses a sampling type nearest point algorithm.  It is very slow.
 * @author Robby McKilliam
 */
public class SamplingLatticeEstimator implements PolynomialPhaseEstimator{
    
    protected double[] ya, gparams;
    protected int n, a;
    protected PhinaStarEfficient lattice;
    protected Matrix M, K;
    
    /** 
     * You must set the polynomial order in the constructor
     * @param a = polynomial order
     */
    public SamplingLatticeEstimator(int a){
        lattice = new PhinaStarEfficient(a);
        this.a = a;
    }

    @Override
    public void setSize(int n) {
        lattice.setDimension(n-a);  
        ya = new double[n];
        gparams = new double[a];
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
        if(n != real.length)
            setSize(real.length);
        
        for(int i = 0; i < real.length; i++)
            ya[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        
        lattice.nearestPoint(ya);
        double[] u = lattice.getIndex();
        
        Matrix ymu = new Matrix(ya.length, 1);
        for (int i = 0; i < ya.length; i++) {
            ymu.set(i, 0, ya[i] - u[i]);
        }

        Matrix params = K.times(ymu);
        
        double[] p = params.getColumnPackedCopy();
        
        for(int i = 0; i < a; i++){
            p[i] = Math.IEEEremainder(p[i], 1.0/Util.factorial(i));
            //p[i] *= 2*Math.PI;
        }
        
        return p;
    }

}