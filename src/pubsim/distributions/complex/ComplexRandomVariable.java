/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.complex;

import pubsim.Complex;
import pubsim.distributions.GenericRandomVariable;
import pubsim.distributions.RealRandomVariable;
import pubsim.distributions.circular.CircularRandomVariable;

/**
 *
 * @author Robby McKilliam
 */
public interface ComplexRandomVariable extends GenericRandomVariable<Complex> {
    
    public RealRandomVariable realMarginal();
    public RealRandomVariable imaginaryMarginal();
    public RealRandomVariable magnitudeMarginal();
    public CircularRandomVariable angleMarginal();
    
}
