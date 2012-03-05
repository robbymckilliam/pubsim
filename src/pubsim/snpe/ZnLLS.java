/*
 * ZnLLS.java
 *
 * Created on 8 July 2007, 08:55
 */

package pubsim.snpe;

import java.util.TreeMap;
import pubsim.lattices.Anstar.AnstarSorted;
import pubsim.lattices.Anstar.AnstarVaughan;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;

/**
 * Zn->An->An* line search.  Uses the sorted
 * Zn search proposed by Dan and Vaughan.  This
 * runs O(n^3log(n)) which is the same as the An*
 * Bresenham line search.
 * @author Robby McKilliam
 */
public class ZnLLS implements PRIEstimator {
    
    final double[] g, z, v, fz;
    final double n, N;
    final TreeMap map;

    final protected PhaseEstimator phasestor;

    /** Period and phase estimates */
    protected double That, phat;

    public ZnLLS(int N){
        this.n = N-1;
        this.N = N;
        phasestor = new PhaseEstimator(N);
        g = new double[N];
        z = new double[N];
        fz = new double[N];
        v = new double[N];
        map = new TreeMap();
    }
     
    /** 
     * Sets protected variable g to the glue
     * vector [i].  See SPLAG pp109.
     */
    protected void glueVector(double i){
        double j = n + 1 - i;
        for(int k = 0; k < j; k++)
            g[k] = i/(n+1);
        for(int k = (int)j; k < n + 1; k++)
            g[k] = -j/(n+1);
    }
    
    @Override
    public void estimate(Double[] y, double Tmin, double Tmax){
        double fmin = 1/Tmax; double fmax = 1/Tmin;

        AnstarVaughan.project(y, z);
        
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
            
            double f = vtv/ztv;
            //line search loop
            while(f < fmax){
                
                double dist = ztz - 2*ztv/f + vtv/(f*f);
                
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
        That = 1.0/bestf;

        //now compute the phase estimate
        phat = phasestor.getPhase(y, That);

        
    }
    
    @Override
    public double getPeriod() {
        return That;
    }

    @Override
    public double getPhase() {
        return phat;
    }
    
}
