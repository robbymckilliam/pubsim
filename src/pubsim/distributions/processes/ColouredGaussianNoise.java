package pubsim.distributions.processes;

import com.sun.org.apache.xalan.internal.xsltc.dom.FilteredStepIterator;
import pubsim.CircularBufferDouble;
import pubsim.VectorFunctions;
import pubsim.distributions.AbstractRandomVariable;
import pubsim.distributions.GaussianNoise;
import pubsim.distributions.RandomVariable;

/**
 * Returns a zero mean stationary correlated Gaussian noise process.  The 
 * process is correlated according to a finite impulse response filter.
 * @author Robby McKilliam
 */
public class ColouredGaussianNoise implements StationaryProcess{

    protected final double[] f;
    protected final GaussianNoise noisegen = new GaussianNoise(0, 1);
    protected final CircularBufferDouble X;
   
    public ColouredGaussianNoise(double[] filter){
        f = filter;
        X = new CircularBufferDouble(filter.length);
    }

    @Override
    public RandomVariable marginal() {
        return new GaussianNoise(0, f[0]*f[0]);
    }

    @Override
    public double[] autocorrelation() {
        return VectorFunctions.conv(f,f);
    }
    
    private int n = 0;
    public double getNoise(){
        X.add(noisegen.getNoise());
        double Y = 0;
        for(int k = 0; k < f.length; k++)
            Y += f[k]*X.get(n-k);
        n++;
        return Y;
    }
    
    
}
