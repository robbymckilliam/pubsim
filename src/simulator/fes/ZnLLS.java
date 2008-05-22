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
        
        // TODO: Determine f and return it
        
        return 0.0;
    }
    
}
