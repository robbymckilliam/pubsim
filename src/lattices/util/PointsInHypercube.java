/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import java.util.concurrent.LinkedBlockingQueue;
import lattices.Lattice;
import simulator.VectorFunctions;

/**
 * Locates all the lattie points from a lattice that lie in
 * the hypercube t + [0,1)^n where n is the dimension of the lattice.
 * and t is a translation in R^n
 * @author harprobey
 */
public class PointsInHypercube extends AbstractPointEnumerator
        implements PointEnumerator{

    Matrix M, Q, Qt, R;
    double[] u;
    int n, m;
    boolean finished = false;
    DecodeThread dthread;

    /**
     * @param lattice
     */
    public PointsInHypercube(Lattice lattice){
        init(lattice);
    }

    private void init(Lattice lattice){

        M = lattice.getGeneratorMatrix();
        m = M.getRowDimension();
        n = M.getColumnDimension();

        u = new double[n];

        simulator.QRDecomposition QR = new simulator.QRDecomposition(M);
        R = QR.getR();
        Q = QR.getQ();
        Qt = Q.transpose();

        System.out.println(VectorFunctions.print(R));
        System.out.println(VectorFunctions.print(Q));
        System.out.println(VectorFunctions.print(Qt));

        //start thread to compute points.
        dthread = new DecodeThread();
        //dthread.setPriority(Thread.MAX_PRIORITY);
        dthread.start();


    }

    protected LinkedBlockingQueue<Matrix> queue = new
            LinkedBlockingQueue<Matrix>(1000);

    Matrix r, uret;
    public Matrix nextElement() {
        //while the queue is empty, yield the main thread
        while(queue.isEmpty())
            Thread.yield();
        try{
            uret = queue.take();
            r = M.times(uret);
        }catch(InterruptedException e){
            throw new RuntimeException("Taking from linked queue interrupted");
        }
        return r;
    }

    /**
     * Returns the index of the previously returned lattice point.
     * This does not iterate through the points (does not remove
     * from the enumeration).
     * @return index
     */
    public Matrix getElementIndex(){
        return uret;
    }

    /**
     * Returns the index of the previously returned lattice point.
     * This does not iterate through the points (does not remove
     * from the enumeration).
     * @return index
     */
    public double[] getElementIndexDouble(){
        return uret.getColumnPackedCopy();
    }

    /**
     * Thread to iteratively compute points inside the sphere.
     */
    protected class DecodeThread extends Thread{

        @Override
        public void run(){
            decode(0);
        }

        /**
         * Recursive decode function to test nearest plane
         * for a particular dimension/element
         */
        protected void decode(int k){

            //DELTA translation to avoid rounding errors
            final double DELTA = 0.00000001;
            
            //set least possible u[k]
            double uqsum = 0.0;
            for(int i = 0; i < k; i++) uqsum += u[i]*R.get(i, k)*Qt.get(i, k);
            double y = Qt.get(k, k)*R.get(k, k);
            double minindex = Math.ceil((-Math.signum(y)/2.0 + DELTA - uqsum)/y);
            double maxindex = Math.floor((Math.signum(y)/2.0 + DELTA - uqsum)/y);

            u[k] = minindex;

            System.out.println("k = " + k + ", " + minindex + ", " + maxindex);

            while(u[k] <=  maxindex){

                //if this is not the first element then recurse
                if( k < n-1 ){
                    decode(k+1);
                //otherwise this point is in the hypercube so include it.
                }else{
                    queue.add(VectorFunctions.columnMatrix(u));
                    //while the queue is a bit big, yield this thread
                    while(queue.size() >= 990)
                        yield();
                    }
                u[k]++;
            }
            
        }

    }

    public double percentageComplete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasMoreElements() {
        //System.out.println(dthread.isAlive() + ", " + !queue.isEmpty());
        return dthread.isAlive() || !queue.isEmpty();
    }

    public double[] nextElementDouble() {
        return nextElement().getColumnPackedCopy();
    }

}