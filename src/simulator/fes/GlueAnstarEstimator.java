/*
 * GlueAnstarEstimator.java
 *
 * Created on 12 August 2007, 12:28
 */

package simulator.fes;

import lattices.LatticeAndNearestPointAlgorithm;
import lattices.NearestPointAlgorithm;
import lattices.Phin2StarGlued;

/**
 * Frequency estimator that uses Pn1 glue vector algorithm to solve the nearest
 * point problem for the frequency estimation lattice Pn1.  O(n^4log(n)).
 * @author Robby McKilliam
 */
public class GlueAnstarEstimator implements FrequencyEstimator {
    
    protected double[] ya;
    protected int n;
    protected LatticeAndNearestPointAlgorithm lattice;
    
    /** Set the number of samples */
    @Override
    public void setSize(int n){
        lattice = new Phin2StarGlued(n-2);
        ya = new double[n];
        this.n = n;
    }
    
    /**
     * Run the estimator on recieved data, @param ya
     */
    @Override
    public double estimateFreq(double[] real, double[] imag){
        if(n != real.length)
            setSize(real.length);
        
        for(int i = 0; i < real.length; i++)
            ya[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        
        lattice.nearestPoint(ya);
        
        /*
        System.out.println("ya antan = " + VectorFunctions.print(ya));
        System.out.println("v = " + VectorFunctions.print(v));
        System.out.println("u = " + VectorFunctions.print(u));
        */
        
        //calculate f from the nearest point
        /*double f = 0;
        double sumn = n*(n+1)/2;
        double sumn2 = n*(n+1)*(2*n+1)/6;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++)
            f += (n*(i+1) - sumn)*(ya[i]-u[i]);
        
        f /= (sumn2*n - sumn*sumn);
        */
        double f = 0, gtg = 0;
        double meann = (n-1)/2.0;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++){
            f += (i - meann)*(ya[i]-u[i]);
            gtg += (i - meann)*(i - meann);
        }
        f /= gtg;
        

        //System.out.println("f = " + f);
        
        return f;
        
    }
    
}
