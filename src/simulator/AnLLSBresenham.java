/*
 * AnLLSBresenham.java
 *
 * Created on 21 June 2007, 10:43
 */

package simulator;

/**
 * This is the bresenham version of AnLLS.  It should be fast and
 * I think that it is reasonably easy to prove that it will always
 * work even though it does miss som voronoi regions.  Essentially
 * it picks the next 'atleast corner connected' box in Zn that is
 * closest to the line.  This should run in O(n^3) which is probably
 * faster than the An* bresenham estimator.  Certainly it is faster
 * in simulation.
 * @author Robby
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
        Anstar.project(y,y);

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

                Anstar.project(v,v);
                
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
                } 

            }

        }

        return bestf;
     }
     
}
