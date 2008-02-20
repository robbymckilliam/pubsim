/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import lattices.PnaEfficient;

/**
 * Runs the PnaEfficient nearest lattice point algorithm to
 * estimate a polynomial phase signal.
 * @author Robby McKilliam
 */
public class LatticeEstimator implements PolynomialPhaseEstimator{
    
    protected double[] ya, params;
    protected int n, a;
    protected PnaEfficient lattice;
    protected Matrix mat;
    
    /** 
     * You must set the polynomial order in the constructor
     * @param a = polynomial order
     */
    public LatticeEstimator(int a){
        lattice = new PnaEfficient(a);
        this.a = a;
        params = new double[a];
    }

    @Override
    public void setSize(int n) {
        lattice.setDimension(n-a);  
        ya = new double[n];
        this.n = n;
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
        
        //calculate f from the nearest point
        double f = 0, gtg = 0;
        double[] g = lattice.getg();
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++){
            f += g[i]*(ya[i]-u[i]);
            gtg += g[i]*g[i];
        }
        f /= gtg;
        
        params[params.length-1] = f - Math.round(2.0*f)/2.0;
        
        //System.out.println("f = " + f)
        return params;
    }

}
