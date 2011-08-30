/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anm;

import pubsim.lattices.*;
import Jama.Matrix;
import java.util.Arrays;
import pubsim.lattices.An.AnSorted;
import pubsim.IndexedDouble;
import pubsim.Util;

/**
 * Implementation of the O(n log(n)) algorithm to find the nearest
 * lattice point in the Coxeter lattice A_{n/m}.  This was suggested
 * by Warren Smith.
 * @author Robby McKilliam
 */
public class AnmSorted extends NearestPointAlgorithmStandardNumenclature{
    
    private IndexedDouble[] z;
    protected int M;
    
    /** Constructor can set the m part of A_{n/m}. */
    public AnmSorted(int M){
        setM(M);
    }
    
    /** 
     * Set the m part of A_{n/m}.  Note that m must divide
     * n+1 else this degernates to the lattice An*, however
     * the algorithm will not work as a nearest point algorithm
     * for An*.
     */
    protected void setM(int M){
        this.M = M;
    }
    
    /** {@inheritDoc} */
    @Override
    public void setDimension(int n){
        this.n = n;
        u = new double[n+1];
        v = new double[n+1];
        z = new IndexedDouble[n+1];
        for(int i = 0; i < n + 1; i++)
            z[i] = new IndexedDouble();
    }
    
    /** {@inheritDoc} */
    @Override
    public void nearestPoint(double[] y){
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        int gamma = 0;
        double a = 0, b = 0;
        for(int i = 0; i < n + 1; i++){
            gamma += Math.round(y[i]);
            z[i].value = y[i] - Math.round(y[i]);
            z[i].index = i;
            a += z[i].value;
            b += z[i].value * z[i].value;
        }
        gamma = Util.mod(gamma, M);
        
        Arrays.sort(z);
        
        double D = Double.POSITIVE_INFINITY;
        int m = 0;
        for(int i = 0; i < n+1; i++){
            double dist = b - a*a/(n+1);
            if(dist < D && gamma == 0){
                D = dist;
                m = i;
            }
            gamma = Util.mod(gamma + 1, M);
            a -= 1;
            b += -2*z[n-i].value + 1;
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < m; i++)
            u[z[n-i].index] += 1;
        
        project(u, v);
           
    }
         
    /**
     * Project a vector into the zero-mean plane
     * y is output, x is input (x & y can be the same array)
     * <p>
     * Pre: y.length >= x.length
     */
    public static void project(double[] x, double[] y) {
	double xbar = 0.0;
	for (int i = 0; i < x.length; i++)
	    xbar += x[i];
	xbar /= x.length;
	for (int i = 0; i < x.length; i++)
	    y[i] = x[i] - xbar;
    }

    /** {@inheritDoc} */
    @Override
    public double volume(){
        return M/Math.sqrt(n+1);
    }

    /** 
     * This is only valid for some values of m and n.
     * See: 
     * Perfect Lattice in Euclidean Spaces
     * (section on Coxeter lattices)
     *  
     */
    public double inradius() {
        return Math.sqrt(2.0)/2.0;
    }

    public Matrix getGeneratorMatrix() {
        Lattice an = new AnSorted(n);
        an.setDimension(n);
        Matrix Mat = an.getGeneratorMatrix();
        double d = ((double) M)/(n+1);
        for(int i = 0; i < n+1; i++){
            Mat.set(i, n-1, -d);
        }
        Mat.set(0, n-1, M - d);
        return Mat;
    }

    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
