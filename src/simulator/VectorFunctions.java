/*
 * VectorFunctions.java
 *
 * Created on 29 April 2007, 18:47
 */

package simulator;

/**
 * Miscelaneous functions to run on double arrays
 * @author Robby
 */
public class VectorFunctions {
    
    /**
     * A slow Fourier Tranform.  It will work on
     * vectors of any length.
     */
    public static void slowFT(double[] x, double[] Xi, double[] Xr){
        
        int N = x.length;
        
        if ( Xi.length != N) Xi = new double[N];
        if ( Xi.length != N) Xr = new double[N];
        
        for (int k = 0; k < N; k++){
            for (int m = 0; m < N; m++){
                Xr[k] += x[m] * Math.cos(k*m*2*Math.PI/N);
                Xi[k] -= x[m] * Math.sin(k*m*2*Math.PI/N);
            }
        }
    }
    
    /**
     * Return the magnitude squared of the fourier
     * tranform of @param x.
     */
    public static double[] abs2FT(double[] x){
        double[] Xi = new double[x.length],
                 Xr = new double[x.length];
        slowFT(x, Xi, Xr);
        double[] out = new double[x.length];
        for(int i = 0; i < x.length; i++)
            out[i] = Xi[i]*Xi[i] + Xr[i]*Xr[i];
        return out;
    }
    
     /**
     * Euclidean distance between two vectors
     */
    public static double distance_between(double[] x, double[] s){
        double out = 0.0;
        for(int i = 0; i < x.length; i++)
            out += Math.pow(x[i] - s[i], 2.0); 
        return Math.sqrt(out);
    }
    
    /**
     * Squared Euclidean distance between two vectors
     */
    public static double distance_between2(double[] x, double[] s){
        double out = 0.0;
        for(int i = 0; i < x.length; i++)
            out += Math.pow(x[i] - s[i], 2.0); 
        return out;
    }
    
    /**
     * Copy vector. <br>
     * PRE: x.length < y.length
     */
    public static void copy(double[] x, double[] y){
        for(int i=0; i<x.length; i++)
            y[i] = x[i]; 
    }
    
    /**
     * angle between two vectors
     */
    public static double angle_between(double[] x, double[] y){
        return Math.acos(dot(x,y)/(magnitude(x)*magnitude(y)));
    }
    
    /**
     * vector subtraction
     */
    public static double[] subtract(double[] x, double[] y){
        double[] out = new double[x.length];
        for(int i = 0; i < x.length; i++)
            out[i] = x[i] - y[i]; 
        return out;
    }
    
    /**
     * vector addition
     */
    public static double[] add(double[] x, double[] y){
        double[] out = new double[x.length];
        for(int i = 0; i < x.length; i++)
            out[i] = x[i] + y[i]; 
        return out;
    }
    
    /**
     * Return the sum of a vector
     */
    public static double sum(double[] x){
        double out = 0.0;
        for(int i = 0; i < x.length; i++)
            out += x[i]; 
        return out;
    }
    
    /**
     * Return the squared sum of a vector
     */
    public static double sum2(double[] x){
        double out = 0.0;
        for(int i = 0; i < x.length; i++)
            out += x[i]*x[i]; 
        return out;
    }
    
    /**
     * Return the mean value of a vector
     */
    public static double mean(double[] x){
        return sum(x)/x.length;
    }
    
    /**
     * Return the magnitude of the vector
     */
    public static double magnitude(double[] x){
        return Math.sqrt(sum2(x));
    }
    
    /**
     * Return the maximum value of a vector
     */
    public static double max(double[] x){
        double out = 0;
        for(int i = 0; i < x.length; i++)
            if(x[i]>out) out = x[i]; 
        return out;
    }
    
    /**
     * Return true if the vector is increasing
     */
    public static boolean increasing(double[] x){
        for(int i = 0; i < x.length-1; i++)
            if(x[i] > x[i+1]) return false;
        return true;
    }
    
    /**
     * Return a string for the vector
     */
    public static String print(double[] x){
        String st = new String();
        for(int i = 0; i < x.length; i++){
            st = st.concat(" " + x[i]);
        }
        return st;
    }
    
    /**
     * Vector dot/inner product
     */
    public static double dot(double[] x, double[] y){
        double sum = 0.0;
        for(int i = 0; i < x.length; i++)
            sum += x[i]*y[i];
        return sum;
    }
    
    /**
     * Return the min value of a vector
     */
    public static double min(double[] x){
        double out = 0.0;
        for(int i = 0; i < x.length; i++)
            if(x[i]<out) out = x[i]; 
        return out;
    }
    
    /**
     * Return the distance between the two elements in
     * x that are the fathest apart.
     */
    public static double max_distance(double[] x)
    {
     return max(x) - min(x);   
    }
    
    /** 
     * Complex dot product using Hermition transpose of x. 
     * <p>
     * Pre: x.length = y.length
     * <p>
     * It would be interesting to know what is faster out
     * of the two ways of calculating this bellow.  One
     * of them is commented out.
     */
    public static Complex dot(Complex[] x, Complex[] y){
        //double re = 0.0, im = 0.0;
        Complex ret = new Complex(0,0);
        for(int i = 0; i < x.length; i++){
            ret = ret.plus(x[i].conjugate().times(y[i]));
            //re += x[i].re()*y[i].re() + x[i].im()*y[i].im();
            //im += x[i].re()*y[i].im() - x[i].im()*y[i].re();
        }
        //return new Complex(re, im);
        return ret;
    }
    
    
}
