/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import java.util.Enumeration;
import lattices.Lattice;

/**
 * Return a set of point uniformly (kind of) distributed in the
 * fundamental parallelepiped of the lattice given by Bu where B is the
 * generator matrix and u in [0,1]^N.
 * @author Robby McKilliam
 */
public class PointInParallelepiped implements Enumeration<Matrix>{

    Matrix u;
    boolean finished = false;
    int c, N;
    Matrix B;
    int samples;
    int counter = 0;

    /**
     * @param L is the lattice
     * @param samples is the number of samples used per dimension
     */
    public PointInParallelepiped(Lattice L, int samples){
        B = L.getGeneratorMatrix();
        this.samples = samples;
        N = B.getColumnDimension();
        u = new Matrix(N, 1);
        c = N - 1;
    }

    /**
     * @param B the generator matrix for the lattice
     * @param samples is the number of samples used per dimension
     */
    public PointInParallelepiped(Matrix B, int samples){
        this.B = B;
        this.samples = samples;
        N = B.getColumnDimension();
        u = new Matrix(N, 1);
        c = N - 1;
    }

    public boolean hasMoreElements() {
        return !finished;
    }

    public Matrix nextElement() {
        addto(0);
        counter++;
        if(counter >= Math.pow(samples, N)) finished = true;
        return B.times(u);
    }

    protected void addto(int i){
        if(u.get(i, 0) >= 1.0 - 1.0/samples){
            u.set(i, 0, 0.0);
            if(i+1 < N)
                addto(i+1);
        }else{
            u.set(i, 0, u.get(i, 0) + 1.0/samples);
        }
    }

}
