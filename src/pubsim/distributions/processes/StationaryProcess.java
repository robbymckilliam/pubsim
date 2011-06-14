/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.processes;

import pubsim.distributions.RandomVariable;

/**
 * Interface for a stationary process
 * @author Robby McKilliam
 */
public interface StationaryProcess {
    
    /** Return the random variable with the marginal pdf/cdf of this process */
    public RandomVariable marginal();
    
    public double[] autocorrelation();
    
    public double getNoise();
    
}
