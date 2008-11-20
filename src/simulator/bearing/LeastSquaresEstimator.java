/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing;

import lattices.Anstar;
import lattices.AnstarBucketVaughan;

/**
 * Least squares phase unwrapping estimator based on the lattice
 * An*.
 * @author Robert McKilliam
 */
public class LeastSquaresEstimator implements BearingEstimator{

    int n;
    protected Anstar anstar;
    protected double[] ymod1, u;
    
    public LeastSquaresEstimator(){
        anstar = new AnstarBucketVaughan();
    }
    
    
    public void setSize(int n) {
        this.n = n;
        anstar.setDimension(n-1);
        ymod1 = new double[n];
    }

    public double estimateBearing(double[] y) {
        if(n != y.length)
            setSize(y.length);
        
        
        for(int i = 0; i < y.length; i++)
            ymod1[i] = y[i]/(2*Math.PI);
        
        anstar.nearestPoint(ymod1);
        u = anstar.getIndex();
        
        double sum = 0.0;
        for(int i = 0; i < y.length; i++)
            sum += ymod1[i] - u[i];
        
        return Math.IEEEremainder(2*Math.PI*sum/n, 2*Math.PI);
        
    }

}
