/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import Jama.Matrix;
import lattices.LatticeAndNearestPointAlgorithm;
import lattices.util.IntegerVectors;
import static simulator.VectorFunctions.add;
import static simulator.VectorFunctions.times;
import static simulator.VectorFunctions.distance_between;
import static simulator.VectorFunctions.copy;
import static simulator.VectorFunctions.print;

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

    private final int N;
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

        N = a.length;

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
        double[] mean = new double[N];
        for( Matrix U : ints){
            double[] x= encode(U.getColumnPackedCopy());
            add(x, mean, mean);
        }
        times(mean, -1.0/Math.pow(r, N), mean);
        return mean;
    }

    public double[] getTranslation(){
        return a;
    }

}
