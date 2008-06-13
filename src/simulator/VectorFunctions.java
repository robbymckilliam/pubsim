/*
 * VectorFunctions.java
 *
 * Created on 29 April 2007, 18:47
 */

package simulator;

import Jama.Matrix;

/**
 * Miscelaneous functions to run on double arrays
 * @author Robby McKilliam
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
     * Return the magnitude squared of the Fourier
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
     * Squared Euclidean distance between two vectors
     */
    public static double distance_between2(Complex[] x, Complex[] s){
        double out = 0.0;
        for(int i = 0; i < x.length; i++)
            out += x[i].minus(s[i]).abs2(); 
        return out;
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
     * Return the vector with each element rounded to
     * the nearest integer.
     * Pre: x.length = y.length
     */
    public static void round(double[] x, double[] y){
        for(int i = 0; i < x.length; i++)
            y[i] = Math.round(x[i]); 
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
     * Return a string for the complex vector
     */
    public static String print(Complex[] x){
        String st = new String();
        for(int i = 0; i < x.length; i++){
            st = st.concat(" " + x[i]);
        }
        return st;
    }
    
    /**
     * Return a string for the vector
     */
    public static String print(double[][] M){
        String st = new String();
        for(int m = 0; m < M.length; m++){
            for(int n = 0; n < M[0].length; n++)
                st = st.concat("\t" + M[m][n]);
            st = st.concat("\n");
        }
        return st;
    }
    
        
    /**
     * Return a string for the vector
     */
    public static String print(Matrix mat){
        return print(mat.getArray());
    }
    
    /**
     * Convolution of two vectors.  This
     * allocates the required memory
     */
    public static double[] conv(double[] x, double[] y){
        double[] r = new double[x.length + y.length - 1];
        for(int t = 0; t < r.length; t++){
            double csum = 0.0;
            for(int i = 0; i < x.length; i++){
                if( t-i >= 0 && t-i < y.length)
                    csum += x[i]*y[t-i];
            }
            r[t] = csum;
        }
        return r;
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
     * Return true if every element in the vectors is equal, esle false.
     */
    public static boolean equal(double[] x, double[] y){
        for(int i = 0; i < x.length; i++)
            if(x[i] != y[i]) return false;
        return true;
    }
    
    /**
     * Return the distance between the two elements in
     * x that are the fathest apart.
     */
    public static double maxDistance(double[] x)
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
    
    /** 
     * y and x and vector, M is a matrix.
     * Performs y = M*x.  
     * PRE: x.length = m, y.length = n, M is n by m matrix
     */
    public static void matrixMultVector(double[][] M, double[] x, double[] y){
        for(int n = 0; n < y.length; n++){
            y[n] = 0;
            for(int m = 0; m < x.length; m++)
                y[n] += M[n][m] * x[m];
        }
    }
    
    /** 
     * Project y parallel to x and return in yp.
     * PRE: y.length == x.length == yp.length
     */
    public static void projectParallel(double[] x, double[] y, double[] yp){
        double xty = dot(x,y);
        double xtx = dot(x,x);
        for(int i = 0; i < x.length; i++)
            yp[i] = xty/xtx*x[i];
    }
    
    /** 
     * Project y orthogonal to x and return in yp.
     * PRE: y.length == x.length == yp.length
     */
    public static void projectOrthogonal(double[] x, double[] y, double[] yp){
        double xty = dot(x,y);
        double xtx = dot(x,x);
        for(int i = 0; i < x.length; i++)
            yp[i] = y[i] - xty/xtx*x[i];
    }
    
    /** 
     * Scalar divide.  Result is returned in y
     * PRE: x.length == y.length
     */
    public static void divide(double[] x, double d, double[] y){
        for(int i = 0; i < x.length; i++)
            y[i] = x[i]/d;
    }
    
    /** 
     * Returns the normalised vector
     * PRE: x.length == y.length
     */
    public static void normalise(double[] x, double[] y){
        double d = magnitude(x);
        for(int i = 0; i < x.length; i++)
            y[i] = x[i]/d;
    }
    
    /** 
     * Returns the transpose of a matrix.  Allocates memory.
     * PRE: x.length == y.length
     */
    public static double[][] transpose(double[][] M1){
        double[][] mat = new double[M1[0].length][M1.length];
        for(int m = 0; m < M1.length; m++){
            for(int n = 0; n < M1[0].length; n++)
                mat[n][m] = M1[m][n];
        }
        return mat;
    }
    
    /** 
     * O(n^3) determinant algorithm that is 'fraction free'
     * and more stable than the LU and trace algorithm in the
     * Jama library.
     * <p>
     * Erwin H. Bareiss, “Sylvester's Identity and Multistep 
     * Integer-Preserving Gaussian Elimination,” 
     * Mathematical Computation 22, 103, pp. 565 – 578, 1968.
     */
    public static double stableDet(Matrix mat){
        
        //handle exceptional cases
        if(mat.getColumnDimension() != mat.getRowDimension())
            throw new IllegalArgumentException("Matrix must be square.");
        if(mat.getRowDimension() == 1) 
            return mat.get(0,0);
        
        return stableDet(mat, 0);          
        
    }
    
    /** Recursive function used by stableDet */
    protected static double stableDet(Matrix mat, int index){
        
        if(mat.getRowDimension() - index == 2) 
            return mat.get(index,index)*mat.get(index+1,index+1) 
                    - mat.get(index,index+1)*mat.get(index+1,index);
        
        for(int i = index + 1; i < mat.getRowDimension(); i++){
            for(int j = index + 1; j < mat.getRowDimension(); j++){
                double sub = mat.get(i,index) * mat.get(index,j);
                mat.set(i,j, mat.get(i,j) * mat.get(index,index) - sub);
            }
        }
        double det = stableDet(mat, index + 1);
        for(int i = index; i < mat.getRowDimension() - 2; i++)
            det /= mat.get(index, index);
        
        return det;
    }
    
    
    
}
