/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;


/**
 * Takes a set of Fields and computes the relative primes from them.
 * @author Robby McKilliam
 */
public class PrimesSet<T extends Field<T> & Comparable<T>> implements Set<T> {

    private final Vector<T> primes = new Vector<T>();

    public PrimesSet(Collection<T> fields){
        T[] sortedFields = (T[])fields.toArray();
        Arrays.sort(sortedFields);

        //add the first element, it's always prime
        primes.add(sortedFields[0]);

        for( T s : sortedFields ){
            boolean prime = true;
        }
    }

    public int size() {
        return primes.size();
    }

    public boolean isEmpty() {
        return primes.isEmpty();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(T e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
