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
        //System.out.println("y = " + VectorFunctions.print(y));
        //System.out.println();

        double maxy = 0;
        int maxi = 0;   //position of the maximum value of y[i]
        for(int i = 0; i <= n; i++){
            if(maxy < Math.abs(y[i])){
                maxy = Math.abs(y[i]);
                maxi = i;
            }
        }
        //System.out.println("maxy = " + maxy + ", maxi = " + maxi);

        //calculate the error term to be added each iteration
        for(int i = 0; i <= n; i++)
            d[i] = y[i]/maxy;

        //System.out.println(VectorFunctions.print(d));
        
        for(int i = 0; i <= n; i++){
            glueVector(i);

            //calculate the number of iterations needed
            int iters = (int)Math.ceil(Math.abs(Math.round(fmax*y[maxi] - g[maxi]) - Math.round(fmin*y[maxi] - g[maxi])));
            //System.out.println("iters = " + iters);

            //go to the first point at fmin
            //double decpart = (fmin*y[maxi] - g[maxi]) - Math.rint(fmin*y[maxi] - g[maxi]);
            for(int j = 0; j <=n ; j++)
                fy[j] = fmin*y[j] - g[j];
            
            //System.out.println("g = " + VectorFunctions.print(g));
            //System.out.println("decpart = " + decpart);
            //System.out.println(VectorFunctions.print(fy));

            //iterate over Zn
            for(int j = 0; j < iters*2; j++){

                //move the the next lattice point
                for(int k = 0; k <=n ; k++){
                    fy[k] += d[k]/2;
                    v[k] = Math.round(fy[k]);
                }

                //if sum(v) is zero this is a lattice point
                //in An so calculate its distance from the 
                //line y
                if(VectorFunctions.sum(v) == 0.0){
                    /*double ytv = 0.0, yty = 0.0;
                    for(int k = 0; k <= n; k++){
                        ytv += y[k]*( v[k] + g[k] );
                        yty += y[k]*y[k];
                    }
                    double f = ytv/yty;*/
                    double vtv = 0.0, ytv = 0.0;
                    for(int k = 0; k <= n; k++){
                        ytv += y[k]*( v[k] + g[k] );
                        vtv += ( v[k] + g[k] )*( v[k] + g[k] );
                    }
                    double f = vtv/ytv;
                   // System.out.println("f0 = " + f0);
                    double dist2 = 0.0;
                    for(int k = 0; k <= n; k++){
                        double diff = f*y[k] - ( v[k] + g[k] );
                        dist2 += diff*diff;
                    }
                    if( dist2 < bestdist2 && f > fmin && f < fmax ){
                        bestdist2 = dist2;
                        bestf = f;
                    } 
                    
                    /*
                    System.out.println(VectorFunctions.print(VectorFunctions.add(v, g)));
                    System.out.println("bestf = " + bestf);
                    System.out.println("f = " + f);
                    System.out.println("bestdist2 = " + dist2);
                     */
                    
                     

                }


            }
           //System.out.println("finished glue " + i);
           //System.out.println();

        }

        return bestf;
     }
}
