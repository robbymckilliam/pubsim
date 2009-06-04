/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import java.util.Enumeration;

/**
 * Interface for generating points in a Voronoi region
 * of a lattice
 * @author Robby McKilliam
 */
public interface PointInVoronoi extends Enumeration<Matrix>{

    /**
     * @return return the next element as a double[] rather than a Matrix
     */
    double[] nextElementDouble();

}
