/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing;

/**
 *
 * @author harprobey
 */
public class MockMeanEstimator implements BearingEstimator {

    public void setSize(int n) {

    }

    public double estimateBearing(double[] y) {

        double sum = 0;
        for(int i = 0; i < y.length; i++){
            sum += y[i];
        }
        return sum/y.length;

    }

}

