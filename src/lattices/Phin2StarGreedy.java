/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Experimental nearest point algorithm for Phin2* that uses
 * a greedy seaerch within the fundamental parrallelotope.
 * I suspect that this algorithm is valid provided that the basis
 * vectors are also relavant vectors.  I beleive that this
 * occurs for large enough n.
 * @author Robby McKilliam
 */
public class Phin2StarGreedy extends Phin2Star {

    double[] z, g, ut;
    List<Integer> totest;
    
    @Override
    public void setDimension(int n){
        this.n = n;
        u = new double[n+2];
        ut = new double[n+2];
        v = new double[n+2];
        z = new double[n+2];
        g = new double[n+2];
        totest = new ArrayList<Integer>(n+2);
    }
    
    public void nearestPoint(double[] y) {
        if(y.length != n+2) setDimension(y.length-2);
        
        double ztz = 0;
        double ztg = 0;
        double zt1 = 0;
        double gtg = 0;
        double oto = n+2;
        
        for(int i=0; i<n+2; i++){
            g[i] = i-(n+1)/2.0;
            ut[i] = Math.floor(y[i]);
            z[i] = y[i] - ut[i];
            ztz += z[i]*z[i];
            zt1 += z[i];
            ztg += z[i]*g[i];
            gtg += g[i]*g[i];
            totest.add(i);
        }
        
        double bestDist = Double.POSITIVE_INFINITY;
        
        for(int i = 0; i<n+2; i++){
            
            double thisMinDist = Double.POSITIVE_INFINITY;
            int bestind = 0;
            Iterator<Integer> itr = totest.iterator();
            while(itr.hasNext()){
                int ind = itr.next().intValue();
                double dist = ztz + 2*(z[ind] - zt1/oto - ztg*g[ind]/gtg)
                                  + 1 - 1/oto - g[ind]*g[ind]/gtg;
                if(dist < thisMinDist){
                    thisMinDist = dist;
                    bestind = ind;
                }
            }
            
            ut[bestind] += 1;
            ztz += 2*z[bestind] + 1;
            zt1 += 1;
            ztg += g[bestind];
            
            if(thisMinDist < bestDist){
                bestDist = thisMinDist;
                System.arraycopy(ut, 0, u, 0, u.length);
                project(u, v);
            }
            
        }
        
        
    }
    
    

}
