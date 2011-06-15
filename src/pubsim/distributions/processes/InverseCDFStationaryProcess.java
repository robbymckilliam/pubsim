/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.processes;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import pubsim.VectorFunctions;
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
        double Xvar = X.autocorrelation()[0];
        final double ir = 20*Math.sqrt(Xvar); //range to compute integral over
        
        //compute the variance term ie. ac[0]
        ac[0] = (new Integration(new IntegralFunction() {
                        public double function(double x) {
                            return y.icdf(g.cdf(x)) * X.marginal().pdf(x);
                        }
                    }, -ir, ir)).gaussQuad(1000);
 
        
        for(int k = 1; k < ac.length; k++){
            
            //get the parameters for the bivariate Gaussian pdf.
            double covar = X.autocorrelation()[k];
            
            ac[k] = (new Integration(new IntegralFunction() {
                public double function(double x1) {
                    final double  x1copy = x1;
                    return y.icdf(g.cdf(x1)) * (new Integration(new IntegralFunction() {
                        public double function(double x2) {
                            return y.icdf(g.cdf(x2)) * x1copy;
                            //need to multiply by bivariate Guassian PDF in here.
                        }
                    }, -ir, ir)).gaussQuad(1000);
                }
            }, -ir, ir)).gaussQuad(1000);
            
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
