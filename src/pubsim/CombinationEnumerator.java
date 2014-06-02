package pubsim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * Class for enumerating combinations of elements in a set
 *
 * @author Robby McKilliam
 * @param <T> The objects we enumerate over
 */
public class CombinationEnumerator<T> implements Iterable<Iterable<T>>, Serializable{

    ///The set we want combinations of
    public final Iterable<T> S;
    
    ///The size of the combinations we want
    public final int k;
        
    public CombinationEnumerator(Set<T> S, int k){
        if(k > S.size()) throw new ArrayIndexOutOfBoundsException("I can't generate a combination with more elements than there are in the set!");
        this.S = S;
        this.k = k;
    }
    
    @Override
    public java.util.Iterator<Iterable<T>> iterator() {
        return new CombinationEnumerator.Iterator();
    }

    protected class Iterator implements java.util.Iterator<Iterable<T>> {
        
        ///Store the current combination
        protected final ArrayList<java.util.Iterator<T>> a;
    
        ///Stores the current index we are iterating upon
        protected int currentIndex = 0;
        
        public Iterator() {
            a = new ArrayList<java.util.Iterator<T>>(k); 
        }

        @Override
        public boolean hasNext() {
            return currentIndex != k-1 || a.get(k-1).hasNext();
        }

        @Override
        public Iterable<T> next() {
            return null;
        }

        @Override
        public void remove() {
        }

    }

}
