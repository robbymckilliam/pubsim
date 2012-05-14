/*
 * ContinuousRandomVariable.java
 *
 * Created on 13 April 2007, 15:08
 */

package pubsim.distributions;

import pubsim.Complex;
import pubsim.distributions.circular.CircularRandomVariable;

/**
 * Interface for the generation of noise with
 * different distributions.
 * @author Robby McKilliam
 */
public interface RealRandomVariable extends GenericRandomVariable<Double> {

    /** 
     * Return the circular random variable that results from wrapping
     * this random variable modulo 1 into [-1/2, 1/2).
     * @return 
     */
    public CircularRandomVariable getWrapped();
    
    /**
     * Return the characteristic function of this random variable E[exp(itX)] where X is
     * this random variable
     */
    public Complex characteristicFunction(Double t);
    
}
