/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import Jama.Matrix;

/**
 *
 * @author Robby McKilliam
 */
public class E8 extends LatticeAndNearestPointAlgorithm{
    
    final int n = 8;
    final double[] yt = new double[n];

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDimension() {
        return 8;
    }

    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double volume(){
        return 1.0;
    }

    @Override
    public double inradius(){
        return Math.sqrt(2.0)/2.0;
    }

    @Override
    public long kissingNumber(){
        return 240;
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length) throw new RuntimeException("vector must have length 8");
        

    }

    @Override
    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
