/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import java.util.Collection;
import java.util.Iterator;
import simulator.FastSelection;
import simulator.IndexedDouble;
import simulator.VectorFunctions;

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
    
    protected int numBuckets;
    
    protected FastSelection fselect;
    
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
        z = new double[n+1];
        
        //setup the buckets.
        numBuckets = (n+1)/M;
        
        buckets = new IndexedDoubleList[numBuckets];
        for(int i = 0; i < numBuckets; i++)
            buckets[i] = new IndexedDoubleList();
                    
        bes = new ListElem[n+1];
        for(int i = 0; i < n + 1; i++){
            bes[i] = new ListElem();
            bes[i].elem = new IndexedDouble(0.0, i);
        }
        
        fselect = new FastSelection(n+1);
        
    }
    
    /** {@inheritDoc} */
    @Override
    public void nearestPoint(double[] y){
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        //make sure that the buckets are empty!
        for(int i = 0; i < numBuckets; i++)
            buckets[i].clear();
        
        double a = 0, b = 0;
        int k = 0;
        for(int i = 0; i < n+1; i++){
            z[i] = y[i] - Math.round(y[i]);
            k += Math.round(y[i]);
            bes[i].elem.value = -z[i];
            bes[i].elem.index = i;
            int bi = numBuckets - 1 - (int)(Math.floor(numBuckets*(z[i]+0.5)));
            buckets[bi].add(bes[i]);
            a += z[i];
            b += z[i] * z[i];
        }
        
        //System.out.println("\n\n k = " + k);
        
        double D = Double.POSITIVE_INFINITY;
        int d = 0;
        int m = 0, bestbucket = 0;
        for(int i = 0; i < numBuckets; i++){
            
            //approximate value of z[i] in the bucket
            double za = (i+0.5)/numBuckets;
            
            //calculate the minimum of the parabola approximation
            double p = (1 - 2*za)*(n+1)/2.0 + a;
            
            /*
            System.out.println();
            Iterator itrp = buckets[i].iterator();
            System.out.print("bucket = ");  
            while(itrp.hasNext())
                System.out.print(itrp.next() + ", ");
            System.out.println();
            */
            
            
            //test first value of j
            int j = nearestMultInRange(p + k + d, k + d, k + d + buckets[i].size()) - k - d;
            fselect.select(j, buckets[i]);
            
            if(!fselect.beforeAndIncluding().isEmpty() || j == 0){
                double ad = a;
                double bd = b;
                Iterator itr = fselect.beforeAndIncluding().iterator();
                while(itr.hasNext()){
                    int ind = ((IndexedDouble)itr.next()).index;
                    ad -= 1;
                    bd += -2*z[ind] + 1;   
                }
                double dist = bd - ad*ad/(n+1);
                if(dist < D){
                    D = dist;
                    m = j;
                    bestbucket = i;
                    //System.out.println("j = " + j + ", j - d = " + (j-d) + ", j + k = " + (j+k));
                }
            }
                
            
            
            /*
            System.out.println("j - d = " + (j - d));
            itrp = fselect.beforeAndIncluding().iterator();
            System.out.print("before = ");  
            while(itrp.hasNext())
                System.out.print(itrp.next() + ", ");
            System.out.println();
            System.out.println(fselect.getElement());
            itrp = fselect.after().iterator();
            System.out.print("after = ");  
            while(itrp.hasNext())
                System.out.print(itrp.next() + ", ");  
            System.out.println();
            */
            
            
            
            //test second value of j
            j += M*Math.signum(j - p);
            fselect.select(j, buckets[i]);
            
            if(!fselect.beforeAndIncluding().isEmpty() || j == 0){
                double ad = a;
                double bd = b;
                Iterator itr = fselect.beforeAndIncluding().iterator();
                while(itr.hasNext()){
                    int ind = ((IndexedDouble)itr.next()).index;
                    ad -= 1;
                    bd += -2*z[ind] + 1;   
                }
                double dist = bd - ad*ad/(n+1);
                if(dist < D){
                    D = dist;
                    m = j;
                    bestbucket = i;
                    //System.out.println("j' = " + j + ", j' - d = " + (j-d) + ", j + k = " + (j+k));
                }
            }
            
            /*
            System.out.println("j' - d = " + (j - d));
            itrp = fselect.beforeAndIncluding().iterator();
            System.out.print("before = ");  
            while(itrp.hasNext())
                System.out.print(itrp.next() + ", ");
            System.out.println();
            System.out.println(fselect.getElement());
            itrp = fselect.after().iterator();
            System.out.print("after = ");  
            while(itrp.hasNext())
                System.out.print(itrp.next() + ", ");  
            System.out.println();
            */
            
            //add all the indices on for the next bucket
            IndexedDoubleListIterator itrd = buckets[i].iterator();
            while(itrd.hasNext()){
                int ind = itrd.next().index;
                a -= 1;
                b += -2*z[ind] + 1; 
                d++;
            }
           
            
        }
        
        //get the first element in the Bresenham set
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        //add all the buckets before the best on
        for(int i = 0; i < bestbucket; i++){
            IndexedDoubleListIterator itr = buckets[i].iterator();
            while(itr.hasNext())
                u[itr.next().index]++;
        }
        
        //fast select the best element of the best bucket and add the
        //previous elements
        fselect.select(m, buckets[bestbucket]);
        Iterator itr = fselect.beforeAndIncluding().iterator();
        while(itr.hasNext())
            u[((IndexedDouble)itr.next()).index]++;
        
        //project index to nearest lattice point
        Anstar.project(u, v);
        
    }
    
    /** Returns the integer multiple of M to v */
    public static int nearestMultM(double v, int M){
        return M*(int)Math.round(v/M);
    }
    
    /** Returns the integer multiple of M to v */
    protected int nearestMultM(double v){
        return M*(int)Math.round(v/M);
    }
    
    protected int nearestMultInRange(double v, double min, double max){
        return nearestMultInRange(v, min, max, M);
    }
    
     public static int nearestMultInRange(double v, double min, double max, int M){
        int j = nearestMultM(v, M);
        if( j < min ){
            j = nearestMultM(min, M);
            if(j < min)
                j += M;
        }else if( j > max ){
            j = nearestMultM(max, M);
            if(j > max)
                j -= M;
        }
        return j;
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
    protected class IndexedDoubleList implements Collection{
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
                ret[count] = itra.next();
                count++;
            }
            return ret;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size==0;
        }

        public boolean contains(Object o) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object[] toArray(Object[] a) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean add(Object e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean containsAll(Collection c) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean addAll(Collection c) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean removeAll(Collection c) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean retainAll(Collection c) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
    /** List element for IntList */
    protected class ListElem{
        protected ListElem next;
        protected IndexedDouble elem;
    }
    
    /** An iterator for IntList */
    protected class IndexedDoubleListIterator implements Iterator{
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
        
        public IndexedDouble next(){
            current = current.next;
            return current.elem;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
    
}
