/*
 * ZnLLS.java
 *
 * Created on 8 July 2007, 08:55
 */

package simulator.pes;

import java.util.TreeMap;
import simulator.*;

/**
 * Zn->An->An* line search.  Uses the sorted
 * Zn search proposed by Dan and Vaughan.  This
 * runs O(n^3log(n)) which is the same as the An*
 * Bresenham line search.
 * @author Robby McKilliam
 */
public class ZnLLS implements PRIEstimator {
    
    double[] g, z, v;
    double n;
    TreeMap map;
     
    /** 
     * Sets protected variable g to the glue
     * vector [i].  See SLAG pp109.
     */
    protected void glueVector(double i){
        double j = n + 1 - i;
        for(int k = 0; k < j; k++)
            g[k] = i/(n+1);
        for(int k = (int)j; k < n + 1; k++)
            g[k] = -j/(n+1);
    }
    
    public void setSize(int N) 
    {
        this.n = N-1;
        g = new double[N];
        z = new double[N];
        v = new double[N];
        map = new TreeMap();
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax){
        if (n != y.length-1)
	    setSize(y.length);
        
        Anstar.project(y, z);
        
        double bestf = 0.0;
        double mindist = Double.POSITIVE_INFINITY;
        
        for(int i = 0; i <=n ; i++){
            map.clear();
            glueVector(i);
            
            //setup map and variables for this glue vector
            double ztz = 0.0, ztv = 0.0, vtv = 0.0;
            for(int j=0; j<=n; j++){
                v[j] = Math.round(fmin*z[j] - g[j]) + g[j];
                map.put(new Double((Math.signum(z[j])*0.5 + v[j])/z[j]), new Integer(j));
                ztz += z[j]*z[j];
                ztv += z[j]*v[j];
                vtv += v[j]*v[j];
            }
            
            double f = ztv/ztz;
            //line search loop
            while(f < fmax){
                
                double dist = ztz - 2*ztv/f + vtv/(f*f);
                
                if(dist < mindist && f > fmin && f < fmax){
                    mindist = dist;
                    bestf = f;
                }
                       
                Double key = ((Double) map.firstKey());
                int k = ((Integer)map.get(key)).intValue();
                double d = Math.signum(z[k]);
                v[k] += d;
                map.remove(key);
                map.put(new Double((d*0.5 + v[k])/z[k]), new Integer(k));
                
                ztv += d*z[k];
                vtv += 2*d*(v[k]-d) + 1;
                
                //update f
                f = ztv/ztz;
                
            }  
        }
        return bestf;
    }
    
    /**Return the best lattice point rather than f.  This is for testing*/
    public double[] bestLatticePoint(double[] y, double fmin, double fmax){
        if (n != y.length-1)
	    setSize(y.length);
        
        double[] bestv = new double[y.length];
        
        Anstar.project(y, z);
        
        double bestf = 0.0;
        double mindist = Double.POSITIVE_INFINITY;
        
        for(int i = 0; i <= n; i++){
            map.clear();
            glueVector(i);
            
            //setup map and variables for this glue vector
            double ztz = 0.0, ztv = 0.0, vtv = 0.0;
            for(int j=0; j<=n; j++){
                v[j] = Math.round(fmin*z[j] - g[j]) + g[j];
                map.put(new Double((Math.signum(z[j])*0.5 + v[j])/z[j]), new Integer(j));
                ztz += z[j]*z[j];
                ztv += z[j]*v[j];
                vtv += v[j]*v[j];
            }
            
            double f = ztv/ztz;
            //line search loop
            while(f < fmax){
                
                double dist = f*f*ztz - 2*f*ztv + vtv;
                
                if(dist < mindist && f > fmin && f < fmax){
                    mindist = dist;
                    bestf = f;
                    bestv = v.clone();
                }
                       
                Double key = ((Double) map.firstKey());
                int k = ((Integer)map.get(key)).intValue();
                double d = Math.signum(z[k]);
                v[k] += d;
                map.remove(key);
                map.put(new Double((d*0.5 + v[k])/z[k]), new Integer(k));
                
                ztv += d*z[k];
                vtv += 2*d*(v[k]-d) + 1;
                
                //update f
                f = ztv/ztz;
                           
            }  
        }
        return bestv;
    }
    
    /** Not implemented here. */
    public double varianceBound(double sigma, double[] s){
        return 0;
    }
    
}
