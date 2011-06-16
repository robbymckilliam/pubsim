/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.optimisation;

import Jama.Matrix;

/**
 * A multivariate function with a method for computing its integral.
 * @author Robby McKilliam
 */
public interface FunctionAndIntegral extends MultivariateFunction{

    double value(Matrix x);
    
    /** 
     * Returns the multidimensional integral of the function between the
     * values in min and max.
     */
    double integral(double[] min, double[] max);
    
}
