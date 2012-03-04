/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim;

import Jama.Matrix;
import pubsim.lattices.AbstractLattice;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import static pubsim.VectorFunctions.times;

/**
 * Class for storing scaled versions of lattices
 * @author Robby McKilliam
 */
public class ScaledLattice extends AbstractLattice implements LatticeAndNearestPointAlgorithm {
    
    protected final LatticeAndNearestPointAlgorithm L;
    protected final double d;
    protected final Matrix B;
    protected final int n,m;

    protected final double[] yd, v;
    
    /**
     * @param L the lattice
     * @param d the scale factor
     */
    public ScaledLattice(LatticeAndNearestPointAlgorithm L, double d){
        this.L = L;
        this.d = d;
        this.B = L.getGeneratorMatrix().times(d);
        this.n = L.getDimension();
        this.m = B.getRowDimension();
        yd= new double[m];
        v = new double[m];
    }
    

    @Override
    public double coveringRadius() {
        return d*L.coveringRadius();
    }
    
    @Override
    public double inradius() {
        return d*L.inradius();
    }

    @Override
    public double volume() {
        return Math.pow(d,n)*L.volume();
    }
    
    @Override
    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDimension() {
        return n;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        return B;
    }

    @Override
    public void nearestPoint(double[] y) {
        if(y.length != m) throw new RuntimeException("vector to compute the nearest point to is the wrong size");
        times(y,1/d,yd);
        L.nearestPoint(yd);
        times(L.getLatticePoint(),d,v);
    }

    @Override
    public double[] getLatticePoint() {
       return v;
    }

    @Override
    public double[] getIndex() {
        return L.getIndex();
    }

    @Override
    public double distance() {
        return d*L.distance();
    }
    
    private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            yDoubletoy = new double[y.length];
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(y);
    }
    
    
    
}
