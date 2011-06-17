/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.bearing;

import pubsim.bearing.BearingEstimator;
import pubsim.distributions.circular.CircularProcess;
import pubsim.distributions.circular.CircularRandomVariable;

/**
 *
 * @author Robby McKilliam
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

    public double[] confidenceInterval(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double asymptoticVariance(CircularRandomVariable noise, int N) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double asymptoticVariance(CircularProcess noise, int N) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

