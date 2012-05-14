/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.complex;

import pubsim.Complex;
import pubsim.distributions.GenericRandomVariable;
import pubsim.distributions.RealRandomVariable;

/**
 *
 * @author Robby McKilliam
 */
public interface ComplexRandomVariable extends GenericRandomVariable<Complex> {
    
    RealRandomVariable realMarginal();
    RealRandomVariable imaginaryMarginal();
    RealRandomVariable magnitudeMarginal();
    RealRandomVariable phaseMarginal();    
    
}
