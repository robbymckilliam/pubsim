/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.processes;

import Jama.Matrix;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import pubsim.distributions.RandomVariable;
import pubsim.optimisation.AutoIntegralFunction;

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
        return y;
    }

    @Override
    public double[] autocorrelation() {
        double Xvar = X.autocorrelation()[0];
        final double ir = 10*Math.sqrt(Xvar); //range to compute integral over
        
        
        //compute the variance term ie. ac[0]
        ac[0] = (new Integration(new IntegralFunction() {
                        public double function(double x) {
                            return y.icdf(g.cdf(x)) * y.icdf(g.cdf(x)) * X.marginal().pdf(x);
                        }
                    }, -ir, ir)).gaussQuad(200);
 
        //compute all teh convariance terms
        double[] min = {-ir,-ir}; double[] max = {ir,ir};
        for(int k = 1; k < ac.length; k++){
            final int kf = k;
            ac[k] = new AutoIntegralFunction(200) {
                public double value(Matrix mat) {
                    double x1 = mat.get(0,0); 
                    double xk = mat.get(1,0);
                    return y.icdf(g.cdf(x1)) * y.icdf(g.cdf(xk)) * X.bivariatePdf(kf, x1, xk);
                }
            }.integral(min, max);
        }
        return ac;
    }
    
    public double[] autocorrelation(int intsteps) {
        double Xvar = X.autocorrelation()[0];
        final double ir = 10*Math.sqrt(Xvar); //range to compute integral over
        
        
        //compute the variance term ie. ac[0]
        ac[0] = (new Integration(new IntegralFunction() {
                        public double function(double x) {
                            return y.icdf(g.cdf(x)) * y.icdf(g.cdf(x)) * X.marginal().pdf(x);
                        }
                    }, -ir, ir)).gaussQuad(intsteps);
 
        //compute all teh convariance terms
        double[] min = {-ir,-ir}; double[] max = {ir,ir};
        for(int k = 1; k < ac.length; k++){
            final int kf = k;
            ac[k] = new AutoIntegralFunction(intsteps) {
                public double value(Matrix mat) {
                    double x1 = mat.get(0,0); 
                    double xk = mat.get(1,0);
                    return y.icdf(g.cdf(x1)) * y.icdf(g.cdf(xk)) * X.bivariatePdf(kf, x1, xk);
                }
            }.integral(min, max);
        }
        return ac;
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
