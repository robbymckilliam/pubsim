/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.pes;

import robbysim.lattices.Anstar.Anstar;
import robbysim.lattices.Anstar.AnstarBucketVaughan;
import robbysim.lattices.LatticeAndNearestPointAlgorithm;

/**
 * Adds ability to estimate the phase to a PRIEstimator.  Slightly
 * changes the interface.  Call estimate(.,.,.), then getPeriod(.),
 * getPhase(.) etc.
 * @author Robby McKilliam
 */
public abstract class AbstractPhaseAndPeriodEstimator implements PRIEstimator {

    protected double period;
    protected double phase;

    protected final LatticeAndNearestPointAlgorithm lattice
                                    = new AnstarBucketVaughan();
    protected double[] d;
    protected double[] fy;


    public void estimate(double[] y, double fmin, double fmax){
        int N = y.length;
        if(lattice.getDimension() != N-1){
            lattice.setDimension(N-1);
            d = new double[N];
            fy = new double[N];
        }

        System.arraycopy(y, 0, d, 0, N);
        double f = estimateFreq(d, fmin, fmax);
        period = 1.0/f;

        for(int i = 0; i < N; i++) fy[i] = f*y[i];
        Anstar.project(fy, d);
        
        lattice.nearestPoint(d);
        double[] s = lattice.getIndex();

        double sum = 0;
        for(int n = 0; n < N; n++){
            sum += y[n] - period*s[n];
        }

        double unwp = (sum/N)/period;

        phase = (unwp - Math.round(unwp))*period;
        
    }

    public double getPhase() {
        return phase;
    }

    public double getFrequency() {
        return 1.0/period;
    }

    public double getPeriod() {
        return period;
    }

}
