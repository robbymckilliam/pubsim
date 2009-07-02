/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;

/**
 * NearestInSet computer for affine surfaces.
 * @author Robby McKilliam
 */
public interface NearestToAffineSurface 
    extends NearestInSet{

    /**
     * Computes the nearest point and the nearest params
     * for the affine surface Pp + c with parameters p within
     * the region R. i.e p \in R.
     * @param c
     * @param P
     * @param R
     */
    public void compute(double[] c, Matrix P, RegionForLines R);

}
