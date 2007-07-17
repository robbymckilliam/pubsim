/*
 * ModifiedZnLLS.java
 *
 * Created on 17 July 2007, 07:44
 */

package simulator;

/**
 *
 * @author Robby
 */
public class ModifiedZnLLS extends ZnLLS implements PRIEstimator {
    
    public double estimateFreq(double[] y, double fmin, double fmax){
        if (n != y.length-1)
	    setSize(y.length);
        
        Anstar.project(y, z);
        
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
            
            double f = ztv/ztz;
            //line search loop
            while(f < fmax){
                
                double dist = f*f*ztz - 2*f*ztv + vtv;
                
                if(dist < mindist && f > fmin && f < fmax){
                    mindist = dist;
                    bestf = f;
                    //System.out.print("****** ");
                }
                
                   /* if( i == 10 && f > 0.8 && f < 0.9 ){
                       System.out.println("f = " + f + ", dist = " + dist);
                       System.out.println("v = " + VectorFunctions.print(v));
                       System.out.println("sum v = " + VectorFunctions.sum(v));
                    }*/
                       
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
    
}