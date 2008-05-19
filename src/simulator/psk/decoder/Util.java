/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.psk.decoder;

/**
 * Utility class PSK signals.
 * @author Robby McKilliam
 */
public class Util{
    
    /** 
     * Returns the differential encoded version of x.  This allocates
     * the required memory.
     * @param M for M-PSK
     */
    public static double[] differentialEncodedSignal(double[] x, int M){
        double[] y = new double[x.length-1];        
        for(int i = 0; i<x.length-1; i++)
            y[i] = (double)mod((int)(x[i+1]-x[i]),M);
        return y;
    }
    
    /** 
     * Takes x mod y and works for negative numbers.  Ie is not
     * just a remainder like java's % operator.
     */
    private static int mod(int x, int y){
        int t = x%y;
        if(x < 0) t+=y;
        return t;
    }
    
    /**
     * Test true if 2 codewords are equal when differentially encoded.
     * Otherwise returns false.  x and y must have equal length.
     * @param M for M-PSK
     */
    public static boolean differentialEncodedEqual(double[] x, double[] y, int M){
         if(y.length != x.length) 
             throw new Error("x and y must have equal length");
         
         for(int i = 0; i<x.length-1; i++){
            int xdiff = mod((int)(x[i+1]-x[i]),M);
            int ydiff = mod((int)(y[i+1]-y[i]),M);
            if(xdiff != ydiff) return false;
         }
         return true;
    }
    
}
