package pubsim;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.Subgraph;

/**
 * Enumerates all cliques (complete subgraphs) of size k in a given graph
 * @author Robby McKilliam
 */
public class AllCliquesOfSize<T> implements Iterable<Set<T>>, Serializable {
    
    ///Graph we are enumerating cliques in
    protected final Graph<T,?> G;
    
    ///size of cliques we are looking for
    public final int k;
            
    public AllCliquesOfSize(int k, Graph<T,?> G){
        this.G = AllCliquesOfSize.<T>subgraphWithDegreeAtleast(k, G);
        this.k = k;
    }

    @Override
    public java.util.Iterator<Set<T>> iterator() {
        if(G.vertexSet().isEmpty()) return new HashSet<Set<T>>().iterator(); //return iterator to emptyset
        return new AllCliquesOfSize.Iterator();
    }
    
    protected class Iterator implements java.util.Iterator<Iterable<T>> {
        
        protected T v; //current value of itr
        protected final java.util.Iterator<T> itr = G.vertexSet().iterator();
        
        ///Store current subgraph we are searching
        protected Graph<T,?> S = new Subgraph<>(G,G.vertexSet()); //make a copy of G
        
        ///Store list of iterators for generating vertices in the subgraph
        protected java.util.Iterator<Set<T>> C;
        
        public Iterator() {
            setupv();
        }
        
        private void setupv() {
            v = itr.next();
            S = subgraphConnectedTo(v, S); //remove v from S
            C = new AllCliquesOfSize<>(k-1,S).iterator();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext() || C.hasNext();
        }

        @Override
        public Iterable<T> next() {
            if (!C.hasNext()) setupv();
            HashSet<T> ret = new HashSet();
            ret.add(v); for(T x : C.next()) ret.add(x);
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    /** Return the subgraph of G connected to vertex v */
    public static <T> Graph<T,?> subgraphConnectedTo(T v, Graph<T,?> G){
        Set<T> H = new HashSet();
        for(T x : G.vertexSet())
            if( G.containsEdge(v, x) ) H.add(x);
        return new Subgraph<>(G,H);
    }
    
    /** Return the subgraph of G with vertices of degree greater than k */
    public static <T> Graph<T,?> subgraphWithDegreeAtleast(int k, Graph<T,?> G) {
        Set<T> H = new HashSet();
        for(T v : G.vertexSet())
            if( G.edgesOf(v).size() >= k ) H.add(v);
        return new Subgraph<>(G,H);
    }
    
}
