/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.circular;

import pubsim.distributions.RandomVariable;
import pubsim.distributions.processes.ColouredGaussianNoise;
import pubsim.distributions.processes.InverseCDFStationaryProcess;

/**
 * Class for generating circular random processes.
 * @author Robby McKilliam
 */
public class InverseCDFCircularProcess extends InverseCDFStationaryProcess {
    
    public InverseCDFCircularProcess(CircularRandomVariable rv, double[] filter){
       super(rv,filter);
    }
    
    /** This is the autocorrelation required by the sample circular mean estimator */
    public double[] sinusoidalAutocorrelation(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
