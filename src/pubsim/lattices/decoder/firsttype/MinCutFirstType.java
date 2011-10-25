/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import pubsim.VectorFunctions;
import pubsim.lattices.decoder.firsttype.graph.FlowNetwork;
import Jama.Matrix;
import static pubsim.VectorFunctions.matrixMultVector;
import pubsim.lattices.Lattice;
import pubsim.lattices.NearestPointAlgorithm;
import pubsim.lattices.decoder.firsttype.graph.FlowEdge;
import pubsim.lattices.decoder.firsttype.graph.FordFulkerson;
import static pubsim.VectorFunctions.onesColumn;
import static pubsim.Util.fracpart;

/**
 * Compute the nearest point for a lattice of Voronoi's first type using
 * the Ford-Fulkerson minimum cut (maximum flow) algorithm.
 * 
 * @author Robby McKilliam
 */
public class MinCutFirstType implements NearestPointAlgorithm {
    
    final protected int N,M;
    
    final protected Matrix B, Q, Binv;
    
    final double[] z, s, u, v;
    
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
        
        Binv = (Bs.transpose().times(Bs)).inverse().times(Bs.transpose());
        
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
        
        z = new double[N+1];
        s = new double[N+1];
        u = new double[N+1];
        v = new double[N+1];
        
    }

    @Override
    public void nearestPoint(double[] y) {
        
        matrixMultVector(Binv, y, z); 
        z[N] = 0.0;
        
        double[] test = matrixMultVector(B, z);
         assert(VectorFunctions.distance_between(test, y) < 0.0000001);
        System.out.println(VectorFunctions.print(z));

        for(int i = 0; i < N+1; i++){
            s[i] = 0.0;
            for(int j = 0; j < N+1; j++) s[i] += Q.get(i,j)*fracpart(z[j]);
        } 
        
        //construct a graph and assign weights
        FlowNetwork F = constructBasicGraph();
        //fill the source and sinc weights using s
        for(int i = 1; i <= N+1; i++){
            if(s[i-1] > 0.0){
                F.addEdge(new FlowEdge(i,N+2, s[i-1]));
                F.addEdge(new FlowEdge(N+2,i, s[i-1]));
            }else{
                F.addEdge(new FlowEdge(i,0, -s[i-1]));
                F.addEdge(new FlowEdge(0,i, -s[i-1]));
            }
        }
        
        System.out.println(F);
        
        //run the maxflow/mincut algorithm
        FordFulkerson maxflow = new FordFulkerson(F, 0, N+2);
        
        //grab the index from the minimum cut
        for(int i = 0; i < N+1; i++){
            if(maxflow.inCut(i+1)) u[i] = Math.floor(z[i]) + 1;
            else u[i] = Math.floor(z[i]);
        }
        
        //compute the nearest point from the index
        matrixMultVector(B, u, v); 
        
        
    }
    
    protected FlowNetwork constructBasicGraph(){
        FlowNetwork F = new FlowNetwork(N+3);
        for(int i = 1; i <= N+1; i++)
            for(int j = 1; j <= N+1; j++)
                if(Q.get(i-1,j-1) != 0.0 && i != j) F.addEdge(new FlowEdge(i, j, -Q.get(i-1, j-1)));
        return F;
    }

    @Override
    public double[] getLatticePoint() {
        return v;
    }

    @Override
    public double[] getIndex() {
        return u;
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
