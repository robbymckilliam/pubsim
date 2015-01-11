/*
 * VectorFunctions.java
 *
 * Created on 29 April 2007, 18:47
 */
package pubsim;

import Jama.Matrix;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import static pubsim.Range.range;

/**
 * Miscellaneous functions to run on arrays/vectors/matrices.
 * @author Robby McKilliam
 * Updated by Vaughan Clarkson to make the Givens rotation safe when
 * a fat matrix is input.
 */
public final class VectorFunctions {
    
    /** Returns an array of ints of length n each element equal to c */
    public static int[] filledArray(int n, int c){
        int[] array = new int[n];
        Arrays.fill(array, c);
        return array;
    }

    /**
     * A slow Fourier Tranform.  It will work on
     * vectors of any length.
     */
    public static void slowFT(double[] x, double[] Xi, double[] Xr) {

        int N = x.length;

        if (Xi.length != N) {
            Xi = new double[N];
        }
        if (Xi.length != N) {
            Xr = new double[N];
        }

        for (int k = 0; k < N; k++) {
            for (int m = 0; m < N; m++) {
                Xr[k] += x[m] * Math.cos(k * m * 2 * Math.PI / N);
                Xi[k] -= x[m] * Math.sin(k * m * 2 * Math.PI / N);
            }
        }
    }

    /** Convert and integer array to a double array */
    public static double[] intArrayToDoubleArray(int[] x){
        double[] y = new double[x.length];
        for(int n = 0; n < x.length; n++) y[n] = x[n];
        return y;
    }
    
    /**
     * Zero padded Fourier Transform. Pads so that the resulting transform is p times
     * the length of x.
     * This is not a FFT and requires O(N^2) operations.
     * UNTESTED!
     */
    public static Complex[] PaddedFT(Complex[] x, double p) {

        int N = (int) (x.length*p);
        Complex[] X = new Complex[N];
        int t = (x.length - N)/2;

        for (int k = 0; k < N; k++) {
            X[k] = new Complex(0,0);
            for (int m = 0; m < N; m++) {
                double re = Math.cos(k * m * 2 * Math.PI / N);
                double im = Math.sin(k * m * 2 * Math.PI / N);
                Complex ejw = new Complex(re, im);
                if(m - t >= 0)
                    X[k] = X[k].plus(x[m-t].times(ejw));
            }
        }

        return X;
    }

    /** multiple all elements in x by s in place */
    public static void multiplyInPlace(double[] x, double s){
        for(int n = 0; n < x.length; n++){
            x[n] *= s;
        }
    }

