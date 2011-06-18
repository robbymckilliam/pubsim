/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.circular;

import Jama.Matrix;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import pubsim.distributions.processes.InverseCDFStationaryProcess;
import pubsim.optimisation.AutoIntegralFunction;

/**
 * Class for generating circular random processes.
 * @author Robby McKilliam
 */
public class InverseCDFCircularProcess extends InverseCDFStationaryProcess implements CircularProcess{
    
    final CircularRandomVariable crv;
    
    protected double[] acs;
    
    public InverseCDFCircularProcess(CircularRandomVariable rv, double[] filter){
       super(rv,filter);
       crv = rv;
    }
    
    /** This is the autocorrelation required by the sample circular mean estimator */
    @Override
    public double[] sinusoidalAutocorrelation(){
        if(acs != null) return acs;
        
        acs = new double[X.autocorrelation().length];
        double Xvar = X.autocorrelation()[0];
        final double ir = 10*Math.sqrt(Xvar); //range to compute integral over        
        
        //compute the variance term ie. ac[0]
        acs[0] = (new Integration(new IntegralFunction() {
                        public double function(double x) {
                            double sFGx = Math.sin(2*Math.PI*y.icdf(g.cdf(x)));
                            return  sFGx*sFGx* X.marginal().pdf(x);
                        }
                    }, -ir, ir)).gaussQuad(200);
 
        //compute all teh convariance terms
        double[] min = {-ir,-ir}; double[] max = {ir,ir};
        for(int k = 1; k < acs.length; k++){
            final int kf = k;
            acs[k] = new AutoIntegralFunction(200) {
                public double value(Matrix mat) {
                    double x1 = mat.get(0,0); 
                    double xk = mat.get(1,0);
                    return Math.sin(2*Math.PI*y.icdf(g.cdf(x1))) 
                            * Math.sin(2*Math.PI*y.icdf(g.cdf(xk))) 
                            * X.bivariatePdf(kf, x1, xk);
                }
            }.integral(min, max);
        }
        return acs;
    }

    public CircularRandomVariable circularMarginal() {
        return crv;
    }
    
    
    
}