package pubsim.lattices.firstkind;

import Jama.Matrix;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import pubsim.AllCliquesOfSize;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import pubsim.lattices.LatticeInterface;
import static pubsim.lattices.firstkind.FirstKindCheckSlow.isObtuse;
import static pubsim.VectorFunctions.dot;
import static pubsim.lattices.firstkind.FirstKindCheckSlow.isSuperbase;

/**
 *
 * @author Robby McKilliam
 */
public class FirstKindCheck extends FirstKindCheckSlow {
    
    public FirstKindCheck(Matrix B) {
        this(new LatticeAndNearestPointAlgorithm(B));
    }
    
    public FirstKindCheck(LatticeInterface L) {
        super(L);        
    }
    
    ///Given a set R containingatleast k vectors, decide whether the set contains an obtuse super basis
    @Override
    protected boolean containsObtuseSuperBasis(Set<Matrix> R) {
        Graph<Matrix,?> G = obtuseConnectedGraph(R);
        for( Set<Matrix> C : new AllCliquesOfSize<>(n+1,G) ) {
            if( isObtuse(C) && isSuperbase(C) ) {
                B = new HashSet(C);
                return true;
            }
        }
        return false;
    }
    
    public static Graph<Matrix,?> obtuseConnectedGraph(Set<Matrix> R){
        Graph G = new SimpleGraph<>(DefaultEdge.class);
        for( Matrix v : R ) G.addVertex(v); //graph contains the relevant vectors
        
        //add edges when dot products are zero or less
        for( Matrix v : R )
            for( Matrix x : R ) 
                if( dot(v,x) <= 1e-8 && !G.containsEdge(v, x) && !G.containsEdge(x, v) ) G.addEdge(v, x);
        
        return G;
    }
    
}
