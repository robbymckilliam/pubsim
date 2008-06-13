/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import java.util.Arrays;
import simulator.IndexedDouble;

/**
 * Implementation of the O(n log(n)) algorithm to find the nearest
 * lattice point in the Coxeter lattice A_{n/m}.  This was suggested
 * by Warren Smith.
 * @author Robby
 */
public class Anm extends AnstarVaughan implements NearestPointAlgorithmInterface{
    
    private IndexedDouble[] z;
    protected int M;
    
    /** Constructor can set the m part of A_{n/m}. */
    public Anm(int M){
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
        
        int sumM = 0;
        double a = 0, b = 0;
        for(int i = 0; i < n + 1; i++){
            sumM += Math.round(y[i]);
            z[i].value = y[i] - Math.round(y[i]);
            z[i].index = i;
            a += z[i].value;
            b += z[i].value * z[i].value;
        }
        
        Arrays.sort(z);
        
        double D = Double.POSITIVE_INFINITY;
        int m = 0;
        for(int i = 0; i < n+1; i++){
            double dist = b - a*a/(n+1);
            if(dist < D && sumM%M == 0){
                D = dist;
                m = i;
            }
            sumM++;
            a -= 1;
            b += -2*z[n-i].value + 1;
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < m; i++)
            u[z[n-i].index] += 1;
        
        project(u, v);
           
    }

    /** {@inheritDoc} */
    @Override
    public double volume(){
        return M/Math.sqrt(n+1);
    }

}
