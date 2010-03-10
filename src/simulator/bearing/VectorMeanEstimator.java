/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing;

/**
 *
 * @author Robby McKilliam
 */
public class VectorMeanEstimator implements BearingEstimator {

    public void setSize(int n) {
        
    }

    public double estimateBearing(double[] y) {
        
        double csum = 0.0, ssum = 0.0;
        for(int i = 0; i < y.length; i++){
            csum += Math.cos(y[i]);
            ssum += Math.sin(y[i]);
        }
        
        return Math.atan2(ssum, csum);
                
    }

}
