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
 * The initial idea behind this algorithm was to head to the "next" relavant
 * vector in An* and see if it was closer.  Originally it was though that this
 * could be achieved by sorted at most half of the elements.  It turns out that
 * this is not the case.  You still need to sort everything.  This algorithm
 * is not in working order at the moment, and I can't see any reason for doing
 * any more work on it.
 * @author Robby McKilliam
 */
public class AnstarHalfSort extends Anstar implements LatticeNearestPointAlgorithm{
    
    private IndexedDouble[] z;
    private double[] projy, au, av, fu, fv;
    
    public void setDimension(int n){
        this.n = n;
        au = new double[n+1];
        av = new double[n+1];
        fu = new double[n+1];
        fv = new double[n+1];
        projy = new double[n+1];
        z = new IndexedDouble[n+1];
        for(int i = 0; i < n + 1; i++)
            z[i] = new IndexedDouble();
    }
    
    public void nearestPoint(double[] y){
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        project(y, projy);
        VectorFunctions.round(projy, fu);
        project(fu, fv);
        System.arraycopy(fu, 0, au, 0, n+1);
        
        //Sort projy-fv into positive and negative parts "halves".
        //This only requires O(n) operations.
        int posi = n, negi = 0;
        for(int i = 0; i < n + 1; i++){
            double er = projy[i] - fv[i];
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
        
        Arrays.sort(z);
        
        System.out.println("z");
        for(int i = 0; i < n + 1; i++)
            System.out.print(z[i].value + " ");
        System.out.println();
        
        //if there are more negative numbers, use them
        if(negi > n - posi){
            
            //calculate the next relavant vector
            int m;
            double bestk = Double.POSITIVE_INFINITY, 
                k, 
                sumdots = 0.0;
            for (int i = 0; i < negi; i++){
                m = i+1;
                sumdots -= z[i].value;
                k = (((double)(m*(n-m+1)))/(n+1))/sumdots;
                if(k < bestk){
                    bestk = k;
                    au[z[i].index] -= 1;
                } else break;
            }
        //else there are more positive number, use them 
        }else{
            
            //calculate the next relavant vector
            int m;
            double bestk = Double.POSITIVE_INFINITY, 
                k, 
                sumdots = 0.0;
            for (int i = n; i > posi; i--){
                m = n + 1 - i;
                sumdots += z[i].value;
                k = (((double)(m*(n-m+1)))/(n+1))/sumdots;
                if(k < bestk){
                    bestk = k;
                    au[z[i].index] += 1;
                } else break;
            }
            
        }
        
        project(au, av);
        double adist = VectorFunctions.distance_between2(projy, av);
        double fdist = VectorFunctions.distance_between2(projy, fv);
        
        System.out.println("fu = " + VectorFunctions.print(fu));
        System.out.println("au = " + VectorFunctions.print(au));
        
        if(adist < fdist){
            v = av;
            u = au;
        }else{
            v = fv;
            u = fu;
        }
           
    }
    
    
}
