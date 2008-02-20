/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import Jama.Matrix;
import lattices.PnaEfficient;
import simulator.VectorFunctions;

/**
 * Runs the PnaEfficient nearest lattice point algorithm to
 * estimate a polynomial phase signal.
 * @author Robby McKilliam
 */
public class LatticeEstimator implements PolynomialPhaseEstimator{
    
    protected double[] ya, params, gparams, cv;
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
        gparams = new double[a];
    }

    @Override
    public void setSize(int n) {
        lattice.setDimension(n-a);  
        ya = new double[n];
        cv = new double[n];
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
        
       /* //calculate f from the nearest point
        double f = 0, gtg = 0;
        double[] g = lattice.getg();
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++){
            f += g[i]*(ya[i]-u[i]);
            gtg += g[i]*g[i];
        }
        f /= gtg;
        
        params[params.length-1] = f - Math.round(2*f)/2;
        */
        //calculate the scalars for the orthogonal g's
       for(int ap = a; ap > 0; ap--){
            gparams[ap-1] = 0;
            double gtg = 0;
            double[] g = lattice.getg(ap);
            double[] u = lattice.getIndex();
            System.out.println(VectorFunctions.print(g));
            for(int i = 0; i < n; i++){
                gparams[ap-1] += g[i]*(ya[i]-u[i]);
                gtg += g[i]*g[i];
            }
            gparams[ap-1] /= gtg;
        }
        
        //params[a-1] = gparams[a-1] - Math.round(2*gparams[a-1])/2;
        
        
        //calculate the scalars for the n's.  These scalars
        //will be our parameters.
        params[a-1] = gparams[a-1] - Math.round(2*gparams[a-1])/2; 
        for(int ap = a-1; ap > 0; ap--){
            
            for(int i = 0; i < n; i++)
                cv[i] += params[ap]*Math.pow(i, ap);
            
            System.out.println(VectorFunctions.print(cv));
            
            double gn = 0, nn = 0;
            double[] g = lattice.getg(ap);
            for(int i = 0; i < n; i++){
                gn += Math.pow(i, ap-1)*( g[i]*gparams[ap-1] - cv[i] );
                nn += Math.pow(i, ap-1)*Math.pow(i, ap-1);
            }
            
            System.out.println("gn = " + gn + ", nn = " + nn + ", ap = " + ap);
            
            params[ap-1] = gn/nn - Math.round(gn/nn);
        }
        
        
        //System.out.println("f = " + f)
        return params;
    }

}
