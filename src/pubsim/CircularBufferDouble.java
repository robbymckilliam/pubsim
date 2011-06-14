/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim;

import java.io.Serializable;

/**
 * Circular buffer, ideal for multiply accumulate, FIR filters etc.
 * @author Robby McKilliam
 */
public class CircularBufferDouble implements Serializable {
    
    protected final double[] buffer;
    
    /** Current position in the buffer */
    protected int cpos = 0;
    
    /** buffer length */
    protected final int N;
    
    public CircularBufferDouble(int capacity) {
        buffer = new double[capacity];
        N = capacity;
    }
    
    public void add(double d){
        buffer[cpos] = d;
        cpos = (cpos + 1) % N;
    }
    
    /** 
     * Get the ith element added to the list.
     * Note that the buffer overwrites periodically so it is really
     * the last element added at period N.
     */
    public double get(int i){
        return buffer[Util.mod(i,N)];
    }
    
}
