/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import java.util.Collection;
import java.util.Iterator;

/**
 * This runs the fast selection algorithm of Blum, Rivest and Tarjan.
 * @author Robby McKilliam
 */
public class FastSelection {

    Comparable elem;
    ArrayBackedCollection before, after, largest, smallest;
    Comparable[] all;

    public FastSelection(int maxsize) {
        all = new Comparable[maxsize];
        before = new ArrayBackedCollection(all);
        smallest = new ArrayBackedCollection(all);
        after = new ArrayBackedCollection(all);
    }

    public Collection before() {
        return before;
    }
    
    public Collection smallest() {
        return smallest;
    }

    /**
     * @return the collection of elements that come after
     * the kth.
     */
    public Collection after() {
        return after;
    }

    /**
     * @return the kth element
     */
    public Comparable getElement() {
        return elem;
    }

    /**
     * Wrapper for the Blum, Rivest and Tarjan selection algorithm
     * and return the kth element.  This is setup with the
     * intetion of you casting the object to the type you
     * want later.  I can't be bothered with generics atm.
     * @param k get the kth element
     * @param c the collection to select from
     * @return the kth element
     * 
     * Note:  If k=1, then this returns the first element.
     * If k=0 it wont return any elements!
     * This is in keeping with the way that smallest
     * is defined.
     * 
     */
    public Comparable select(int k, Collection c) {
        
        elem = null;
        Iterator itr = c.iterator();
        int i = 0;
        while(itr.hasNext()){
            all[i] = (Comparable) itr.next();
            i++;
        }
        
        //only run FloydRivest if k is within range [1,c.size]
        if(k > 0 && k <= c.size())
            elem = FloydRivestSelect(0, c.size()-1, k-1, all);
        
        smallest.range(0, k-1);
        after.range(k, c.size()-1);
        before.range(0, k-2);
        
        return getElement();
    }
    
     /**
     * Rearranges A[L..R] so that 
      *  A[i] &lt= A[K] &lt= A[j]  if L &lt=  i &lt K &lt j &lt= R.
     * Then returns A[K].  If R-L+1=N then
     * expected runtime for randomly ordered A[] is 1.5*N compares
     * asymptotically to find median (and less for other K, e.g. finds max
     * in only 1.0*N).
     * In particular if L=0, R=N-1 and K=(N-1)/2 with N odd, then returns median.
     * If N is even, then use K=N/2 to find the upper bimedian, and then
     * the max of A[0..K-1] is the lower bimedian.
     * 
     * Notes:  
     * This is Warren D. Smith's implementation
     * This will alter the input array A
     * This version takes a Comparable[] as input
     **/
    public static Comparable FloydRivestSelect(int L, int R, int K, Comparable[] A) {
        int I, J, N, S, SD, LL, RR;
        double Z;
        Comparable T, W;
        while (R > L) {
            N = R - L + 1;
            if (N > 601) { /* big enough so worth doing FR-type subsampling to
                find strong splitter */
                I = K - L + 1;
                Z = Math.log((double) (N));
                S = (int) (0.5 * Math.exp(Z * 2.0 / 3));
                SD = (int) (0.5 * Math.sqrt(Z * S * (N - S) / N) * Math.signum(2 * I - N));
                LL = Math.max(L, K - ((I * S) / N) + SD);
                RR = Math.min(R, K + (((N - I) * S) / N) + SD);
                /* Recursively select inside small subsample to find an element
                A[K] usually very
                 * near the desired one: */
                FloydRivestSelect(LL, RR, K, A);
            } else if (N > 20 && (int) (5 * (K - L)) > N && (int) (5 * (R - K)) > N) {
                /* not big enough to be worth evaluating those expensive logs etc;
                 * but big enough so random splitter is poor; so use median-of-3 */
                I = K - 1;
                if ( A[K].compareTo(A[I]) < 0 ) {
                    W = A[I];
                    A[I] = A[K];
                    A[K] = W;
                }
                I = K + 1;
                if (A[I].compareTo(A[K]) < 0) {
                    W = A[I];
                    A[I] = A[K];
                    A[K] = W;
                }
                I = K - 1;
                if (A[K].compareTo(A[I]) < 0) {
                    W = A[I];
                    A[I] = A[K];
                    A[K] = W;
                }
            } /* otherwise using random splitter (i.e. current value of A[K]) */
            /* now use A[K] to split A[L..R] into two parts... */
            T = A[K];
            I = L;
            J = R;
            W = A[L];
            A[L] = A[K];
            A[K] = W;
            if (A[R].compareTo(T) > 0) {
                W = A[R];
                A[R] = A[L];
                A[L] = W;
            }
            while (I < J) {
                W = A[I];
                A[I] = A[J];
                A[J] = W;
                I++;
                J--;
                while (A[I].compareTo(T) < 0) {
                    I++;
                }
                while (A[J].compareTo(T) > 0) {
                    J--;
                }
            }
            if (A[L].compareTo(T) == 0) {
                W = A[L];
                A[L] = A[J];
                A[J] = W;
            } else {
                J++;
                W = A[J];
                A[J] = A[R];
                A[R] = W;
            }
            if (J <= (int) K) {
                L = J + 1;
            }
            if ((int) K <= J) {
                R = J - 1;
            }
        /* Now continue on using contracted [L..R] interval... */
        }
        return (A[K]);
    }

