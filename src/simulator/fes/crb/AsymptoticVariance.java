/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes.crb;

import distributions.circular.ProjectedNormalDistribution;

/**
 * Asymptotic variance of the least squares phase unwrapping
 * algorithm.
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
        v = var/(amplitude*amplitude);
    }

    public void setAmplitude(double amp) {
        this.amplitude = amp;
    }

    public double getBound() {
        ProjectedNormalDistribution dist = new ProjectedNormalDistribution(0.0, v);
        double h = dist.pdf(0.5);
        //System.out.print(h);
        double s2 = dist.unwrappedVariance();
        return 12.0*s2/((1-h)*(1-h))/Math.pow(N,3.0);
    }

}
