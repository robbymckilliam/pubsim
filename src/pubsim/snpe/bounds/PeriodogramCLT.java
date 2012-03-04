/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.snpe.bounds;

import pubsim.distributions.ContinuousRandomVariable;
import pubsim.distributions.circular.WrappedCircularRandomVariable;

/**
 *
 * @author Robby McKilliam
 */
public class PeriodogramCLT implements CLT {
    
    protected final double scalefac;
    protected final double dmean;
    
    
    public PeriodogramCLT(ContinuousRandomVariable noise, double discretemean, double T0) {
        
        WrappedCircularRandomVariable wrp = new WrappedCircularRandomVariable(noise);
        scalefac = 0;
        dmean = 0;
    }

    @Override
    public double phaseVar(int N) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double periodVar(int N) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double periodPhaseCoVar(int N) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
