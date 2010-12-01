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
public class BarnesWall extends AbstractLattice{

    protected int n;
    protected int m;

    public BarnesWall(int m){
        this.m = m;
        n = (int)Math.round(Math.pow(2, m+1));
    }

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDimension() {
        return n;
    }

    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double logVolume(){
        return n*m/4;
    }

    @Override
    public double volume(){
        return Math.pow(2.0, logVolume());
    }

    @Override
    public double inradius(){
        return Math.pow(2.0, m/2.0)/2.0;
    }

    @Override
    public long kissingNumber(){
        long prod = 1;
        long pow = 1;
        for(int i = 1; i <= m+1; i++){
            pow *= 2;
            prod *= pow + 2;
        }
        return prod;
    }

}
