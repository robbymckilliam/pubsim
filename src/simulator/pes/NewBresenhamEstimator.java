/*
 * NewBresenhamEstimator.java
 *
 * Created on 11 June 2007, 23:12
 */

package simulator.pes;

import simulator.*;

/**
 * This using a O(n) algorithm to get the the next
 * Voronoi region in Anstar.  Runs the nearsestPoint
 * O(nlog(n)) search to start with, then O(n) after.
 * It is theoretically only O(n) if you start from the
 * origin, but in practice this is slower. <p>
 * THIS IS WRONG!  It misses some voronoi regions.  Can
 * only step to basis vectors which is not good enough.
 * @author Robby McKilliam
 */
public class NewBresenhamEstimator extends Anstar implements PRIEstimator{
    
    double theta;
    double[] zi, fy, kappa;
    
    public void setSize(int N) {
        setDimension(N-1); // => n = N-1
        fy = new double[N];
        zi = new double[N];
        kappa = new double[N];
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
        if (n != y.length-1)
	    setSize(y.length);
        
        //y is z in my book
        double ybar = VectorFunctions.mean(y);
	for(int i=0; i<=n; i++) fy[i] = fmin*y[i];
        nearestPoint(fy);
        
        //u is k in my book
        double ubar = VectorFunctions.mean(u);
        
        double utz = 0, utu = 0;
        for(int i=0; i<=n; i++) {
        utz += (u[i] - ubar)*(y[i] - ybar);
            utu += (u[i] - ubar)*(u[i] - ubar);
        }
        double T = utz/utu;
        
        theta = 0.0;
        for(int i=0; i<=n; i++) theta += y[i] - T*u[i];
        theta /= (n+1);
        
        double bestT = 0.0, minDist2 = Double.POSITIVE_INFINITY;
        while( T > 1/fmax ){
            double bestDel = Double.POSITIVE_INFINITY;
            int besti = 0;
            double pn = 0, bestpn = 0;
            double thisDist2 = 0;
            
            for(int i=0; i<=n; i++){
                zi[i] = y[i] - u[i]*T - theta;
                thisDist2 += zi[i]*zi[i];
            }
            if(thisDist2 < minDist2){
                minDist2 = thisDist2/(T*T);
                bestT = T;
            }
            
            for(int i=0; i<=n; i++){
                double delp = Double.POSITIVE_INFINITY;
                double deln = Double.POSITIVE_INFINITY;
                
                if( (2*(u[i] - ubar) - 1) < 0 ){
                    deln = (-T - 2*zi[i])/(2*(u[i] - ubar) - 1);
                }
                if( (2*(u[i] - ubar) + 1) > 0 ){
                    delp = (T - 2*zi[i])/(2*(u[i] - ubar) + 1);
                }
                
                double del;
                if(delp < deln){
                    pn = 1;
                    del = delp;
                }
                else{
                    pn = -1;
                    del = deln; 
                }
                
                if(del < bestDel){
                    bestDel = del;
                    besti = i;
                    bestpn = pn;
                }
                
                //System.out.println(besti + ", " + bestDel + ", " + pn + ", " + deln + ", " + delp + ", " + (2*(u[i] - ubar)));
                
            }
            
            
            //System.out.println(VectorFunctions.print(zi));
            //System.out.println(VectorFunctions.print(u));
            //System.out.println(T + ", " + theta + ", " + ubar + ", " + besti + ", " + bestpn);
            //System.out.println("dist = " + thisDist2);

            
            u[besti] += bestpn;
            ubar += bestpn/(n+1);
            
            T = T - bestDel;    
            
            theta = 0.0;
            for(int i=0; i<=n; i++) theta += y[i] - T*u[i];
            theta /= (n+1);

            for(int i=0; i<=n; i++){
                zi[i] = y[i] - u[i]*T - theta;
                double roundT = Math.round(zi[i]/T);
                u[i] += Math.round(zi[i]/T);
                ubar += Math.round(zi[i]/T)/(n+1);
            }
                
                           
            //System.out.println("to " + VectorFunctions.print(u));
            //System.out.println("to " + T + ", " + theta+ ", " + ubar);
            //System.out.println("");
            
            //get the best t in the next voronoi region
            utz = 0; utu = 0;
            for(int i=0; i<=n; i++) {
            utz += (u[i] - ubar)*(y[i] - ybar);
                utu += (u[i] - ubar)*(u[i] - ubar);
            }
            T = utz/utu;
            
            //System.out.println(T);
            
        }
            
       return 1.0/bestT;
    }

    public double varianceBound(double sigma, double[] k) {
	Anstar.project(k, kappa);
	double sk = 0;
	for (int i = 0; i < k.length; i++)
	    sk += kappa[i] * kappa[i];
	return sigma * sigma / sk;
    }
    
}
