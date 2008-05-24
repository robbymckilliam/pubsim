/*
 * ZnLLS.java
 *
 * Created on 12 August 2007, 12:32
 */

package simulator.fes;

import lattices.NearestPointAlgorithmInterface;
import lattices.Phin2StarZnLLS;

/**
 * Use the faster O(n^3 log(n)) Pn2 nearest point algorithm that
 * is a specialisation of the ZnLLS algorithm for the period
 * estimation problem.
 * 
 * @author Tim Mason and Robby McKilliam
 */
public class ZnLLS implements FrequencyEstimator{
    
    protected double[] ya;
    protected int n;
    protected NearestPointAlgorithmInterface lattice;
    
    /** Set the number of samples */
    public void setSize(int n){
        lattice = new Phin2StarZnLLS();
        lattice.setDimension(n-2);
        ya = new double[n];
        this.n = n;
    }
    
    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }
        
        for(int i = 0; i < real.length; i++) {
            ya[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        }
        
        lattice.nearestPoint(ya);
        
        // copied directly from GlueAnstarEstimator
        double f = 0, gtg = 0;
        double meann = (n-1)/2.0;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++){
            f += (i - meann)*(ya[i]-u[i]);
            gtg += (i - meann)*(i - meann);
        }
        f /= gtg;

        //System.out.println("f = " + f);
        
        // f is normalised to fs/2, so it should be between 0 and 1
        while (f < 0) {
            f++;
        }
        while (f >= 1) {
            f--;
        }
        
        return f;
    }
    
}
