/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.optimisation;

import Jama.Matrix;

/**
 *
 * @author Robby McKilliam
 */
public interface MultivariateFunction {

    /** Return the value of the function */
    double value(Matrix x);

}
