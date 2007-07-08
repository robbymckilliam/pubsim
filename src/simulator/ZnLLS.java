/*
 * ZnLLS.java
 *
 * Created on 8 July 2007, 08:55
 */

package simulator;

/**
 * Zn->An->An* line search.  Uses the sorted
 * Zn search proposed by Dan and Vaughan.  This should
 * run in O(n^3log(n)) which is the same as the An*
 * Bresenham line search.
 * @author Robby McKilliam
 */
public class ZnLLS implements PRIEstimator {
    
    Double[] p;
    Integer[] ip;
    double[] g;
    double n;
     
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
        p = new Double[N];
        ip = new Integer[N];
        for(int i = 0; i <= n; i++){
            ip[i] = new Integer(i);
        }
        g = new double[N];
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax){
        return 0;
    }
    
    /** Not implemented here. */
    public double varianceBound(double sigma, double[] s){
        return 0;
    }
    
}
