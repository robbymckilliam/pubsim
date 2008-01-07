/*
 * AnstarHalfSort.java
 *
 * Created on 7 January 2008, 09:26
 */

package lattices;

import java.util.Arrays;
import simulator.IndexedDouble;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
 */
public class AnstarHalfSort extends Anstar implements LatticeNearestPointAlgorithm{
    
    private IndexedDouble[] z;
    private double[] progy;
    
    public void setDimension(int n){
        this.n = n;
        u = new double[n+1];
        v = new double[n+1];
        progy = new double[n+1];
        z = new IndexedDouble[n+1];
        for(int i = 0; i < n + 1; i++)
            z[i] = new IndexedDouble();
    }
    
    public void nearestPoint(double[] y){
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        project(y, progy);
        VectorFunctions.round(progy, u);
        
        //Sort progy into positive and negative parts halves.
        //This only requires O(n) operations.
        int posi = n, negi = 0;
        for(int i = 0; i < n + 1; i++){
            double er = progy[i] - u[i];
            if(er > 0.0){
                z[posi].value = er;
                z[posi].index = i;
                posi--;
            }
            else if(er < 0.0){
                z[negi].value = er;
                z[negi].index = i;
                negi++;
            }
        }
        
        //if there are less negative numbers, use them
        if(negi < n - posi){
            //sort the negative half of the array
            Arrays.sort(z, 0, negi-1);
            
            int m;
            double bestk = Double.POSITIVE_INFINITY, 
                k, 
                sumdots = 0.0;
            for (int i = 0; i < negi-1; i++){
                m = n + 1 - i;
                sumdots += z[i].value;
                k = (((double)(m*(n-m+1)))/(n+1))/sumdots;
                if(k < bestk){
                    bestk = k;
                } else break;
            }
            
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < m; i++)
            u[z[n-i].index] += 1;
        
        project(u, v);
           
    }
    
    
}
