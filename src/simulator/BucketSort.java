/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

/**
 * Bucket sort implementation.
 * Incomplete!
 * @author Robby McKilliam
 */
public class BucketSort<T> {

    int numBuckets, numData;
    double startRange, endRange;
    int[] bucketSize;
    T[] sorted;

    public BucketSort(int numBuckets, int numData,
            double startRange, double endRange){
        this.numBuckets = numBuckets;
        this.startRange = startRange;
        this.endRange = endRange;
        bucketSize = new int[numBuckets];
        sorted = (T[]) new Object[numData];
        this.numData = numData;
    }

    public T[] sort(T[] data){

        //clear the bucketSize array
        for(int i = 0; i < numBuckets; i++){
            bucketSize[i] = 0;
        }

        double interval = Math.abs(startRange - endRange);
        //count number in each bucket
        for(int i = 0; i < numData; i++){
            
        }
        return sorted;
    }

}