    /**
     * Return the magnitude squared of the Fourier
     * tranform of @param x.
     */
    public static double[] abs2FT(double[] x) {
        double[] Xi = new double[x.length],
                Xr = new double[x.length];
        slowFT(x, Xi, Xr);
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            out[i] = Xi[i] * Xi[i] + Xr[i] * Xr[i];
        }
        return out;
    }

    /**
     * Euclidean distance between two vectors
     */
    public static double distance_between(double[] x, double[] s) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += Math.pow(x[i] - s[i], 2.0);
        }
        return Math.sqrt(out);
    }
    
    /**
     * Euclidean distance between two vectors
     */
    public static double distance_between(Integer[] x, Double[] s) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += Math.pow(x[i] - s[i], 2.0);
        }
        return Math.sqrt(out);
    }
    
    /**
     * Euclidean distance between two vectors
     */
    public static double distance_between(Double[] x, Double[] s) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += Math.pow(x[i] - s[i], 2.0);
        }
        return Math.sqrt(out);
    }

    /**
     * Squared Euclidean distance between two vectors
     */
    public static double distance_between2(double[] x, double[] s) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += Math.pow(x[i] - s[i], 2.0);
        }
        return out;
    }

    /**
     * Squared Euclidean distance between two vectors
     */
    public static double distance_between2(Complex[] x, Complex[] s) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += x[i].minus(s[i]).abs2();
        }
        return out;
    }

    /**
     * angle between two vectors
     */
    public static double angle_between(double[] x, double[] y) {
        return Math.acos(dot(x, y) / (magnitude(x) * magnitude(y)));
    }

    /**
     * vector subtraction x - y
     */
    public static double[] subtract(double[] x, double[] y) {
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            out[i] = x[i] - y[i];
        }
        return out;
    }

    /**
     * vector subtraction x - y.  Stores result in res. Does not
     * allocate memory.
     * Requires res.length == y.length == x.length.
     */
    public static void subtract(double[] x, double[] y, double[] res) {
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            res[i] = x[i] - y[i];
        }
    }

    /**
     * vector addition x + y.  Stores result in res. Does not
     * allocate memory.
     * Requires res.length == y.length == x.length.
     */
    public static void add(double[] x, double[] y, double[] res) {
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            res[i] = x[i] + y[i];
        }
    }

    /**
     * Return x*s into vector res.
     * Requires res.length == x.length.
     */
    public static void times(double[] x, double s, double[] res) {
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            res[i] = x[i]*s;
        }
    }

    /**
     * Return x*s into vector res.
     * Requires res.length == x.length.
     */
    public static void times(Complex[] x, Complex s, Complex[] res) {
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            res[i] = x[i].times(s);
        }
    }

    /**
     * vector addition x + y
     */
    public static double[] add(double[] x, double[] y) {
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            out[i] = x[i] + y[i];
        }
        return out;
    }


    /**
     * calculate x mod m for each element in x in place.
     * Assumes that the element in x are integers.
     */
    public static void modInPlace(double[] x, int m) {
        for (int i = 0; i < x.length; i++) {
            x[i] = pubsim.Util.mod((int)x[i], m);
        }
    }


    /**
     * Return a vector of length N with all elements equal to 1.0
     */
    public static double[] ones(int N) {
        double[] out = new double[N];
        for (int i = 0; i < N; i++) {
            out[i] = 1.0;
        }
        return out;
    }
    
    /** A column vector of ones, returned as a matrix */
    public static Matrix onesColumn(int N){
        Matrix B = new Matrix(N,1);
        for (int n = 0; n < N; n++) B.set(n,0,1.0);
        return B;
    }
    
     /**
     * Return the product of elements in a vector
     */
    public static long prod(int[] x) {
        int out = 1;
        for (int i = 0; i < x.length; i++) {
            out *= x[i];
        }
        return out;
    }

    /**
     * Return the sum of a vector
     */
    public static double prod(double[] x) {
        double out = 1.0;
        for (int i = 0; i < x.length; i++) {
            out *= x[i];
        }
        return out;
    }
    
    /**
     * Return the sum of a vector
     */
    public static double sum(double[] x) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += x[i];
        }
        return out;
    }
    
    /**
     * Return the sum of a vector
     */
    public static int sum(int[] x) {
        int out = 0;
        for (int i = 0; i < x.length; i++) {
            out += x[i];
        }
        return out;
    }
    
    /**
     * Return the sum of a vector
     */
    public static int sum(Integer[] x) {
        int out = 0;
        for (int i = 0; i < x.length; i++) {
            out += x[i];
        }
        return out;
    }

    /**
     * Return the sum of a vector
     */
    public static Complex sum(Complex[] x) {
        double outr = 0.0, outi = 0.0;
        for (int i = 0; i < x.length; i++) {
            outr += x[i].re();
            outi += x[i].im();
        }
        return new Complex(outr, outi);
    }

    /**
     * Return the vector with each element rounded to
     * the nearest integer.
     * Pre: x.length = y.length
     */
    public static void round(double[] x, double[] y) {
        for (int i = 0; i < x.length; i++) {
            y[i] = Math.round(x[i]);
        }
    }
    
    /**
     * Return the vector with each element rounded to
     * the nearest integer.
     * Pre: x.length = y.length
     */
    public static double[] round(double[] x) {
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = Math.round(x[i]);
        }
        return y;
    }
    
    /**
     * Return the vector with each element rounded to
     * the nearest integer.
     * Pre: x.length = y.length
     */
    public static void round(Double[] x, Integer[] y) {
        for (int i = 0; i < x.length; i++) {
            y[i] = (int) Math.round(x[i]);
        }
    }

    /**
     * Return the floor of each element in the vector
     * the nearest integer.
     * Pre: x.length = y.length
     */
    public static void floor(double[] x, double[] y) {
        for (int i = 0; i < x.length; i++) {
            y[i] = Math.floor(x[i]);
        }
    }

    /**
     * Return the floor of each element in the vector
     * the nearest integer.
     */
    public static double[] floor(double[] x) {
        double[] y = new double[x.length];
        floor(x, y);
        return y;
    }

    /**
     * Return the x - floor(x).
     */
    public static double[] fracpart(double[] x) {
        double[] y = new double[x.length];
        fracpart(x, y);
        return y;
    }

    /**
     * Return the x - floor(x) into y.
     * Pre: x.length = y.length
     */
    public static void fracpart(double[] x, double[] y) {
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] - Math.floor(x[i]);
        }
    }
    
    /** 
     * Returns true if all the elements in y are zero, i.e. less than tol
     */ 
    public static boolean isZero(double[] y, double tol){
        boolean t = true;
        int c = 0;
        while(t && c < y.length){
            t = Math.abs(y[c]) < tol;
            c++;
        }
        return t;
    }

    /**
     * Householder reflection of x about r.  Stores result in y.
     * Requires
     */
    public static void reflect(double[] r, double[] x, double[] y){
        if((r.length != x.length) || (r.length != y.length))
            throw new ArrayIndexOutOfBoundsException("r, x, and y are not equal length");
        double rmag = sum2(r);
        double alpha = dot(x, r)/rmag;
        for(int i = 0; i < x.length; i++){
            y[i] = x[i] - 2*alpha*r[i];
        }
    }

    /**
     * Householder reflection of x about r.  Allocates memory and returns.
     * Requires
     */
    public static double[] reflect(double[] r, double[] x){
        double[] y = new double[x.length];
        reflect(r, x, y);
        return y;
    }

    /**
     * Return a vector of length N with all elements equal to 1.0
     */
    public static boolean allElementsEqual(double[] x) {
        for (int i = 0; i < x.length-1; i++) {
            if(x[i] != x[i+1]) return false;
        }
        return true;
    }


    /**
     * Return a vector of zero mean var = 1 gaussian
     * noise.
     */
    public static double[] randomGaussian(int length) {
        double[] x = new double[length];
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            x[i] = r.nextGaussian();
        }
        return x;
    }

    /**
     * Return a vector of zero mean var = 1 complex
     * noise i.e. Raylieh noise.
     */
    public static Complex[] randomComplex(int length) {
        Complex[] x = new Complex[length];
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            x[i] = new Complex(r.nextGaussian(), r.nextGaussian());
        }
        return x;
    }
    
    /**
     * Return a vector of Gaussian noise.
     */
    public static double[] randomGaussian(int length, double mean, double var) {
        double[] x = new double[length];
        Random r = new Random();
        double std = Math.sqrt(var);
        for (int i = 0; i < length; i++) {
            x[i] = r.nextGaussian()*std + mean;
        }
        return x;
    }
    
    /**
     * Apply a Given's rotation to matrix M.  The rotation will
     * make M[m2,n] = 0.  It affects the rows m1 and m2 in M.
     * In this implementation m2 > m1 so it's only applicable to
     * lower triangular matrixes (i.e. it can only be used to zero lower
     * triangular elements.  For the purpose of QR decomposition it
     * can only compute upper triangular R matrices).
     */
    public static void givensRotate(Matrix M, int m1, int m2, int n){
        if(n > M.getColumnDimension()-1 || m2 > M.getRowDimension())
            throw new RuntimeException("Given's rotation parameters outside matrix size.");
        if(m1 >= m2)
            throw new RuntimeException("This Given's implementation requires m1 < m2");
        
        double a = M.get(m1, n);
        double b = M.get(m2, n);
        double d = 1.0 / Math.sqrt(a*a + b*b);
        double c = a*d;
        double s = b*d;
	if (Double.isInfinite(d)) { // be safe when M is fat
	    c = 1;
	    s = 0;
	}
	for(int i = 0; i < M.getColumnDimension(); i++){
	    double v1 = M.get(m1, i);
	    double v2 = M.get(m2, i);
	    M.set(m1, i, c*v1 + s*v2);
	    M.set(m2, i, -s*v1 + c*v2);
	}

    }

    /**
     * Compute the mth difference of a vector.
     * i.e. the mth psuedoderivative.
     */
    public static double[] mthDifference(double[] y, int m){
        double[] d = y;
        for(int i = 1; i<=m; i++){
            d = firstDifference(d);
        }
        return d;
    }
    
    /**
     * Compute the mth difference of a vector.
     * i.e. the mth psuedoderivative.
     */
    public static double[] mthDifference(Double[] y, int m){
        double[] d = new double[y.length];
        for(int i = 0; i < y.length; i++) d[i] = y[i];
        for(int i = 1; i<=m; i++){
            d = firstDifference(d);
        }
        return d;
    }

    /**
     * Compute the first difference of a vector.
     * i.e. psuedoderivative.
     */
    public static double[] firstDifference(double[] y){
        double[] d = new double[y.length - 1];
        for(int i = 0; i < d.length; i++){
            d[i] = y[i+1] - y[i];
        }
        return d;
    }

    /**
     * Return the squared sum of a vector
     */
    public static double sum2(double[] x) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += x[i] * x[i];
        }
        return out;
    }

    /**
     * Return the l-norm of a vector, i.e. sum of elements to the lth power
     */
    public static double norm(int l, double[] x) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += Math.pow(x[i],l);
        }
        return out;
    }


    /**
     * Return a vector of length n with zeros everywhere
     * except the e[i] = 1.0
     */
    public static double[] eVector(int i, int n) {
        double[] e = new double[n];
        e[i] = 1.0;
        return e;
    }
    
    /**
     * Return a column vector of length n with zeros everywhere
     * except the e[i] = 1.0
     */
    public static Matrix eColumn(int i, int n) {
        Matrix e = new Matrix(n,1,0.0);
        e.set(i,0,1.0);
        return e;
    }
    
    /**
     * Return a row vector of length n with zeros everywhere
     * except the e[i] = 1.0
     */
    public static Matrix eRow(int i, int n) {
        Matrix e = new Matrix(1,n,0.0);
        e.set(0,i,1.0);
        return e;
    }

    /**
     * Return the mean value of a vector
     */
    public static double mean(double[] x) {
        return sum(x) / x.length;
    }

    /**
     * Return the magnitude of the vector
     */
    public static double magnitude(double[] x) {
        return Math.sqrt(sum2(x));
    }

    /**
     * Return the magnitude squares of complex vector
     */
    public static double magnitude2(Complex[] x) {
        double m = 0.0;
        for(int n = 0; n < x.length; n++){
            double re = x[n].re();
            double im = x[n].im();
            m += re*re + im*im;
        }
        return m;
    }

    /**
     * Return the maximum value of a vector
     */
    public static double max(double[] x) {
        double out = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] > out) {
                out = x[i];
            }
        }
        return out;
    }

    /**
     * Return true if the vector is increasing
     */
    public static boolean increasing(double[] x) {
        for (int i = 0; i < x.length - 1; i++) {
            if (x[i] > x[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Return true if the vector is increasing
     */
    public static boolean increasing(Integer[] x) {
        for (int i = 0; i < x.length - 1; i++) {
            if (x[i] > x[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return a string for the vector
     */
    public static String print(double[] x) {
        if(x == null) return "null";
        String st = new String();
        for (int i = 0; i < x.length; i++) {
            st = st.concat(" " + x[i]);
        }
        return st;
    }

    /**
     * Return a string for the complex vector
     */
    public static String print(Complex[] x) {
        if(x == null) return "null";
        String st = new String();
        for (int i = 0; i < x.length; i++) {
            st = st.concat(" " + x[i]);
        }
        return st;
    }
    
    /**
     * Return a string for the int[]
     */
    public static String print(int[] x) {
        if(x == null) return "null";
        String st = new String();
        for (int i = 0; i < x.length; i++) {
            st = st.concat(" " + x[i]);
        }
        return st;
    }
    
    /**
     * Return a string for the complex vector
     */
    public static String print(Object[] x) {
        String st = new String();
        for (int i = 0; i < x.length; i++) {
            if(x[i] != null)
                st = st.concat(" " + x[i].toString());
            else
                st = st.concat(" null");
        }
        return st;
    }
   
    /**
     * Return a string for the vector
     */
    public static String print(double[][] M) {
        if(M == null) return "null";
        String st = new String();
        for (int m = 0; m < M.length; m++) {
            for (int n = 0; n < M[0].length; n++) {
                st = st.concat("\t" + M[m][n]);
            }
            st = st.concat("\n");
        }
        return st;
    }
    
        /**
     * Return a string for the vector
     */
    public static String printForMathematica(double[][] M) {
        if(M == null) return "null";
        String st = new String();
        st = st.concat("{");
        for (int m = 0; m < M.length; m++) {
            st = st.concat("{");
            for (int n = 0; n < M[0].length; n++) {
                st = st.concat(" " + M[m][n]);
                if(n != M[0].length - 1) st = st.concat(",");
            }
            if(m != M.length - 1)  st = st.concat("},\n");
            else st = st.concat("}");
        }
        st = st.concat("}");
        return st;
    }

    /**
     * Return a string for the matrix of objects. Just call's toString.
     */
    public static String print(Object[][] M) {
        if(M == null) return "null";
        String st = new String();
        for (int m = 0; m < M.length; m++) {
            for (int n = 0; n < M[0].length; n++) {
                st = st.concat("\t" + M[m][n].toString());
            }
            st = st.concat("\n");
        }
        return st;
    }


    /**
     * Return a string for the vector
     */
    public static String print(Matrix mat) {
        if(mat == null) return "null";
        return print(mat.getArray());
    }
    
    /**
     * Return a string for the vector
     */
    public static String printForMathematica(Matrix mat) {
        if(mat == null) return "null";
        return printForMathematica(mat.getArray());
    }

    /**
     * Packs the vector y rowise into a double[M][N]
     * where N = y.length/numrows;  Zero pads if numrows does
     * not divide y.length;
     */
    public static double[][] packRowiseToMatrix(double[] y, int numrows){
        int M = numrows;
        int N = y.length/M;
        double[][] u = new double[M][N];
        int i = 0;
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                if(i < y.length)
                    u[m][n] = y[i];
                i++;
            }
        }
        return u;
    }

    /**
     * Packs the matrix M rowise into a double[] y.
     * Assumes that y is preallocated with y.length being
     * greater than the number of element in M.
     */
    public static void packRowiseToArray(Matrix Mat, double[] y){
        int M = Mat.getRowDimension();
        int N = Mat.getColumnDimension();
        if(y.length < M*N)
            throw new ArrayIndexOutOfBoundsException("y.length must be " +
                    "greater than the number of elements in the matrix");
        int count = 0;
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                y[count] = Mat.get(m,n);
                count++;
            }
        }
    }
    
    /** 
     * Adds x to the front of M.  Assumes M is a column matrix
     */
    public static Matrix prependColumnMatrix(Matrix B, double x){
        int M = B.getRowDimension();
        int N = B.getColumnDimension();
        if(N != 1) throw new ArrayIndexOutOfBoundsException("M must be a column matrix");
        Matrix ret = new Matrix(M + 1, 1);
        for(int i = 0; i < M; i++) ret.set(i+1,0, B.get(i,0));
        ret.set(0,0,x);
        return ret;
    }

    /**
     * Packs the matrix rowise into a double[]
     */
    public static double[] unpackRowise(Matrix B){
        int M = B.getRowDimension();
        int N = B.getColumnDimension();
        double[] y = new double[M*N];
        int i = 0;
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                y[i] = B.get(m,n);
                i++;
            }
        }
        return y;
    }

    /**
     * Packs the matrix columnwise into a double[]
     */
    public static double[] unpackColumnwise(Matrix B){
        int M = B.getRowDimension();
        int N = B.getColumnDimension();
        double[] y = new double[M*N];
        int i = 0;
        for(int n = 0; n < N; n++){
            for(int m = 0; m < M; m++){
                y[i] = B.get(m,n);
                i++;
            }
        }
        return y;
    }

    /**
     * Convolution of two vectors.  This
     * allocates the required memory
     */
    public static double[] conv(double[] x, double[] y) {
        double[] r = new double[x.length + y.length - 1];
        for (int t = 0; t < r.length; t++) {
            double csum = 0.0;
            for (int i = 0; i < x.length; i++) {
                if (t - i >= 0 && t - i < y.length) {
                    csum += x[i] * y[t - i];
                }
            }
            r[t] = csum;
        }
        return r;
    }

    /**
     * Vector dot/inner product
     */
    public static double dot(double[] x, double[] y) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }
        return sum;
    }
    
    /** Dot product between two vectors, ignores whether row or column */
    public static double dot(Matrix x, Matrix y) {
        return dot(unpackRowise(x), unpackRowise(y));
    }

    /**
     * Return the min value of a vector
     */
    public static double min(double[] x) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] < out) {
                out = x[i];
            }
        }
        return out;
    }

    /**
     * Return true if every element in the vectors is equal, else false.
     */
    public static boolean equal(double[] x, double[] y) {
        for (int i = 0; i < x.length; i++) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reverse the order of a vector.  Allocates memory
     */
    public static double[] reverse(double[] x) {
        double[] rx = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            rx[i] = x[x.length - i - 1];
        }
        return rx;
    }

    /**
     * Return the distance between the two elements in
     * x that are the fathest apart.
     */
    public static double maxDistance(double[] x) {
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
    public static Complex dot(Complex[] x, Complex[] y) {
        //double re = 0.0, im = 0.0;
        Complex ret = new Complex(0, 0);
        for (int i = 0; i < x.length; i++) {
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
     * PRE: x.length = i, y.length = j, M is j by i matrix
     */
    public static void matrixMultVector(double[][] M, double[] x, double[] y) {
        for (int n = 0; n < y.length; n++) {
            y[n] = 0;
            for (int m = 0; m < x.length; m++) {
                y[n] += M[n][m] * x[m];
            }
        }
    }
    
    /**
     * y and x and vector, M is a matrix.
     * Performs y = M*x.  
     * PRE: x.length = j, y.length = i, M is i by j matrix
     */
    public static void matrixMultVector(Matrix M, double[] x, double[] y) {
        int m = M.getRowDimension();
        int n = M.getColumnDimension();
        for (int i = 0; i < m; i++) {
            y[i] = 0;
            for (int j = 0; j < n; j++) {
                y[i] += M.get(i,j) * x[j];
            }
        }
    }

    /**
     * y and x and vector, M is a matrix.
     * Performs y = M*x.
     * PRE: x.length = j, y.length = i, M is i by j matrix
     */
    public static double[] matrixMultVector(Matrix M, double[] x) {
        double[] y = new double[M.getRowDimension()];
        matrixMultVector(M, x, y);
        return y;
    }
    
    /**
     * y and x and vector, M is a matrix.
     * Performs y = M*x.
     * PRE: x.length = j, y.length = i, M is i by j matrix
     */
    public static double[] matrixMultVector(Matrix M, Double[] x) {
        double[] y = new double[M.getRowDimension()];
        double[] xd = new double[x.length];
        for(int i = 0; i < x.length; i++) xd[i] = x[i];
        matrixMultVector(M, xd, y);
        return y;
    }

    /**
     * Return column j of the matrix M.
     * PRE: x.length >= #rows in M
     */
    public static void getColumn(int j, Matrix M, double[] x) {
        for(int m = 0; m < M.getRowDimension(); m++){
            x[m] = M.get(m, j);
        }
    }

    /**
     * Return column j of the matrix M.
     */
    public static double[] getColumn(int j, Matrix M) {
        double[] x = new double[M.getRowDimension()];
        getColumn(j, M, x);
        return x;
    }

    /**
     * Return row j of the matrix M.
     * PRE: x.length >= #rows in M
     */
    public static void getRow(int j, Matrix M, double[] x) {
        for(int m = 0; m < M.getColumnDimension(); m++){
            x[m] = M.get(j, m);
        }
    }

    /**
     * Return row j of the matrix M.
     */
    public static double[] getRow(int j, Matrix M) {
        double[] x = new double[M.getColumnDimension()];
        getRow(j, M, x);
        return x;
    }

    /**
     * Return the norm of the column vector of
     * smallest norm.  Norm is sum of squares.
     */
    public static double minColumnNorn(Matrix M){
       double min = Double.POSITIVE_INFINITY;
       for(int n = 0; n < M.getColumnDimension(); n++){
           double sum2 = 0.0;
           for(int m = 0; m < M.getRowDimension(); m++){
               sum2 += M.get(m,n)*M.get(m,n);
           }
           if(sum2 < min) min = sum2;
       }
       return min;
    }

   /**
     * returns the vector  x - round(x)
     */
    public static double[] wrap(double[] x) {
        double[] y = new double[x.length];
        for(int i = 0; i < y.length; i++){
            y[i] = x[i] - Math.round(x[i]);
        }
        return y;
    }

    /**
     * sets y = x - round(x)
     */
    public static void wrap(double[] x, double[] y) {
        int len = Math.min(y.length, x.length);
        for(int i = 0; i < len; i++){
            y[i] = x[i] - Math.round(x[i]);
        }
    }

    /** 
     * Project y parallel to x and return in yp.
     * PRE: y.length == x.length == yp.length
     */
    public static void projectParallel(double[] x, double[] y, double[] yp) {
        double xty = dot(x, y);
        double xtx = dot(x, x);
        for (int i = 0; i < x.length; i++) {
            yp[i] = xty / xtx * x[i];
        }
    }

    /** 
     * Project y orthogonal to x and return in yp.
     * PRE: y.length == x.length == yp.length
     */
    public static void projectOrthogonal(double[] x, double[] y, double[] yp) {
        double xty = dot(x, y);
        double xtx = dot(x, x);
        for (int i = 0; i < x.length; i++) {
            yp[i] = y[i] - xty / xtx * x[i];
        }
    }


    /**
     * Return orthogonalised version of A.  All the columns
     * in the returned matrix will be orthogonal.
     */
    public static Matrix orthogonalise(Matrix A){
        int N = A.getColumnDimension();
        double[][] mat = A.transpose().getArrayCopy();
        //for all columns
        for(int n = 1; n < N; n++){
            for(int m = 0; m < n; m++){
                //System.out.println("mat[n] = " + print(mat[n]));
                //System.out.println("mat[m] = " + print(mat[m]));
                projectOrthogonal(mat[m], mat[n], mat[n]);
                //System.out.println("mat[n] proj = " + print(mat[n]));
            }
        }
        return (new Matrix(mat)).transpose();
    }

    /**
     * Return a vector containing the magnitude squared
     * of the column vectors of the Matrix A.
     */
    public static double[] columnSquareSum(Matrix A){
        int N = A.getColumnDimension();
        int M = A.getRowDimension();
        double[] ret = new double[N];
        //for all columns
        for(int n = 0; n < N; n++){
            ret[n] = 0.0;
            for(int m = 0; m < M; m++){
                double d = A.get(m,n);
                ret[n] += d*d;
            }
        }
        return ret;
    }

    /** 
     * Scalar divide.  Result is returned in y
     * PRE: x.length == y.length
     */
    public static void divide(double[] x, double d, double[] y) {
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] / d;
        }
    }

    /** 
     * Returns the normalised vector
     * PRE: x.length == y.length
     */
    public static void normalise(double[] x, double[] y) {
        double d = magnitude(x);
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] / d;
        }
    }

    /**
     * Return the elements in x between indicies start and end.
     */
    public static double[] getSubVector(double[] x, int start, int end) {
        int len = end - start + 1;
        double[] d = new double[len];
        System.arraycopy(x, start, d, 0, len);
        return d;
    }

    /**
     * Copy x to y.  Assumes y.length > x.length.
     * This uses System.arraycopy.
     */
    public static void copy(double[] x, double[] y){
        System.arraycopy(x, 0, y, 0, x.length);
    }

    /**
     * Return x modulo the parallelepiped contructed
     * by the columns of P.
     */
    public static double[] moduloParallelepiped(double[] x, Matrix P){
        Matrix invP = P.inverse();
        double[] p = matrixMultVector(invP, x);
        fracpart(p, p);
        return matrixMultVector(P, p);
    }

       /**
     * Return the elements in x between indicies start and end.
     */
    public static Double[] getSubVector(Double[] x, int start, int end) {
        int len = end - start + 1;
        Double[] d = new Double[len];
        System.arraycopy(x, start, d, 0, len);
        return d;
    }

    /** Convert a double[] to a Double[] */
    public static Double[] doubleArrayToDoubleArray(double[] d){
        Double[] D = new Double[d.length];
        for(int i = 0; i < d.length; i++)
            D[i] = new Double(d[i]);
        return D;
    }

    /**
     * Convert a Double[] to a double[].
     * nulls are silently converted to 0's
     */
    public static double[] DoubleArrayTodoubleArray(Double[] D){
        double[] d = new double[D.length];
        for(int i = 0; i < D.length; i++){
            if(D[i] == null)
                d[i] = 0.0;
            else
                d[i] = D[i].doubleValue();
        }
        return d;
    }
    
    /**
     * Copies f into x starting at start.  This modifies x.
     * @param start must be >= 0
     */
    public static void fillVector(double[] x, double[] f, int start) {
        if(start < 0)
            throw new ArrayIndexOutOfBoundsException("start must be >= 0");
        System.arraycopy(f, 0, x, start, Math.min(x.length - start, f.length));
    }

     /**
      * Copies f into x starting at start.  This modifies x.
      * @param start must be >= 0
      */
    public static void fillVector(Object[] x, Object[] f, int start) {
        if(start < 0)
            throw new ArrayIndexOutOfBoundsException("start must be >= 0");
        System.arraycopy(f, 0, x, start, Math.min(x.length - start, f.length));
    }

    /**
     * Places f into the last elements of x.  Modifies x.
     * f.length must be <= x.length
     */
    public static void fillEnd(double[] x, double[] f) {
        fillVector(x, f, x.length - f.length);
    }

     /**
     * Places f into the last elements of x.  Modifies x.
     * f.length must be <= x.length
     */
    public static void fillEnd(Object[] x, Object[] f) {
        fillVector(x, f, x.length - f.length);
    }

   /**
     * Places f into the first element of x.  Modifies x.
     */
    public static void fillStart(double[] x, double[] f) {
        fillVector(x, f, 0);
    }

    /**
     * Places f into the first element of x.  Modifies x.
     */
    public static void fillStart(Object[] x, Object[] f) {
        fillVector(x, f, 0);
    }

    /** 
     * Fill all elements of x with val.  This copies references so
     * it's assuming that val is immutable, be carefull!.
     */
    public static void fill(Object[] x, Object val){
        for(int n = 0; n < x.length; n++){
            x[n] = val;
        }
    }

    /** 
     * Returns the transpose of a matrix.  Allocates memory.
     * PRE: x.length == y.length
     */
    public static double[][] transpose(double[][] M1) {
        double[][] mat = new double[M1[0].length][M1.length];
        for (int m = 0; m < M1.length; m++) {
            for (int n = 0; n < M1[0].length; n++) {
                mat[n][m] = M1[m][n];
            }
        }
        return mat;
    }

    /** 
     * O(j^3) determinant algorithm that is 'fraction free'
     * and more stable than the LU and trace algorithm in the
     * Jama library.
     * <p>
     * Erwin H. Bareiss, �Sylvester's Identity and Multistep 
     * Integer-Preserving Gaussian Elimination,� 
     * Mathematical Computation 22, 103, pp. 565 � 578, 1968.
     */
    public static double stableDet(Matrix mat) {

        //handle exceptional cases
        if (mat.getColumnDimension() != mat.getRowDimension()) {
            throw new IllegalArgumentException("Matrix must be square.");
        }
        if (mat.getRowDimension() == 1) {
            return mat.get(0, 0);
        }

        return stableDet(mat, 0);

    }

    /** Recursive function used by stableDet */
    protected static double stableDet(Matrix mat, int index) {

        if (mat.getRowDimension() - index == 2) {
            return mat.get(index, index) * mat.get(index + 1, index + 1) - mat.get(index, index + 1) * mat.get(index + 1, index);
        }

        for (int i = index + 1; i < mat.getRowDimension(); i++) {
            for (int j = index + 1; j < mat.getRowDimension(); j++) {
                double sub = mat.get(i, index) * mat.get(index, j);
                mat.set(i, j, mat.get(i, j) * mat.get(index, index) - sub);
            }
        }
        double det = stableDet(mat, index + 1);
        for (int i = index; i < mat.getRowDimension() - 2; i++) {
            det /= mat.get(index, index);
        }

        return det;
    }
    
    /** Returns vector of length n of randomGaussian integer in the range -M to M-1 */ 
    public static double[] randomIntegerVector(int n, int M) {
        double[] u = new double[n];
        Random r = new Random();
        for (int t = 0; t < n; t++) {
            u[t] = r.nextInt(2*M) - M;
        }
        return u;
    }
    
     /** Returns vector of length n of randomGaussian integer in the range 0 to M-1 */ 
    public static int[] randomIntegers(int n, int M) {
        int[] u = new int[n];
        Random r = new Random();
        for (int t = 0; t < n; t++) {
            u[t] = r.nextInt(M);
        }
        return u;
    }

    /** Swap columns i and j in a matrix inplace */
    public static void swapColumns(Matrix B, int i, int j) {
        int n = B.getRowDimension();
        for (int t = 0; t < n; t++) {
            double temp = B.get(t, i);
            B.set(t, i, B.get(t, j));
            B.set(t, j, temp);
        }
    }

    /** Swap all columns from i to i+len - 1 and j to j + len - 1 in a matrix inplace */
    public static void swapColumns(Matrix B, int i, int j, int len) {
        for(int t = 0; t < len; t++){
            swapColumns(B, i+t, j+t);
        }
    }

    /** Swap columns i and j in a matrix inplace */
    public static void swapRows(Matrix B, int i, int j) {
        int n = B.getColumnDimension();
        for (int t = 0; t < n; t++) {
            double temp = B.get(i, t);
            B.set(i, t, B.get(j, t));
            B.set(j, t, temp);
        }
    }

    /** Add d times column i to column j in matrix B*/
    public static void addMultipleOfColiToColj(Matrix B, double d, int i, int j) {
        int n = B.getColumnDimension();
        for (int t = 0; t < n; t++)
            B.set(t, j, d*B.get(t, i) + B.get(t, j));
    }

    /** Construct a column matrix (vector) from a double[] */
    public static Matrix columnMatrix(double[] x){
        Matrix M = new Matrix(x.length, 1);
        for(int n = 0; n < x.length; n++){
            M.set(n, 0, x[n]);
        }
        return M;
    }
    
        /** Construct a column matrix (vector) from a double[] */
    public static Matrix columnMatrix(int[] x){
        Matrix M = new Matrix(x.length, 1);
        for(int n = 0; n < x.length; n++){
            M.set(n, 0, x[n]);
        }
        return M;
    }
   

     /** Construct a row matrix (vector) from a double[] */
    public static Matrix rowMatrix(double[] x){
        Matrix M = new Matrix(1, x.length);
        for(int n = 0; n < x.length; n++){
            M.set(0, n, x[n]);
        }
        return M;
    }

    /** Construct a row matrix (vector) from a double[] */
    public static Matrix rowMatrix(int[] x){
        Matrix M = new Matrix(1, x.length);
        for(int n = 0; n < x.length; n++){
            M.set(0, n, x[n]);
        }
        return M;
    }
    
    /** Split matrix B into a set of column vectors */
    public static Set<Matrix> splitColumns(Matrix B){
        int M = B.getColumnDimension();
        int N = B.getRowDimension();
        HashSet<Matrix> S = new HashSet();
        for(int n = 0; n < N; n++) S.add(B.getMatrix(0, M-1, n, n));
        return S;
    }


   
   /** Tests whether a matrix U is unimodular.  TOL determines what counts as zero. */
   public static boolean isUnimodular(Matrix U, double TOL) {
        int n = U.getColumnDimension();
        if(n != U.getRowDimension() ) return false; //matrix must be square
        if( Math.abs(U.det()) - 1.0 > TOL ) return false; //determinant is not one
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if( Math.abs(pubsim.Util.fracpart(U.get(i,j))) > TOL ) return false; //elements aren't integers
        return true;
   }
   ///Default TOL = 10e-9
   public static boolean isUnimodular(Matrix U) { return isUnimodular(U,1e-9); }
    
}
