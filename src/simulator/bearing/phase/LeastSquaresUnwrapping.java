/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing.phase;

import simulator.Complex;
import simulator.bearing.AngularlLeastSquaresEstimator;

/**
 * Phase estiamtor based on the nearest lattice point in An*.
 * Minimises the sum of square errror in the phase parameter.
 * @author Robby McKilliam
 */
public class LeastSquaresUnwrapping implements PhaseEstimator {

    private AngularlLeastSquaresEstimator ls;
    private int n;
    double[] a;

    public void setSize(int n) {
        this.n = n;
        ls = new AngularlLeastSquaresEstimator();
        ls.setSize(n);
        a = new double[n];
    }

    public double estimatePhase(Complex[] y) {
        if(n != y.length)
            setSize(y.length);

        for(int i = 0; i < n; i++)
            a[i] = y[i].phase()/(2*Math.PI);

        return ls.estimateBearing(a);
    }

}
