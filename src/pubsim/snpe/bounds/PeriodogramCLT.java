/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.snpe.bounds;

import pubsim.distributions.ContinuousRandomVariable;

/**
 * Computes the asymptotic variance of the periodogram estimator of a sparse
 * noisy periodic signal.
 * @author Robby McKilliam
 */
public class PeriodogramCLT implements CLT {

    protected final double scalefac;

    public PeriodogramCLT(ContinuousRandomVariable noise, double discretemean, double T0) {

        double numer = 3 * T0 * T0 * (1 - noise.characteristicFunction(4 * Math.PI*T0).re());
        double chrf2pi = noise.characteristicFunction(2*Math.PI*T0).re();
        double denom = 2 * Math.PI * Math.PI * discretemean * discretemean * chrf2pi * chrf2pi;
        scalefac = numer / denom;
    }

    /** 
     * This is wrong, but is set this way for convenience.  Should really add this later
     */
    @Override
    public double phaseVar(int N) {
        return 0.0;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double periodVar(int N) {
        return scalefac/N/N/N;
    }

    @Override
    public double periodPhaseCoVar(int N) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
