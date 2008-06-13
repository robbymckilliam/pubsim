/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.psk.decoder;

import simulator.Complex;

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
    public static int mod(int x, int y){
        int t = x%y;
        if(t < 0) t+=y;
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
    
    /**
     * Returns the number of symbols errors in the codewords assuming
     * differential encoding.
     * @param M for M-PSK
     */
    public static int differentialEncodedSymbolErrors(double[] x, double[] y, int M){
         if(y.length != x.length) 
             throw new Error("x and y must have equal length");
         
         int errors = 0;
         for(int i = 0; i<x.length-1; i++){
            int xdiff = mod((int)(x[i+1]-x[i]),M);
            int ydiff = mod((int)(y[i+1]-y[i]),M);
            if(xdiff != ydiff) errors++;
         }
         return errors;
    }
    
    /**
     * Returns the number of symbols errors in the codewords assuming
     * differential encoding.
     * @param M for M-PSK
     */
    public static int SymbolErrors(double[] x, double[] y, int M){
         if(y.length != x.length) 
             throw new Error("x and y must have equal length");
         
         int errors = 0;
         for(int i = 0; i<x.length; i++){
             int xmod = mod((int)Math.round(x[i]), M);
             int ymod = mod((int)Math.round(y[i]), M);
            if(xmod != ymod) errors++;
         }
         return errors;
    }
    
    /**
     * Returns the number of bit errors in the codewords assuming
     * differential encoding.  Assumes differential encoding and that
     * M is a power of 2.  The number of bit errors will be incorrect
     * if M is not a power of 2.
     * @param M for M-PSK
     */
    public static int differentialEncodedBitErrors(double[] x, double[] y, int M){
         if(y.length != x.length) 
             throw new Error("x and y must have equal length");
         
         int errors = 0;
         for(int i = 0; i<x.length-1; i++){
            int xdiff = mod((int)(x[i+1]-x[i]),M);
            int ydiff = mod((int)(y[i+1]-y[i]),M);
            //System.out.println(" xdiff = " + xdiff + ", ydiff = " + ydiff + ", errors = " + mod(xdiff-ydiff, M/2+1));
            errors += mod(xdiff-ydiff, M/2+1);
         }
         return errors;
    }
    
    /**
     * Returns the number of bit errors in the codewords.  Assumens that
     * M is a power of 2.  The number of bit errors will be incorrect
     * if M is not a power of 2.
     * @param M for M-PSK
     */
    public static int bitErrors(double[] x, double[] y, int M){
        if(y.length != x.length) 
             throw new Error("x and y must have equal length");
         
         int errors = 0;
         for(int i = 0; i<x.length; i++){
            errors += mod((int)Math.round(x[i]-y[i]), M/2+1);
         }
         return errors;
    }
    
    /**
     * Returns the true if the codewords are not equal 
     * @param M for M-PSK
     */
    public static boolean codewordError(double[] x, double[] y, int M){
         if(y.length != x.length) 
             throw new Error("x and y must have equal length");
         
         for(int i = 0; i<x.length; i++){
             int xmod = mod((int)Math.round(x[i]), M);
             int ymod = mod((int)Math.round(y[i]), M);
            if(xmod != ymod) return true;
         }
         return false;
    }
    
    
}
