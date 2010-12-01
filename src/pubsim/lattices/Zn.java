/*
 * Zn.java
 *
 * Created on 2 November 2007, 13:14
 */

package pubsim.lattices;

import Jama.Matrix;
import pubsim.Util;

/**
 * Nearest point algorithm for the square lattice Zn.
 * You probably never want to use this, it's just here
 * for completeness.
 * @author Robby McKilliam
 */
public class Zn extends LatticeAndNearestPointAlgorithm {
    
    protected double[] x;
    protected int n;

    public Zn(int n){
        setDimension(n);
    }

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
    public double volume(){ return 1.0;}

    @Override
    public double inradius() {
        return 0.5;
    }

    public int getDimension() {
        return n;
    }

    @Override
    public long kissingNumber() {
        return 2*n;
    }

    public Matrix getGeneratorMatrix() {
        return Matrix.identity(n, n);
    }

    public double coveringRadius() {
        return Math.sqrt(0.5*n);
    }

    public double effectiveCodingGain(double SNR){
        return 0;
    }
    
}
