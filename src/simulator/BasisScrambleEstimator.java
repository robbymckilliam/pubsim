/*
 * BasisScrambleEstimator.java
 *
 * Created on 28 April 2007, 12:49
 */

package simulator;

/**
 * Keeps moving along the basis vectors  that are closest to the
 * search line.  Guaranteed ML.  Something like o(n^2.5) time.
 * Unfortunately, I now realise that this must actually add a
 * relavant vector, not just a basis vector such that it is closest
 * to the line zeta.  I am not sure how to do this efficiently yet.
 * @author Robby McKilliam
 */
public class BasisScrambleEstimator extends Anstar implements PRIEstimator {
    
    double[] zeta, fzeta;
    double N;
    
    public void setSize(int n){
        N = n;
        setDimension(n);
        zeta = new double[n];
        fzeta = new double[n];
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax)
    {
        if(n != y.length)
            setSize(y.length);
        
        double ymean = VectorFunctions.mean(y);
        for (int i = 0; i < N; i++)
            zeta[i] = y[i] - ymean;
        
        double zetatzeta = 0.0;
        for (int i = 0; i < N; i++){
            zetatzeta += zeta[i]*zeta[i];
            fzeta[i] = fmin * zeta[i];
        }
        nearestPoint(fzeta);
        
        double fhat = 0.0, f = 0.0, bestL = Double.POSITIVE_INFINITY;
        
        while(f < fmax){
            
            
            double pvproj = 0.0, pfproj = 0.0;
            for (int i = 0; i < N; i++){
               v[i] -= 1.0/N; 
               pvproj += Math.pow(v[i] - v[i]*zeta[i]*zeta[i]/zetatzeta, 2);
               pfproj += v[i]*zeta[i];
            }
            pfproj /= zetatzeta;
            
            double bproj = 0.0, L = Double.POSITIVE_INFINITY, f0, fold = f;
            int besti = 0;
            boolean bestIsPos = true;
            for(int i = 0; i < N; i++){
                bproj = pvproj + Math.pow(1 -  zeta[i]*zeta[i]/zetatzeta, 2)*
                        ( Math.pow( v[i] + 1.0 - 1.0/N , 2) 
                        - Math.pow( v[i] - 1.0/N , 2) );
                f0 = pfproj + zeta[i]/zetatzeta;
                bproj /= (f0*f0);
                if(bproj < L && f0 > fold){
                    L = bproj;
                    besti = i;
                    f = f0;
                }
            }
            
            
            /*
            //slow way
            double L=Double.POSITIVE_INFINITY, Li, fold = f, f0;
            int besti = 0;
            boolean negbasis = false;
            for(int i = 0; i < N; i++){
                v[i] += 1.0;
                Li = 0.0; 
                f0 = 0.0;
                for(int k = 0; k < N; k++){
                    Li += Math.pow(v[k] - 1.0/N - (v[k] - 1.0/N)*zeta[k]*zeta[k]/zetatzeta, 2); 
                    f0 += (v[k] - 1.0/N)*zeta[k]/zetatzeta;
                }
                if(Li < L && f0 > fold){
                    L = Li;
                    f = f0;
                    besti = i;
                }
                System.out.println(Li + "\t" + L + "\t" + i + "\t" + besti + "\t" + f0 + "\t" + negbasis);
                v[i] -= 2.0;
                Li = 0.0;
                f0 = 0.0;
                for(int k = 0; k < N; k++){
                    Li += Math.pow(v[k] + 1.0/N - (v[k] + 1.0/N)*zeta[k]*zeta[k]/zetatzeta, 2);
                    f0 += (v[k] + 1.0/N)*zeta[k]/zetatzeta;
                }
                if(Li < L && f0 < fold){
                    L = Li;
                    besti = i;
                    f = f0;
                    negbasis = true;
                }
                System.out.println(Li + "\t" + L + "\t" + i + "\t" + besti + "\t" + f0 + "\t" + negbasis);
            }
             */
            
            //make v the next lattice point along the line
            if(!negbasis){
                for(int i = 0; i < N; i++)
                    v[i] -= 1.0/N;
                v[besti] += 1.0;
            }
            else{
                for(int i = 0; i < N; i++)
                    v[i] += 1.0/N;
                v[besti] -= 1.0;
            }
            
            //System.out.println("1 " + L + "\t" + bestL + "\t" + f + "\t" + fhat);
            
            //if the current estimate is the best, then calculate
            //the value of fhat corresponding to the current x
            if(L < bestL && f > fmin){
                bestL = L;
                fhat = f;
            }
            
            System.out.println("2 " + L + "\t" + bestL + "\t" + f + "\t" + fhat);
            
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
