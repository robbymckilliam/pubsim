/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import pubsim.lattices.decoder.firsttype.graph.FlowNetwork;
import Jama.Matrix;
import pubsim.lattices.Lattice;
import pubsim.lattices.NearestPointAlgorithm;
import static pubsim.VectorFunctions.onesColumn;

/**
 * Compute the nearest point for a lattice of Voronoi's first type using
 * the Ford-Fulkerson minimum cut (maximum flow) algorithm.
 * 
 * @author Robby McKilliam
 */
public class MinCutFirstType implements NearestPointAlgorithm {
    
    final protected int N,M;
    
    final protected Matrix B, Q;
    
    final protected FlowNetwork F;
    
    /** 
     * Input L is a lattice.  The generator of L must be of the first type
     * i.e by including the vector made by summing all the column vectors in the
     * basis of L, we get obtuse superbasis.
     */
    public MinCutFirstType(Lattice L){
        Matrix Bs = L.getGeneratorMatrix();
        M = Bs.getRowDimension();
        N = Bs.getColumnDimension();
        Matrix bnp1 = Bs.times(onesColumn(N));  //the extra vector
        
        //fill an extended obtuse superbasis matrix
        B = new Matrix(M,N+1);
        for(int n = 0; n < N; n++) 
            for(int m = 0; m < M; m++) B.set(m, n, Bs.get(m,n));
        for(int m = 0; m < M; m++) B.set(m, N, -bnp1.get(m,0));
        
        //compute the extended Gram matrix (i.e. the Selling parameters)
        Q = B.transpose().times(B);
        
        //System.out.println(VectorFunctions.print(B));
        //System.out.println(VectorFunctions.print(Q));
        
        //check it actually is obtuse
        for(int n = 0; n < N+1; n++) 
            for(int m = 0; m < M; m++)
                if(m != n && Q.get(m, n) > 0) 
                    throw new RuntimeException("Not an obtuse basis!");
        
        F = new FlowNetwork(N+3);
        
    }

    @Override
    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
