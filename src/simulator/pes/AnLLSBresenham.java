/*
 * AnLLSBresenham.java
 *
 * Created on 21 June 2007, 10:43
 */

package simulator.pes;

import lattices.Anstar.AnstarVaughan;
import simulator.*;

/**
 * This is the bresenham version of AnLLS.  It should be fast and
 * I think that it is reasonably easy to prove that it will always
 * work even though it does miss some voronoi regions.  Essentially
 * it picks the next 'atleast corner connected' box in Zn that is
 * closest to the line.  This should run in O(n^3) which is probably
 * faster than the An* bresenham estimator.  Certainly it is faster
 * in simulation.
 * THIS DOES NOT ACTUALLY WORK!  CAN MISS THE CLOSEST BOX!
 * @author Robby McKilliam
 */
public class AnLLSBresenham extends AnLLS implements PRIEstimator {
    
    protected double[] d;
    protected double[] fy;
    
    public void setSize(int N) {
        n = N-1;
        kappa = new double[N];
        v = new double[N];
        g = new double[N];
        d = new double[N];
        fy = new double[N];
    }    
    
    public double estimateFreq(double[] y, double fmin, double fmax){
        if (n != y.length-1)
            setSize(y.length);

        double bestdist2 = Double.POSITIVE_INFINITY;
        double bestf = 0.0;

        //put y in the zero mean plane
        AnstarVaughan.project(y,y);

        //System.out.println(iters);
        //System.out.println("y = " + VectorFunctions.print(y));
        
        double maxy = 0;
        int maxi = 0;   //position of the maximum value of y[i]
        for(int i = 0; i <= n; i++){
            if(maxy < Math.abs(y[i])){
                maxy = Math.abs(y[i]);
                maxi = i;
            }
        }

        //calculate the error term to be added each iteration
        for(int i = 0; i <= n; i++)
            d[i] = y[i]/maxy;
        
        //System.out.println(iters);
        //System.out.println("d = " + VectorFunctions.print(d));
        
        for(int i = 0; i <= n; i++){
            glueVector(i);

            //go to the first point at fmin
            double ifmin = (Math.round(fmin*y[maxi] - g[maxi]) + g[maxi])/y[maxi];
            for(int j = 0; j <=n ; j++)
                fy[j] = ifmin*y[j] - g[j] - d[j];
            
            //calculate the number of iterations needed
            //int iters = (int) (Math.ceil(Math.abs(fmax*y[maxi] - g[maxi])) - Math.floor(Math.abs(fmin*y[maxi] - g[maxi])));
          
            //System.out.println();
            //System.out.println("glue " + i);
            //System.out.println("glue i = " + VectorFunctions.print(g));
            
            //System.out.println("start ifmin val = " + (ifmin*y[maxi] - g[maxi] - d[maxi]));
            //System.out.println("fy = " + VectorFunctions.print(fy));
            
            //iterate over Zn
            //for(int j = 0; j < iters; j++){
            double f = fmin;
            while(f < fmax){

                //move the the next lattice point
                for(int k = 0; k <=n ; k++){
                    fy[k] += d[k];
                    v[k] = Math.round(fy[k]);
                    v[k] += g[k];   
                }

                if( i == 10 ){
                   //System.out.println("sum v =" + VectorFunctions.sum(v));
                }
                
                //AnstarVaughan.project(v,v);
                //if(Math.abs(VectorFunctions.sum(v)) < 0.2 ){
                
                    double ytv = 0.0, yty = 0.0;
                    for(int k = 0; k <= n; k++){
                        ytv += y[k]*v[k];
                        yty += y[k]*y[k];
                    }
                    f = ytv/yty;
                    double dist2 = 0.0;
                    for(int k = 0; k <= n; k++){
                        double diff = f*y[k] - v[k];
                        dist2 += diff*diff;
                    }
                    //dist2 /= (f*f);   //uncomment to make this maximum liklihood
                    if( dist2 < bestdist2 && f >= fmin && f <= fmax ){
                        bestdist2 = dist2;
                        bestf = f;
                    }

                //}

            }

        }

        return bestf;
     }
     
    public double[] bestLatticePoint(double[] y, double fmin, double fmax){
        if (n != y.length-1)
            setSize(y.length);
        
         double[] bestv = new double[y.length];

        double bestdist2 = Double.POSITIVE_INFINITY;
        double bestf = 0.0;

        //put y in the zero mean plane
        AnstarVaughan.project(y,y);

        double maxy = 0;
        int maxi = 0;   //position of the maximum value of y[i]
        for(int i = 0; i <= n; i++){
            if(maxy < Math.abs(y[i])){
                maxy = Math.abs(y[i]);
                maxi = i;
            }
        }

        //calculate the error term to be added each iteration
        for(int i = 0; i <= n; i++)
            d[i] = y[i]/maxy;
        
        //calculate the number of iterations needed
        int iters = (int)Math.ceil(Math.abs(Math.round(fmax*y[maxi]) - Math.round(fmin*y[maxi])));
        
        for(int i = 0; i <= n; i++){
            glueVector(i);

            //go to the first point at fmin
            fmin = (Math.round(fmin*y[maxi] - g[maxi]) + g[maxi])/y[maxi];
            for(int j = 0; j <=n ; j++)
                fy[j] = fmin*y[j] - g[j];
            
            //iterate over Zn
            for(int j = 0; j < iters; j++){

                //move the the next lattice point
                for(int k = 0; k <=n ; k++){
                    fy[k] += d[k];
                    v[k] = Math.round(fy[k]);
                    v[k] += g[k];   
                }

                //AnstarVaughan.project(v,v);
                if(Math.abs(VectorFunctions.sum(v)) < 0.2 ){
                
                    double ytv = 0.0, yty = 0.0;
                    for(int k = 0; k <= n; k++){
                        ytv += y[k]*v[k];
                        yty += y[k]*y[k];
                    }
                    double f = ytv/yty;
                    double dist2 = 0.0;
                    for(int k = 0; k <= n; k++){
                        double diff = f*y[k] - v[k];
                        dist2 += diff*diff;
                    }
                    if( dist2 < bestdist2 && f > fmin && f < fmax ){
                        bestdist2 = dist2;
                        bestf = f;
                        bestv = v.clone();
                    } 
                }

            }

        }

        return bestv;
     }
}
