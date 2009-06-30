/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import java.util.Enumeration;

/**
 * Interface for generating points (ie Matrix of double[]).
 * @author Robby McKilliam
 */
public interface PointEnumerator extends Enumeration<Matrix>, Iterable<Matrix>{

    /**
     * @return return the next element as a double[] rather than a Matrix
     */
    double[] nextElementDouble();

    double percentageComplete();



}
