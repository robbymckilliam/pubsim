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
    
    protected double[] ya, p;
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
        p = new double[a];
        this.n = n;
        
        M = lattice.getMMatrix();
        Matrix Mt = M.transpose();
        K = Mt.times(M).inverse().times(Mt);
        
    }
   

    @Override
    public double[] estimate(double[] real, double[] imag) {
        if(n != real.length)
            setSize(real.length);
        
        for(int i = 0; i < real.length; i++)
            ya[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        
        lattice.nearestPoint(ya);
        double[] u = lattice.getIndex();
        
        double[] ymu = new double[ya.length];
        for (int i = 0; i < ya.length; i++) {
            ymu[i] = ya[i] - u[i];
        }
        
        //compute the parameters
        VectorFunctions.matrixMultVector(K, ymu, p); 
        
        
        //System.out.println("p = " + VectorFunctions.print(p));
        
        //Round the parameters back to
        //allowable ranges.  Care needs to be taken
        //here as the parameters are not independent.
        for(int i = a-1; i > 0; i--){
            double val = Math.IEEEremainder(p[i], 1.0/Util.factorial(i));
            p[i-1] -= p[i] - val;
            p[i] = val;
            //p[j] *= 2*Math.PI;
        }
        p[0] = Math.IEEEremainder(p[0], 1.0);
        
        return p;
    }

    public double[] error(double[] real, double[] imag, double[] truth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}