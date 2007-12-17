/*
 * AnstarNew.java
 *
 * Created on 14 November 2007, 09:46
 */

package lattices;

import simulator.IndexedDouble;
import simulator.VectorFunctions;
import java.util.Arrays;

/**
 * This is a new, simpler and faster version of the Anstar algorithm.
 * Geometrically it searches along the line $\bm{1} r$ where 
 * $r \in [0, 1]$ in the lattice Zn.
 * @author Robby
 */
public class AnstarNew extends Anstar implements LatticeNearestPointAlgorithm{
    
    private IndexedDouble[] z;
    
    public void setDimension(int n){
        this.n = n;
        u = new double[n+1];
        v = new double[n+1];
        z = new IndexedDouble[n+1];
        for(int i = 0; i < n + 1; i++)
            z[i] = new IndexedDouble();
    }
    
    public void nearestPoint(double[] y){
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        double a = 0, b = 0;
        for(int i = 0; i < n + 1; i++){
            z[i].value = y[i] - Math.round(y[i]);
            z[i].index = i;
            a += z[i].value;
            b += z[i].value * z[i].value;
        }
        
        Arrays.sort(z);
        
        double D = b - a*a/(n+1);
        int m = 0;
        for(int i = 0; i < n + 1; i++){
            double dist = b - a*a/(n+1);
            if(dist < D){
                D = dist;
                m = i;
            }
            a -= 1;
            b += -2*z[n-i].value + 1;
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < m; i++)
            u[z[n-i].index] += 1;
        
        project(u, v);
           
    }
    
    
}
