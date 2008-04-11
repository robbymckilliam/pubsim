/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import lattices.PhinaEfficient;
import simulator.VectorFunctions;

/**
 * Runs the PhinaEfficient nearest lattice point algorithm to
 * estimate a polynomial phase signal.
 * @author Robby McKilliam
 */
public class LatticeEstimator implements PolynomialPhaseEstimator{
    
    protected double[] ya, gparams;
    protected int n, a;
    protected PhinaEfficient lattice;
    protected Matrix mat;
    
    /** 
     * You must set the polynomial order in the constructor
     * @param a = polynomial order
     */
    public LatticeEstimator(int a){
        lattice = new PhinaEfficient(a);
        this.a = a;
    }

    @Override
    public void setSize(int n) {
        lattice.setDimension(n-a);  
        ya = new double[n];
        gparams = new double[a];
        this.n = n;
        
        //setup matricies for psuedo inverse
        mat = new Matrix(n, a);
        Matrix gmat = new Matrix(n, a);
        for(int i = 0; i < a; i++){
            double[] g = lattice.getg(i+1);
            for(int j = 0; j < n; j++){
                mat.set(j, i, Math.pow((j+1), i));
                gmat.set(j, i, g[j]);
            }
        }     
        mat = mat.inverse().times(gmat);
        //System.out.println(VectorFunctions.print(mat));
        
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
        
        //calculate the scalars for the orthogonal g's
       for(int ap = a; ap > 0; ap--){
            gparams[ap-1] = 0;
            double gtg = 0;
            double[] g = lattice.getg(ap);
            double[] u = lattice.getIndex();
            for(int i = 0; i < n; i++){
                gparams[ap-1] += g[i]*(ya[i]-u[i]);
                gtg += g[i]*g[i];
            }
            gparams[ap-1] /= gtg;
        }
        
        Matrix gp = new Matrix(gparams, a);
        
        Matrix params = mat.times(gp);
        
        double[] p = params.getColumnPackedCopy();
        
        for(int i = 0; i < a; i++)
            p[i] -= Math.round(p[i]);
        
        //System.out.println("f = " + f)
        return p;
    }

}