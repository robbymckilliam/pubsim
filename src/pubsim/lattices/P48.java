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
public class P48 extends AbstractLattice{

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDimension() {
        return 48;
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
        return Math.sqrt(6.0)/2.0;
    }

    @Override
    public long kissingNumber(){
        return 52416000;
    }


}
