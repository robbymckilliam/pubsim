/*
 * GlueAnstarEstimator.java
 *
 * Created on 12 August 2007, 12:28
 */

package simulator.fes;

import simulator.Anstar;

/**
 * Frequency estimator that performs n^3 glues of the An* lattice to find the
 * nearest point in the frequency estimation lattice, Pn-2.  O(n^4log(n)).
 * @author Robby
 */
public class GlueAnstarEstimator extends Anstar implements FrequencyEstimator {
    
      /** Set the number of samples */
    public void setSize(int n){
        
    }
    
    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] y){
        return 0.0;
    }
    
}
