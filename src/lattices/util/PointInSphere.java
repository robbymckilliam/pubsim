/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import lattices.Lattice;
import simulator.VectorFunctions;

/**
 *  Returns all the points of a lattice in a sphere of given
 * radius.  The sphere does not have to be centered at
 * the origin.
 * @author Robby McKilliam
 */
public class PointInSphere implements PointEnumerator{

    Matrix M, R, Q;
    double[] y, u;
    double D;
    int n, m;
    boolean finished = false;
    DecodeThread dthread;

    /**
     * @param lattice
     * @param radius The radius of the sphere
     * @param center The position of the center of the sphere
     */
    public PointInSphere(Lattice lattice, double radius, double[] center){
        init(lattice, radius, center);
    }

    /**
     * Assumes the center of the sphere is the origin.
     * @param lattice
     * @param radius The radius of the sphere
     */
    public PointInSphere(Lattice lattice, double radius){
        double[] center = new double[lattice.getGeneratorMatrix().getRowDimension()];
        init(lattice, radius, center);
    }

    private void init(Lattice lattice, double radius, double[] center){
        M = lattice.getGeneratorMatrix();
        m = M.getRowDimension();
        n = M.getColumnDimension();
        if(m != center.length)
            throw new ArrayIndexOutOfBoundsException("center is not the correct dimension");

        simulator.QRDecomposition QR = new simulator.QRDecomposition(M);
        R = QR.getR();
        Q = QR.getQ();
        D = radius;
        //compute the center in the triangular frame
        y = VectorFunctions.matrixMultVector(Q.transpose(), center);
        u = new double[n];

        System.out.println("M = " + VectorFunctions.print(M));
        System.out.println("R = " + VectorFunctions.print(R));
        System.out.println("Q = " + VectorFunctions.print(Q));

        //start thread to compute points.
        dthread = new DecodeThread();
        dthread.setPriority(Thread.MAX_PRIORITY);
        dthread.start();

    }

    public double[] nextElementDouble() {
        double[] r = VectorFunctions.matrixMultVector(M, u);
        //compute the next point
        dthread.resume();
        //block this thread until it completes.
        return r;
    }

    /**
     * Thread to iteratively compute points inside the sphere.
     */
    protected class DecodeThread extends Thread{

        @Override
        public void run(){
            decode(n-1, 0);
        }

        /**
         * Recursive decode function to test nearest plane
         * for a particular dimension/element
         */
        protected void decode(int k, double d){
            //return if this is already not in the sphere
            if(d > D){
                return;
            }

            //compute the sum of R[k][k+i]*uh[k+i]'s
            //and the distance so far
            double rsum = 0.0;
            for(int i = k+1; i < n; i++ ){
                rsum += u[i]*R.get(k, i);
            }

            //set least possible ut[k]
            u[k] = Math.ceil((-Math.sqrt(D - d) + y[k] - rsum)/R.get(k,k));

             //System.out.println("u = " + VectorFunctions.print(u));
             //System.out.println("k = " + k);
             //System.out.println("D = " + D);
             //System.out.println("rsum = " + rsum);

            while(u[k] <= (Math.sqrt(D - d) + y[k] - rsum)/R.get(k,k) ){
                double kd = R.get(k, k)*u[k] + rsum - y[k];
                double sumd = d + kd*kd;

                //if this is not the first element then recurse
                if( k > 0)
                    decode(k-1, sumd);
                //otherwise check if this is the best point so far encounted
                //and update if required
                else{
                    if(sumd <= D){
                        //System.out.println("*** u = " + VectorFunctions.print(u));
                        //System.out.flush();
                        suspend();
                       //System.arraycopy(u, 0, ubest, 0, n);
                    }
                }
                u[k]++;
            }
        }
    }

    public double percentageComplete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasMoreElements() {
        return dthread.isAlive();
    }

    public Matrix nextElement() {
        return VectorFunctions.columnMatrix(nextElementDouble());
    }

}
