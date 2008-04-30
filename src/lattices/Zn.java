/*
 * Zn.java
 *
 * Created on 2 November 2007, 13:14
 */

package lattices;

/**
 * Nearest point algorithm for the square lattice Zn.
 * You probably never want to use this, it's just here
 * for completeness.
 * @author Robby McKilliam
 */
public class Zn implements NearestPointAlgorithmInterface {
    
    protected double[] x;
    protected int n;
    
    @Override
    public void setDimension(int n){
        this.n =  n;
        x = new double[n];
    }
    
    @Override
    public void nearestPoint(double[] y){
        if (n != y.length)
	    setDimension(y.length);
        
        for(int i = 0; i < n; i++)
            x[i] = Math.round(y[i]);
    }
    
    @Override
    public double[] getLatticePoint() { return x; }
    
    @Override
    public double[] getIndex() { return x; }

    /** {@inheritDoc} */
    @Override
    public double volume(){ return 1;}

    public double inradius() {
        return 1.0;
    }
    
}
