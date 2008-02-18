/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import simulator.IndexedDouble;

/**
 * This is Warren Smith's O(n) bucket A_{n/m} nearest point
 * algorithm.
 * UNDER CONSTRUCTION
 * @author Robby McKilliam
 */
public class AnmBucket extends Anm implements LatticeNearestPointAlgorithm{

    private IndexedDoubleList[] buckets;
    private ListElem[] bes;
    private double[] z;
    
    /** Constructor can set the m part of A_{n/m}. */
    public AnmBucket(int M){
        super(M);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setDimension(int n){
        this.n = n;
        u = new double[n+1];
        v = new double[n+1];
        
        //setup the buckets.  We will test with n+1 buckets first
        //buckets = new IndexedDoubleList[(n+1)/M];
        buckets = new IndexedDoubleList[n+1];
        for(int i = 0; i < n + 1; i++){
            buckets[i] = new IndexedDoubleList();
            bes[i] = new ListElem();
            bes[i].elem = new IndexedDouble(0.0, i);
        }
        
    }
    
    /** {@inheritDoc} */
    @Override
    public void nearestPoint(double[] y){
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        //make sure that the buckets are empty!
        for(int i = 0; i < n + 1; i++)
            buckets[i].clear();
        
        double a = 0, b = 0;
        int k = 0;
        for(int i = 0; i < n + 1; i++){
            z[i] = y[i] - Math.round(y[i]);
            k += Math.round(y[i]);
            int bi = n - (int)(Math.floor((n+1)*(z[i]+0.5)));
            bes[i].elem.value = z[i];
            buckets[bi].add(bes[i]);
            a += z[i];
            b += z[i] * z[i];
        }
        
        double D = b - a*a/(n+1);
        int m = 0;
        for(int i = 0; i < n+1; i++){
            
            //for the next bucket test all potential values of m
            //such the (m+k)M with Warren's approximation
            
            //calculate the next m such the (m+k)/M.
            int nextm = M*(k/M + 1);
           
            
            
        }
           
    }
    
    
    /** 
     * Specialised list implementation for the bucket
     * sorting algorithm.  This should be significantly
     * faster than java's list implementations.  It
     * allows a fixed memory implementation.
     * 
     * Also a toArray method is included to allow testing
     * before an implementation of fast sorting is made.
     */
    protected class IndexedDoubleList{
        protected int size;
        protected ListElem current, first;
        protected IndexedDoubleListIterator itr;
        
        public IndexedDoubleList(){
            first = new ListElem();
            current = first;
            current.next = null;
            size = 0;
            itr = new IndexedDoubleListIterator(this);
        }
        
        public boolean add(ListElem e){
            current.next = e;
            current = e;
            current.next = null;
            size++;
            return true;
        }
        
        public void clear(){
            current = first;
            current.next = null;
            size = 0;
        }
        
        public IndexedDoubleListIterator iterator(){
            itr.reset(this);
            return itr;
        }
        
        /** Returns the list as an array of indexed doubles. */
        public IndexedDouble[] toArray(){
            IndexedDouble[] ret = new IndexedDouble[size];
            IndexedDoubleListIterator itra = this.iterator();
            int count = 0;
            while(itra.hasNext()){
                ret[count] = itra.next().elem;
                count++;
            }
            return ret;
        }
        
    }
    
    /** List element for IntList */
    protected class ListElem{
        protected ListElem next;
        protected IndexedDouble elem;
    }
    
    /** An iterator for IntList */
    protected class IndexedDoubleListIterator{
        protected ListElem current;
        
        public IndexedDoubleListIterator(IndexedDoubleList list){
            current = list.first;
        }
        
        public void reset(IndexedDoubleList list){
            current = list.first;
        }
        
        public boolean hasNext(){
            if(current.next == null) return false;
            else return true;
        }
        
        public ListElem next(){
            current = current.next;
            return current;
        }

    }
    
}
