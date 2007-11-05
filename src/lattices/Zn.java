/*
 * Zn.java
 *
 * Created on 2 November 2007, 13:14
 */

package lattices;

/**
 * Nearest point algorithm for the square lattice Zn.
 * @author Robby McKilliam
 */
public class Zn implements LatticeNearestPointAlgorithm {
    
    protected double[] x;
    protected int n;
    
    public void setDimension(int n){
        this.n =  n;
        x = new double[n];
    }
    
    public void nearestPoint(double[] y){
        for(int i = 0; i < n; i++)
            x[i] = Math.round(y[i]);
    }
    
    public double[] getLatticePoint() { return x; }
    
    public double[] getIndex() { return x; }
    
}
