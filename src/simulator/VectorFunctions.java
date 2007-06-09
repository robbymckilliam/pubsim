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
     * vectors of any length.  Assuming that this
     * estimator performs well, then it would be
     * necessary to write an FFT here so that the
     * estimator runs in o(nlog(n)) and not o(n^2).
     * <p>
     * Given that the input vector @param x is
     * gauranteed to only contain 0's and 1's it may
     * be possible to write and very fast fourier transform.
     */
    public static void slowFT(double[] x, double[] Xi, double[] Xr){
        
        int N = x.length;
        
        if ( Xi.length != N) Xi = new double[N];
        if ( Xi.length != N) Xr = new double[N];
        
        for (int k = 0; k < N; k++){
            for (int m = 0; m < N; m++){
                Xr[k] += x[m] * Math.cos(k*m*2*Math.PI/N);
                Xi[k] += x[m] * Math.sin(k*m*2*Math.PI/N);
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
            out[i] += x[i] - y[i]; 
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
     * Return the sum of a vector
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
     * Return the mean value of a vector
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
     * x that are the farest apart.
     */
    public static double max_distance(double[] x)
    {
     return max(x) - min(x);   
    }
}
