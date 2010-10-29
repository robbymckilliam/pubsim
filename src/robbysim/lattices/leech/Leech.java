/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.leech;

import Jama.Matrix;
import robbysim.lattices.AbstractLattice;
import robbysim.Util;

/**
 * Standard, unimodular, self-dual representation of the Leech lattice.
 * See page 133 of SPLAG.
 * @author harprobey
 */
public class Leech extends AbstractLattice{

    @Override
    public double volume() {
        return 1.0;
    }

    @Override
    public double inradius() {
        return 1.0;
    }

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDimension() {
        return 24;
    }

    protected static final double[][] dMat
            = { {8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {2,2,2,2,0,0,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0},
                {2,2,0,0,2,2,0,0,2,2,0,0,2,2,0,0,0,0,0,0,0,0,0,0},
                {2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,0,0,0,0,0,0,0,0},
                {2,0,0,2,2,0,0,2,2,0,0,2,2,0,0,2,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0},
                {2,0,2,0,2,0,0,2,2,2,0,0,0,0,0,0,2,2,0,0,0,0,0,0},
                {2,0,0,2,2,2,0,0,2,0,2,0,0,0,0,0,2,0,2,0,0,0,0,0},
                {2,2,0,0,2,0,2,0,2,0,0,2,0,0,0,0,2,0,0,2,0,0,0,0},
                {0,2,2,2,2,0,0,0,2,0,0,0,2,0,0,0,2,0,0,0,2,0,0,0},
                {0,0,0,0,0,0,0,0,2,2,0,0,2,2,0,0,2,2,0,0,2,2,0,0},
                {0,0,0,0,0,0,0,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0},
                {-3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1} };

    //Generator matrix for the leech lattice
    protected static final Matrix mat
                    = new Matrix(dMat).times(1.0/Math.sqrt(8.0)).transpose();

    public Matrix getGeneratorMatrix() {
        return mat;
    }

    @Override
    public long kissingNumber(){
        return 196560;
    }

}
