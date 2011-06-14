package pubsim.distributions.processes;

import pubsim.distributions.GaussianNoise;

/**
 * Returns a zero mean stationary correlated Gaussian noise process.  The 
 * process is correlated according to a finite impulse response filter.
 * @author Robby McKilliam
 */
public class ColouredGaussianNoise {

    protected final double[] f;
    protected final GaussianNoise X = new GaussianNoise(0, 1);
    
    public ColouredGaussianNoise(double[] filter){
        f = filter;
    }
    
    
    
}
