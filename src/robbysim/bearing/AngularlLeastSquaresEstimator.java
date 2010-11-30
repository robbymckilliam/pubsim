/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.bearing;

import robbysim.distributions.RandomVariable;
import robbysim.distributions.circular.CircularRandomVariable;
import robbysim.lattices.Anstar.Anstar;
import robbysim.lattices.Anstar.AnstarBucketVaughan;
import static robbysim.Util.fracpart;

/**
 * Least squares phase unwrapping estimator based on the lattice
 * An*.  Assumes that angles are measure in interval [-1/2, 1/2).
 * @author Robert McKilliam
 */
public class AngularlLeastSquaresEstimator implements BearingEstimator{

    protected final int N;
    protected Anstar anstar;
    protected double[] u;
    
    public AngularlLeastSquaresEstimator(int n){
        this.N = n;
        anstar = new AnstarBucketVaughan();
    }

    public double estimateBearing(double[] y) {
        
        anstar.nearestPoint(y);
        u = anstar.getIndex();
        
        double sum = 0.0;
        for(int i = 0; i < y.length; i++)
            sum += y[i] - u[i];
        
        return fracpart(sum/N);
        
    }

    public static double asymptoticVariance(CircularRandomVariable noise, int N){
        double sigma2 = noise.unwrappedVariance();
        double d = 1 - noise.pdf(-0.5);
        return sigma2/(N*d*d);
    }

}
