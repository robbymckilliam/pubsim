/*
 * BasisScrambleEstimator.java
 *
 * Created on 28 April 2007, 12:49
 */

package simulator;

/**
 * Keeps moving along the basis vectors  that are closest to the
 * search line.  Guaranteed ML.  Something like o(n^2.5) time.
 * @author Robby McKilliam
 */
public class BasisScrambleEstimator extends Anstar implements PRIEstimator {
    
    double[] zeta, fzeta, zetaproj, posv, negv;
    
    public void setSize(int n){
        setDimension(n-1);
        zeta = new double[n];
	fzeta = new double[n];
        zetaproj = new double[n];
        posv = new double[n];
        negv = new double[n];
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax)
    {
      
        double ymean = VectorFunctions.mean(y);
        for (int i = 0; i <= n; i++)
            zeta[i] = y[i] - ymean;
        
        double zetatzeta = 0.0;
        for (int i = 0; i <= n; i++){
            zetatzeta += zeta[i]*zeta[i];
            fzeta[i] = fmin * zeta[i];
        }
        nearestPoint(fzeta);
        
        for (int i = 0; i <= n; i++)
            zetaproj[i] = zeta[i]*zeta[i]/zetatzeta;
        
        double fhat = 0.0, f = 0.0, bestL = Double.POSITIVE_INFINITY;
        double[] vp = v;
        
        while(f < fmax){
            
            double posvproj = 0.0, posfproj = 0.0;
            double negvproj = 0.0, negfproj = 0.0;
            for (int i = 0; i <= n; i++){
               posv[i] = vp[i] - 1.0/(n+1.0); 
               posvproj += Math.pow(v[i] - vp[i]*zetaproj[i],2);
               posfproj += v[i]*zeta[i]/zetatzeta;
               negv[i] = vp[i] + 1.0/n;
               negvproj += Math.pow(v[i] - vp[i]*zetaproj[i],2);
               negfproj += vp[i]*zeta[i]/zetatzeta;
            }
            
            System.out.println(VectorFunctions.print(vp));
            
            double bproj, L = Double.POSITIVE_INFINITY, f0, fold = f;
            int besti = 0;
            boolean bestIsPos = true;
            for(int i = 0; i <= n; i++){
                bproj = posvproj + Math.pow(1 -  zetaproj[i], 2)*
                        ( Math.pow( posv[i] + 1 - 1.0/(n+1.0) , 2) 
                        - Math.pow( posv[i] - 1.0/(n+1.0) , 2) );
                f0 = posfproj + zeta[i]/zetatzeta;
                if(bproj < L && f0 > fold){
                    L = bproj;
                    besti = i;
                    f = f0;
                }
                bproj = negvproj + Math.pow(1 -  zetaproj[i], 2)*
                        ( Math.pow( negv[i] - 1 + 1.0/(n+1.0) , 2) 
                        - Math.pow( negv[i] + 1.0/(n+1.0) , 2) );
                f0 = negfproj + zeta[i]/zetatzeta;
                if(bproj < L && f0 > fold){
                    L = bproj;
                    besti = i;
                    f = f0;
                    bestIsPos = false;
                } 
            }
            
            //make v the next lattice point along the line
            if(bestIsPos){
                vp = posv;
                vp[besti] += 1.0;
            }else{
                vp = negv;
                vp[besti] -= 1.0;
            }
            
            //if the current estimate is the best, then calculate
            //the value of fhat corresponding to the current x
            if(L < bestL){
                bestL = L;
                fhat = f;
            }
            
            System.out.println(L);
            //System.out.println(VectorFunctions.print(u));
                 
        }
        
        return fhat;
    }
    
    /**
     * One would hope that this estimator approached the CRB
     */
    public double varianceBound(double sigma, double[] s){
        return 0.0;
    }
    
}
