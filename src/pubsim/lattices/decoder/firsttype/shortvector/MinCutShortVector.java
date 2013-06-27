package pubsim.lattices.decoder.firsttype.shortvector;

import Jama.Matrix;
import java.util.Set;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import static pubsim.VectorFunctions.matrixMultVector;
import static pubsim.VectorFunctions.onesColumn;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.decoder.ShortVector;

/**
 * Computes a short vector in a lattice of first type using the Stoer-Wagner minimum cut algorithm
 * @author Robby McKilliam
 */
public class MinCutShortVector implements ShortVector{
    
    final protected int N,M;
    
    final protected Matrix B, Q, Binv;
    
    final double[] u,v;
    
    public MinCutShortVector(LatticeInterface L){
        Matrix Bs = L.getGeneratorMatrix();
        M = Bs.getRowDimension();
        N = Bs.getColumnDimension();
        Matrix bnp1 = Bs.times(onesColumn(N));  //the extra vector
        
        Binv = (Bs.transpose().times(Bs)).inverse().times(Bs.transpose());
        //System.out.println(VectorFunctions.print(Binv));
        
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
        
        WeightedGraph<Integer, DefaultWeightedEdge> G =
                new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
       
        //add all the vertices
        for(int i = 0; i < N+1; i++) G.addVertex(i); 
        
        //add all the edges
        for(int i = 0; i < N+1; i++) 
            for(int j = i+1; j < N+1; j++)
                G.setEdgeWeight(G.addEdge(i,j), -Q.get(i,j));
        
        //compute the minimum cut
        StoerWagnerMinimumCut<Integer, DefaultWeightedEdge> mincut = 
                new StoerWagnerMinimumCut<Integer, DefaultWeightedEdge>(G);
        
        u = new double[N+1];
        v = new double[N+1];
        
        Set<Integer> C = mincut.minCut();
        for( Integer i : C ) u[i] = 1;
        
        //compute the nearest point from the index
        matrixMultVector(B, u, v); 
        
    } 

    @Override
    public double[] getShortestVector() {
        return v;
    }

    @Override
    public double[] getShortestIndex() {
        return u;
    }
    
    
    
    
}
