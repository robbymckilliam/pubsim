/*
 * Zn.java
 *
 * Created on 2 November 2007, 13:14
 */

package pubsim.lattices;

import Jama.Matrix;

/**
 * Nearest point algorithm for the square lattice Zn.
 * You probably never want to use this, it's just here
 * for completeness.
 * @author Robby McKilliam
 */
public class Zn extends AbstractLattice implements LatticeAndNearestPointAlgorithmInterface {
    
    protected double[] x;
    protected int n;

    public Zn(int n){
        this.n =  n;
        x = new double[n];
    }
    
    @Override
    public final void nearestPoint(double[] y){
        if (n != y.length) throw new RuntimeException("y is the wrong length");
        
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
    public double norm() {
        return 1.0;
    }

    @Override
    public int getDimension() {
        return n;
    }

    @Override
    public final long kissingNumber() {
        return 2*n;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        return Matrix.identity(n, n);
    }

    @Override
    public final double coveringRadius() {
        return Math.sqrt(0.5*n);
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            yDoubletoy = new double[y.length];
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(yDoubletoy);
    }
    
    @Override
    public String name() {
        return "Zn" + n;
    }
    
}
