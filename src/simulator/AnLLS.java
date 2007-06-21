/*
 * AnLLS.java
 *
 * Created on 20 June 2007, 13:39
 */

package simulator;

/**
 * This is similar to the bresenham estimator but
 * it uses relationships Zn->An->An* to search
 * each point.  This should be slower than the
 * bresenham estimator, expected to require atmost
 * O(n^2 sqrt(n)) steps with each step requiring O(n) 
 * calculations.  Total O(n^3 sqrt(n)).
 * @author Robby McKilliam
 */
public class AnLLS implements PRIEstimator {
    
    protected double[] kappa, v, g;
    protected double n;
    
    /** Creates a new instance of AnLLS */
    public AnLLS() {
    }
    
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
    
    public void setSize(int N) {
        n = N-1;
	kappa = new double[N];
        v = new double[N];
        g = new double[N];
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax){
        if (n != y.length-1)
	    setSize(y.length);
        
        Anstar.project(y, y);
        
        double bestf = 0.0;
        double bestdist2 = Double.POSITIVE_INFINITY;
        double f;
        
        for(int i = 0; i <= n; i++){
            glueVector(i);
            f = fmin;
            
                    //int count = 0;
            
            while(f < fmax){
                
                for(int j = 0; j <= n; j++){
                    if(y[j]>0.0)
                        v[j] = pround(f*y[j] - g[j]) + g[j];
                    else
                        v[j] = nround(f*y[j] - g[j]) + g[j];
                }
                //System.out.println(VectorFunctions.print(v));
                
                //if sum(v) is zero this is a lattice point
                //in An so calculate its distance from the 
                //line y
                if(VectorFunctions.sum(v) == 0.0){
                    double ytv = 0.0, yty = 0.0;
                    for(int j = 0; j <= n; j++){
                        ytv += y[j]*v[j];
                        yty += y[j]*y[j];
                    }
                    double f0 = ytv/yty;
                   // System.out.println("f0 = " + f0);
                    double dist2 = 0.0;
                    for(int j = 0; j <= n; j++){
                        double diff = f0*y[j] - v[j];
                        dist2 += diff*diff;
                    }
                    if( dist2 < bestdist2 && f0 > fmin && f0 < fmax ){
                        bestdist2 = dist2;
                        bestf = f0;
                    }      
                }
                
                //calculate the next f that moves into another
                //voronoi region in Zn
                double mindel = Double.POSITIVE_INFINITY;
                for(int j = 0; j <= n; j++){
                    double del = Double.POSITIVE_INFINITY, pn = 0.0;
                    if( y[j] > 0.0 )
                        del = (0.5 + pround(f*y[j] - g[j]) + g[j])/y[j] - f;
                    else if(y[j] < 0.0)
                        del = (-0.5 + nround(f*y[j] - g[j]) + g[j])/y[j] - f;
                    else del = Double.POSITIVE_INFINITY;
                    
                    //System.out.println(del);
                    
                    if(del > 0.0) mindel = Math.min(mindel, del);
                }
                //System.out.println("f0 = " + f0);
                //System.out.println("f = " + f);
                if( mindel <= 0.0 )
                    throw new Error( "mindel <= zero!\n" + mindel + "\n" + f + "\n" + VectorFunctions.print(y) + "\n" + VectorFunctions.print(g) + "\n" + VectorFunctions.print(v) );
                
                f += mindel;
                //System.out.println("f = " + f); 
                //System.out.println();
             
                //if(count++ > 1000)
                //    throw new Error( "too many loops" );
                
            }
            //System.out.println("finished glue" + i + " with bestf so far = " + bestf);
        }
        return bestf;
    }
    
    /**
     * Specialised round. Rounds 0.5 and -0.5 to greatest
     * magnitude. <br>
     * Eg:
     * round(-1.5) = -2
     * round(1.5) = 2
     */
    public static double round(double x){
        if(x > 0)
            return Math.floor(x + 0.5);
        else
            return -Math.floor(-x + 0.5);
    }
    
    /**
     * nround(1.5) = 1.0
     * nround(-1.5) = -2.0 
     */
    public static double nround(double x){
        return -Math.floor(-x + 0.5);
    }
    
    /**
     * pround(1.5) = 2.0
     * pround(-1.5) = 1.0 
     */
    public static double pround(double x){
        return Math.floor(x + 0.5);
    }
    
    public double varianceBound(double sigma, double[] k) {
	Anstar.project(k, kappa);
	double sk = 0;
	for (int i = 0; i < k.length; i++)
	    sk += kappa[i] * kappa[i];
	return sigma * sigma / sk;
    }
    
    /** 
     * Returns the glue vector g. This getter
     * is really only here for testing purposes.
     */
    public double[] getg() { return g; }
    
}
