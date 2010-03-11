/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing;

import distributions.NoiseGenerator;
import distributions.circular.CircularDistribution;
import lattices.Anstar.Anstar;
import lattices.Anstar.AnstarBucketVaughan;
import static simulator.Util.fracpart;

/**
 * Least squares phase unwrapping estimator based on the lattice
 * An*.  Assumes that angles are measure in interval [-1/2, 1/2).
 * @author Robert McKilliam
 */
public class LeastSquaresEstimator implements BearingEstimator{

    int n;
    protected Anstar anstar;
    protected double[] u;
    
    public LeastSquaresEstimator(){
        anstar = new AnstarBucketVaughan();
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

    public static double asymptoticVariance(CircularDistribution noise, int N){
        double sigma2 = noise.getWrappedVariance();
        double d = 1 - noise.pdf(-0.5);
        return sigma2/(N*d*d);
    }

}
