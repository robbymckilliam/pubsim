/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing;

/**
 * Assumes that angles are measure in interval [-1/2, 1/2).
 * @author Robby McKilliam
 */
public class VectorMeanEstimator implements BearingEstimator {

    public void setSize(int n) {
        
    }

    public double estimateBearing(double[] y) {
        
        double csum = 0.0, ssum = 0.0;
        double twopi = 2.0 * Math.PI;
        for(int i = 0; i < y.length; i++){
            csum += Math.cos(twopi*y[i]);
            ssum += Math.sin(twopi*y[i]);
        }
        
        return Math.atan2(ssum, csum)/twopi;
                
    }

}
