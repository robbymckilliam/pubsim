/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.nearset;

import Jama.Matrix;

/**
 * NearestInSet computer for affine surfaces.
 * @author Robby McKilliam
 */
public abstract class NearestToAffineSurface
    implements NearestInSet{

    protected final Matrix P;
    protected final RegionForLines R;

    public NearestToAffineSurface(Matrix P, RegionForLines R){
        this.P = P;
        this.R = R;
    }

    /**
     * Computes the nearest point and the nearest params
     * for the affine surface Pp + c with parameters p within
     * the region R. i.e p \in R.
     * @param c
     */
    public abstract void compute(double[] c);

}
