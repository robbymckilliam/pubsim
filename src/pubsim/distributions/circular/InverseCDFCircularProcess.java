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
    
    public InverseCDFCircularProcess(CircularRandomVariable rv, double[] filter){
       super(rv,filter);
    }
    
    /** This is the autocorrelation required by the sample circular mean estimator */
    @Override
    public double[] sinusoidalAutocorrelation(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}