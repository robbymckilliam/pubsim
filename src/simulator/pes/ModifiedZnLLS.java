/*
 * ModifiedZnLLS.java
 *
 * Created on 17 July 2007, 07:44
 */

package simulator.pes;

import lattices.AnstarVaughan;
import simulator.*;

/**
 * ZnLLS with the unormalised log-likelihood function, ie. it finds the
 * closest point to the line rather than the point of minimal angle.
 * @author Robby McKilliam
 */
public class ModifiedZnLLS extends ZnLLS implements PRIEstimator {
    
    @Override
    public double estimateFreq(double[] y, double fmin, double fmax){
        if (n != y.length-1)
	    setSize(y.length);
        
        AnstarVaughan.project(y, z);
        
        double bestf = 0.0;
        double mindist = Double.POSITIVE_INFINITY;
        
        for(int i = 0; i <=n ; i++){
            map.clear();
            glueVector(i);
            
            
            //System.out.println();
            //System.out.println("glue " + i);
            
            //setup map and variables for this glue vector
            double ztz = 0.0, ztv = 0.0, vtv = 0.0;
            for(int j=0; j<=n; j++){
                v[j] = Math.round(fmin*z[j] - g[j]) + g[j];
                map.put(new Double((Math.signum(z[j])*0.5 + v[j])/z[j]), new Integer(j));
                ztz += z[j]*z[j];
                ztv += z[j]*v[j];
                vtv += v[j]*v[j];
            }
            
            double f = vtv/ztv;
            //line search loop
            while(f < fmax){
                
                double dist = f*f*ztz - 2*f*ztv + vtv;
                
                if(dist < mindist /*&& f > fmin && f < fmax*/){
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
                f = vtv/ztv;
                
            }  
        }
        return bestf;
    }
    
}
