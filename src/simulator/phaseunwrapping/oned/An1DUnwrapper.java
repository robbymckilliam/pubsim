/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.oned;

import lattices.An.An;
import lattices.An.AnFastSelect;
import lattices.decoder.GeneralNearestPointAlgorithm;
import simulator.VectorFunctions;

/**
 * Simple 0th order 1D unwrapper that reduces to finding
 * the nearest point in the lattice An.
 *
 * This needs some thinking the lattice involved is not actually
 * An but a scewed version of it.  Still could ne a fast nearest
 * point algorithm for this lattice and it's probably only a small
 * modification of the algorithm for An.
 * @author Robby McKilliam
 */
public class An1DUnwrapper implements OneDUnwrapperInterface{

    An lattice;
    int N;

    public double[] unwrap(double[] y) {
        if(N != y.length) setSize(y.length);
        double[] By = VectorFunctions.matrixMultVector(
                                    lattice.getGeneratorMatrixBig(), y);
        lattice.nearestPoint(By);
        return lattice.getIndex();
    }

    public void setSize(int N) {
        this.N = N;
        lattice = new AnFastSelect(N-1);
    }

}
