/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes.crb;

import distributions.circular.ProjectedNormalDistribution;

/**
 *
 * @author robertm
 */
public class AsymptoticVariance implements BoundCalculator {

    protected int N;
    private double amplitude;
    private double v;

    /** Constructor.  The amplitude defaults to 1. */
    public AsymptoticVariance(){
        setAmplitude(1.0);
    }

    public void setN(int N) {
        this.N = N;
    }

    public void setVariance(double var) {
        v = amplitude/Math.sqrt(var);
    }

    public void setAmplitude(double amp) {
        this.amplitude = amp;
    }

    public double getBound() {
        double h = ProjectedNormalDistribution.Pdf(0.5, v);
        System.out.print(h);
        double s2 = ProjectedNormalDistribution.getWrappedVariance(v);
        return 12.0*s2/((1-h)*(1-h))/Math.pow(N,3.0);
    }

}
