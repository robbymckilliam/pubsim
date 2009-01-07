/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

/**
 * Linear nearest point algorithm for the coxeter lattices.  Implementation
 * mimics the version of the code that ended up in the final paper.  Otherwise
 * this is very similar to AnmBucket.
 *
 * UNDER CONSTRUCTION.
 *
 * @author Robby McKilliam
 */
public class AnmLinear extends AnmSorted implements NearestPointAlgorithmInterface {

    /** Constructor can set the m part of A_{n/m}. */
    public AnmLinear(int M){
        super(M);
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length-1)
	    setDimension(y.length-1);

        throw new UnsupportedOperationException("Not supported yet.");
    }


}
