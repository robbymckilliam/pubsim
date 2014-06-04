package pubsim;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for enumerating all combinations of elements
 *
 * @author Robby McKilliam
 * @param <T> The objects we enumerate over
 */
public class CombinationEnumerator<T> implements Iterable<Set<T>>, Serializable {

    ///The set we want combinations of
    public final Set<T> S;

    ///The size of the combinations we want
    public final int k;

    ///Enumerates all combinations of size k of the set S
    public CombinationEnumerator(Set<T> S, int k) {
        this.S = S;
        this.k = k;
    }

    @Override
    public java.util.Iterator<Set<T>> iterator() {
        if(k <= 0 || k > S.size()) return CombinationEnumerator.<T>setwithemptyset().iterator();
        return new CombinationEnumerator.Iterator();
    }

    protected class Iterator implements java.util.Iterator<Set<T>> {

        public final long total = pubsim.Util.binom(S.size(), k);  
        protected long count = 0;
        protected final java.util.Iterator<T> itr = S.iterator();
        protected T v; //current value of itr
        protected Set<T> H = new HashSet(S); //make a copy of S
        
        ///Store list of iterators for generating elements
        protected java.util.Iterator<Iterable<T>> C = null;

        public Iterator() {
            setupv();
        }

        private void setupv() {
            v = itr.next();
            if( !H.remove(v) ) throw new RuntimeException("Tried to remove an element that wasn't there. This shouldn't happen!");  //update H
            C = new CombinationEnumerator(H, k - 1).iterator();
        }

        @Override
        public boolean hasNext() {
            return count < total;
        }

        @Override
        public Set<T> next() {
            count++;
            if (!C.hasNext()) setupv();
            HashSet<T> ret = new HashSet();
            ret.add(v); for(T x : C.next() ) ret.add(x);
            return ret;
        }

        @Override
        public void remove() {
        }

    }
    
    /** Returns a set that contains the empty set */
    public static <T> Set<Set<T>> setwithemptyset() {
        HashSet<Set<T>> S = new HashSet();
        S.add(new HashSet<T>());
        return S;
    }

}
