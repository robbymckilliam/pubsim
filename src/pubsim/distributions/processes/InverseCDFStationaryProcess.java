/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.processes;

import pubsim.distributions.GaussianNoise;
import pubsim.distributions.RandomVariable;

/**
 * This implements a stationary process with arbitrary pdf using a Gaussian process.  You can
 * set the correlation of the underlying Gaussian process but this doesn't let you control 
 * the correlation of the output process that is not Gaussian.
 * @author Robby McKilliam
 */
public class InverseCDFStationaryProcess implements StationaryProcess {
    
    protected final ColouredGaussianNoise X;
    protected final RandomVariable g, y;
    protected final double[] ac;
    
    protected InverseCDFStationaryProcess(){ X = null; g = null; y = null; ac = null;}
    
    public InverseCDFStationaryProcess(RandomVariable rv, double[] filter){
       X = new ColouredGaussianNoise(filter);
       g = X.marginal();
       y = rv;
       ac = new double[filter.length];
    }

    @Override
    public RandomVariable marginal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] autocorrelation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getNoise() {
        return y.icdf(g.cdf(X.getNoise()));
    }

    @Override
    public void randomSeed() {
        X.randomSeed();
    }

    @Override
    public void setSeed(long seed) {
        X.setSeed(seed);
    }
    
}
