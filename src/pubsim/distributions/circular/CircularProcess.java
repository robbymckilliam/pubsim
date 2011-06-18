/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.circular;

import pubsim.distributions.processes.StationaryProcess;

/**
 * @author Robby McKilliam
 */
public interface CircularProcess extends StationaryProcess {
    
    /** This is the autocorrelation required by the sample circular mean estimator */
    double[] sinusoidalAutocorrelation();
    
    /** Return the marginal distribution as a circular random variable. */
    CircularRandomVariable circularMarginal();

}
