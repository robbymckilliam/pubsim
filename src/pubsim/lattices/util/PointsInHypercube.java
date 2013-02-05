/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.util;

import Jama.Matrix;
import java.util.concurrent.LinkedBlockingQueue;
import pubsim.lattices.LatticeInterface;
import pubsim.VectorFunctions;

/**
 * Locates all the lattie points from a lattice that lie in
 * the hypercube center + [-0.5,0.5)^m where m is the dimension of the
 * space that the lattice lies in. i.e. the number of rows in
 * the generator matrix.  center is a translation from the origin.
 *
 * Uses a sphere decoder with radius 0.5sqrt(n) and then rejects those
 * points not in the hypercube.
 *
 * This seems to have some numerical problems so that it obtains points
 * outside the hypercube sometimes.  It's potentially difficult to perform
 * this operation and guarantee that all points are in the hypercube due
 * to imprecision when rounding.
 *
 * @author harprobey
 */
public class PointsInHypercube extends PointInSphere {

    private Matrix unext;
    //used DELTA to avoid rounding error
    final double DELTA = 0.5 - 0.00000001;

    /**
     * @param lattice
     */
    public PointsInHypercube(LatticeInterface lattice, double[] center){
        int rows = lattice.getGeneratorMatrix().getRowDimension();
        double radius = 0.5 * Math.sqrt(rows);
        init(lattice, radius, center);
        unext = findNextInHyperCube();
        
    }

    public PointsInHypercube(LatticeInterface lattice){
        int rows = lattice.getGeneratorMatrix().getRowDimension();
        double radius = 0.5 * Math.sqrt(rows);

        double[] center = new double[rows];
        for(int i = 0; i < rows; i++) center[i] = DELTA;

        init(lattice, radius, center);
        unext = findNextInHyperCube();
    }


    @Override
    public Matrix nextElement() {
        uret = unext.copy();
        unext = findNextInHyperCube();
        r = M.times(uret);
        return r;
    }

    protected Matrix findNextInHyperCube(){
        //while the queue is empty, yield the main thread
        while(dthread.isAlive() || !queue.isEmpty()){
            while(queue.isEmpty())
                Thread.yield();
            try{
                Matrix u = queue.take();
                Matrix r = M.times(u);
                boolean inhyper = true;
                for(int i = 0; i < m; i++){
                    inhyper &= (r.get(i,0) < 0.5 + DELTA) && (r.get(i, 0) >= -0.5 + DELTA);
                }
                if(inhyper) return u;
            }catch(InterruptedException e){
                throw new RuntimeException("Taking from linked queue interrupted");
            }
        }
        return null;
    }

    @Override
    public boolean hasMoreElements() {
        //System.out.println(dthread.isAlive() + ", " + !queue.isEmpty());
        return unext != null;
    }



}