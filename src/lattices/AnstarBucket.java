/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lattices;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Robby
 */
public class AnstarBucket extends Anstar
        implements LatticeNearestPointAlgorithm {

    private ArrayList<Integer>[] buckets;
    private double[] z;

    @Override
    public void setDimension(int n) {
        this.n = n;
        // Allocate some space for arrays
        u = new double[n + 1];
        v = new double[n + 1];
        z = new double[n + 1];
        buckets = new ArrayList[n+1];
        for(int i = 0; i < n + 1; i++)
            buckets[i] = new ArrayList<Integer>();
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        double a = 0, b = 0;
        for(int i = 0; i < n + 1; i++){
            z[i] = y[i] - Math.round(y[i]);
            int bi = n - (int)(Math.floor((n+1)*(z[i]+0.5)));
            buckets[bi].add(new Integer(i));
            a += z[i];
            b += z[i] * z[i];
        }
        
        double D = b - a*a/(n+1);
        int m = 0;
        for(int i = 0; i < n+1; i++){
            double dist = b - a*a/(n+1);
            if(dist < D){
                D = dist;
                m = i;
            }
            Iterator<Integer> itr = buckets[i].iterator();
            while(itr.hasNext()){
                int ind = itr.next().intValue();
                a -= 1;
                b += -2*z[ind] + 1;                
            }
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < m; i++){
            Iterator<Integer> itr = buckets[i].iterator();
            while(itr.hasNext()){
                int ind = itr.next().intValue();
                u[ind] += 1;
            }
        }
        
        project(u, v);
        
    }
    
}
