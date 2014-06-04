package pubsim;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;

/**
 * Finds all cliques (complete subgraphs) of size k in a given graph
 * @author Robby McKilliam
 */
public class AllCliques<T> implements Iterable<Set<T>>, Serializable {
    
    ///Graph we are enumerating cliques in
    protected final Graph<T,?> G;
    
    ///size of cliques we are looking for
    public final int k;
    
    ///set of vertices of degree greater than or equal to k
    protected final Set<T> H;
            
    public AllCliques(Graph<T,?> G, int k){
        this.G = G;
        this.k = k;
        H = new HashSet();
        for(T v : G.vertexSet() )
        if( G.edgesOf(v).size() >= k ) H.add(v); //store those vertices of degree >= k
    }

    @Override
    public java.util.Iterator<Set<T>> iterator() {
        if(H.isEmpty()) return CombinationEnumerator.<T>setwithemptyset().iterator();
        return new AllCliques.Iterator();
    }
    
    protected class Iterator implements java.util.Iterator<Iterable<T>> {
        
        protected T v; //current value of itr
        protected final java.util.Iterator<T> itr = H.iterator();
        
        ///Store current subgraph we are searching
        protected Graph<T,?> S = null; //want to copy G here
        
        public Iterator() {

        }
        
        private void setupv() {
            v = itr.next();
            
            //if( !H.remove(v) ) throw new RuntimeException("Tried to remove a vertex that wasn't there. This shouldn't happen!");  //update H
            //C = new CombinationEnumerator(H, k - 1).iterator();
        }

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Iterable<T> next() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
}
