/*
 * ZnLLS.java
 *
 * Created on 12 August 2007, 12:32
 */

package simulator.fes;

import simulator.Pn2;

/**
 * Use the faster O(n^3 log(n)) Pn2 nearest point algorithm that
 * is a specialisation of the ZnLLS algorithm for the period
 * estimation problem.
 * 
 * @author Tim Mason and Robby McKilliam
 */
public class ZnLLS extends Pn2 implements FrequencyEstimator{
    
    /** Set the number of samples */
    public void setSize(int n){
        
    }
    
    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag){
        return 0.0;
    }
    
}