    /**
     * Rearranges A[L..R] so that A[i] <= A[K] <= A[j]  if L<=i<K<j<=R.
     * Then returns A[K].  If R-L+1=N then
     * expected runtime for randomly ordered A[] is 1.5*N compares
     * asymptotically to find median (and less for other K, e.g. finds max
     * in only 1.0*N).
     * In particular if L=0, R=N-1 and K=(N-1)/2 with N odd, then returns median.
     * If N is even, then use K=N/2 to find the upper bimedian, and then
     * the max of A[0..K-1] is the lower bimedian.
     * 
     * Notes:  
     * This is Warren D. Smith's implementation
     * This will alter the input array A
     * This version takes a double[] as input
     **/
    public static double FloydRivestSelect(int L, int R, int K, double[] A) {
        int I, J, N, S, SD, LL, RR;
        double Z;
        double T, W;
        while (R > L) {
            N = R - L + 1;
            if (N > 601) { /* big enough so worth doing FR-type subsampling to
                find strong splitter */
                I = K - L + 1;
                Z = Math.log((double) (N));
                S = (int) (0.5 * Math.exp(Z * 2.0 / 3));
                SD = (int) (0.5 * Math.sqrt(Z * S * (N - S) / N) * Math.signum(2 * I - N));
                LL = Math.max(L, K - ((I * S) / N) + SD);
                RR = Math.min(R, K + (((N - I) * S) / N) + SD);
                /* Recursively select inside small subsample to find an element
                A[K] usually very
                 * near the desired one: */
                FloydRivestSelect(LL, RR, K, A);
            } else if (N > 20 && (int) (5 * (K - L)) > N && (int) (5 * (R - K)) > N) {
                /* not big enough to be worth evaluating those expensive logs etc;
                 * but big enough so random splitter is poor; so use median-of-3 */
                I = K - 1;
                if (A[K] < A[I]) {
                    W = A[I];
                    A[I] = A[K];
                    A[K] = W;
                }
                I = K + 1;
                if (A[I] < A[K]) {
                    W = A[I];
                    A[I] = A[K];
                    A[K] = W;
                }
                I = K - 1;
                if (A[K] < A[I]) {
                    W = A[I];
                    A[I] = A[K];
                    A[K] = W;
                }
            } /* otherwise using random splitter (i.e. current value of A[K]) */
            /* now use A[K] to split A[L..R] into two parts... */
            T = A[K];
            I = L;
            J = R;
            W = A[L];
            A[L] = A[K];
            A[K] = W;
            if (A[R] > T) {
                W = A[R];
                A[R] = A[L];
                A[L] = W;
            }
            while (I < J) {
                W = A[I];
                A[I] = A[J];
                A[J] = W;
                I++;
                J--;
                while (A[I] < T) {
                    I++;
                }
                while (A[J] > T) {
                    J--;
                }
            }
            if (A[L] == T) {
                W = A[L];
                A[L] = A[J];
                A[J] = W;
            } else {
                J++;
                W = A[J];
                A[J] = A[R];
                A[R] = W;
            }
            if (J <= (int) K) {
                L = J + 1;
            }
            if ((int) K <= J) {
                R = J - 1;
            }
        /* Now continue on using contracted [L..R] interval... */
        }
        return (A[K]);
    }
    
    /**
     * Collection with an underlying array and an iterator
     * whose start and end points in the array can be modified.
     */
    public static class ArrayBackedCollection implements Collection{

        Object[] a;
        int start, end;
        
        public ArrayBackedCollection(Object[] a){
            this.a = a;
            start = 0;
            end = a.length-1;
        }
        
        public int size() {
            return start - end + 1;
        }

        public boolean isEmpty() {
            return start > end;
        }
        
        public void start(int start){
            this.start = start;
        }
        public void end(int end){
            this.end = end;
        }
        public void range(int start, int end){
            start(start);
            end(end);
        }
        

        public boolean contains(Object arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public ArrayBackedCollectionIterator iterator() {
            return new ArrayBackedCollectionIterator();
        }
        
        /** 
         * Iterator for ArrayBackedCollection.
         * Moves from a[start] to a[end].
         */
        public class ArrayBackedCollectionIterator implements Iterator{
            
            int p;
            
            public ArrayBackedCollectionIterator(){
                p = start;
            }
            
            public boolean hasNext() {
                return p <= end;
            }

            public Object next() {
                p++;
                return a[p-1];
            }

            public void remove() {
                throw new UnsupportedOperationException("Not supported.");
            }
            
        }

        public Object[] toArray() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object[] toArray(Object[] arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean add(Object arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean remove(Object arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean containsAll(Collection arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean addAll(Collection arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean removeAll(Collection arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean retainAll(Collection arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void clear() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
    
}
