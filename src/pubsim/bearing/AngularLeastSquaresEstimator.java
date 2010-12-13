/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.bearing;

import pubsim.distributions.circular.CircularRandomVariable;
import pubsim.distributions.circular.UnwrappedMeanAndVariance;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarBucketVaughan;
import static pubsim.Util.fracpart;

/**
 * Least squares phase unwrapping estimator based on the lattice
 * An*.  Assumes that angles are measure in interval [-1/2, 1/2).
 * @author Robert McKilliam
 */
public class AngularLeastSquaresEstimator implements BearingEstimator{

    int n;
    protected final Anstar anstar;
    protected double[] u;
    
    public AngularLeastSquaresEstimator(int length){
        this.n = length;
        anstar = new AnstarBucketVaughan(n-1);
    }    
    
    public void setSize(int n) {
        this.n = n;
        anstar.setDimension(n-1);
    }

    public double estimateBearing(double[] y) {
        if(n != y.length)
            setSize(y.length);
        
        anstar.nearestPoint(y);
        u = anstar.getIndex();
        
        double sum = 0.0;
        for(int i = 0; i < y.length; i++)
            sum += y[i] - u[i];
        
        return fracpart(sum/n);
        
    }

    public double asymptoticVariance(CircularRandomVariable noise, int N){
        double sigma2 = UnwrappedMeanAndVariance.computeWrappedVarianceAbout(0, noise, 10000);
        //double sigma2 = noise.unwrappedVariance();
        double d = 1 - noise.pdf(-0.5);
        return sigma2/(N*d*d);
    }

    public double[] confidenceInterval(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
