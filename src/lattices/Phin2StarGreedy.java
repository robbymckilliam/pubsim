/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

/**
 * Experimental nearest point algorithm for Phin2* that uses
 * a greedy seaerch within the fundamental parrallelotope.
 * I suspect that this algorithm is valid provided that the basis
 * vectors are also relavant vectors.  I beleive that this
 * occurs for large enough n.
 * @author Robby McKilliam
 */
public class Phin2StarGreedy extends Phin2Star {

    double[] z, g, ut, yt;
    boolean[] tested;
    
    @Override
    public void setDimension(int n){
        this.n = n;
        u = new double[n+2];
        ut = new double[n+2];
        v = new double[n+2];
        z = new double[n+2];
        g = new double[n+2];
        yt = new double[n+2];
        tested = new boolean[n+2];
    }
    
    public void nearestPoint(double[] y) {
        if(y.length != n+2) setDimension(y.length-2);
        
        double ztz = 0;
        double ztg = 0;
        double zt1 = 0;
        double gtg = 0;
        double oto = n+2;
        
        project(y,yt);
        for(int i=0; i<n+2; i++)
            ut[i] = Math.floor(yt[i]);
        project(ut,yt);
        
        for(int i=0; i<n+2; i++){
            g[i] = i-(n+1)/2.0;
            z[i] = yt[i] - ut[i];
            ztz += z[i]*z[i];
            zt1 += z[i];
            ztg += z[i]*g[i];
            gtg += g[i]*g[i];
            tested[i] = false;
        }
        
        double bestDist = Double.POSITIVE_INFINITY;
        
        for(int i = 0; i<n+2; i++){
            
            double thisMinDist = Double.POSITIVE_INFINITY;
            int bestj = 0;
            for(int j = 0; j < n+2; j++){
                double dist = ztz + 2*(z[j] - zt1/oto - ztg*g[j]/gtg)
                                  + 1 - 1/oto - g[j]*g[j]/gtg;
                if(dist < thisMinDist && !tested[j]){
                    thisMinDist = dist;
                    bestj = j;
                }
            }
            
            ut[bestj] += 1;
            ztz += 2*z[bestj] + 1;
            zt1 += 1;
            ztg += g[bestj];
            tested[bestj] = true;
            
            if(thisMinDist < bestDist){
                bestDist = thisMinDist;
                System.arraycopy(ut, 0, u, 0, u.length);
                project(u, v);
            }
            
        }
        
        
    }
    
    

}
