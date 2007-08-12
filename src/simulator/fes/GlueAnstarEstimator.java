/*
 * GlueAnstarEstimator.java
 *
 * Created on 12 August 2007, 12:28
 */

package simulator.fes;

import simulator.PnoneGlued;

/**
 * Frequency estimator that uses Pn1 glue vector algorithm to solve the nearest
 * point problem for the frequency estimation lattice Pn1.  O(n^4log(n)).
 * @author Robby
 */
public class GlueAnstarEstimator extends PnoneGlued implements FrequencyEstimator {
    
    protected double[] x;
    
    /** Set the number of samples */
    public void setSize(int n){
        setDimension(n-2);  
        x = new double[n];
    }
    
    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] y){
        if(n+2 != y.length)
            setSize(y.length);
        
        project(y,x);
        nearestPoint(x);
        
        //calculate f from the nearest point
        double f = 0;
        
        return f;
        
    }
    
}
