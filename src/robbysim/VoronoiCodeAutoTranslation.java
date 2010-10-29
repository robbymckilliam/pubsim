/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim;

import Jama.Matrix;
import robbysim.lattices.LatticeAndNearestPointAlgorithm;
import robbysim.lattices.util.IntegerVectors;
import static robbysim.VectorFunctions.add;
import static robbysim.VectorFunctions.times;
import static robbysim.VectorFunctions.distance_between;
import static robbysim.VectorFunctions.copy;
import static robbysim.VectorFunctions.print;

/**
 * This implements Conway and Sloane's iterative scheme for finding
 * good translations for Vornoi codes. It is not garauteed to
 * be optimal but it does a reasonable job.
 *
 * You can use this instead of setting a specific translation.
 *
 * @author Robby McKilliam
 */
public class VoronoiCodeAutoTranslation
        extends VoronoiCode {

    private final double tol = 0.00001;

    //you should probably leave this as a prime to try to avoid
    //the origin being selected when cycling occurs.
    private final int MAX_ITER = 11;

    public VoronoiCodeAutoTranslation(
            LatticeAndNearestPointAlgorithm lattice, int scale) {
        super(lattice, scale);
        //small translation of a so that Nearest point algorithm
        //wont be ambiguous.  It shouldn't anyway, this is just
        //procautionary.
        a[0] += 0.000001;

        //iterate
        double[] anext = computeMean();
        double itercount = 0;
        while(distance_between(a, anext) > tol && itercount < MAX_ITER){
            copy(anext, a);
            anext = computeMean();
            itercount++;
        }
    }

    protected double[] computeMean(){
        IntegerVectors ints = new IntegerVectors(N, r);
        double[] mean = new double[M];
        for( Matrix U : ints){
            double[] x= encode(U.getColumnPackedCopy());
            add(x, mean, mean);
        }
        times(mean, -1.0/Math.pow(r, N), mean);
        return mean;
    }

}
