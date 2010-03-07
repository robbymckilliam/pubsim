/*
 * VnmStarSampledEfficient.java
 *
 * Created on 3 November 2007, 13:48
 */

package lattices;

import lattices.Anstar.Anstar;
import lattices.Anstar.AnstarBucket;
import simulator.Util;
import simulator.VectorFunctions;

/**
 * This is m version of VnmStarSampled that avoids allocating and deallocated memory all
 * the time.  This is achieved by precalculating and storing all 
 * Pnb, b<=m algorithms.
 * <p>
 * The biggest gain comes from createg and project not recuring so much. 
 * @author Robby McKilliam
 */
public class VnmStarSampledEfficient extends VnmStarSampled {
    
    /** Store P_n^(m-1) that is used for recursion. */
    protected VnmStarSampledEfficient pnam1;
    
    /** 
     * gtg = VectorFunctions.sum2(g)
     * This gets used frequenctly so it is precalculated.
     */
    private double gtg;
    
     /** When m = 1, we can use the O(n) An* algorithm */
    protected Anstar anstar;
    
    public VnmStarSampledEfficient(int a) {
        super(a);
    }
    
    public VnmStarSampledEfficient(int a, int n){
        super(a,n);
    }
    
    @Override
    public void setDimension(int n){
        this.n =  n;
        
        //setup pnam1
        if(m > 0)
            pnam1 = new VnmStarSampledEfficient(m-1, n+1);
        
        u = new double[n + m];
        v = new double[n + m];
        yt = new double[n + m];
        yp = new double[n + m];
        createg();
        gtg = VectorFunctions.sum2(g);
        if( m == 1 ) {
            anstar = new AnstarBucket(n);
        }
    }
    
    @Override
    public void nearestPoint(double[] y){
        if(u.length != y.length)
            setDimension(y.length-m);
        
        //project point into this space of lattice P_n^m
        project(y, yp);
        
        double Dmin = Double.POSITIVE_INFINITY;
        if(m > 1){
            //This step length is motivated by the ambiguity of
            //polynomial phase estiamtion.  I have not formally
            //worked it out in terms of lattices.
            double magg = 2*Math.sqrt(gtg)/Util.factorial(m);
            //double step = magg/Math.pow(n,m);
            double step = magg/Math.pow(n,m);
            for(double s = 0; s < magg; s+=step){
                for(int i = 0; i < y.length; i++)
                    yt[i] = y[i] + s*g[i];
                pnam1.nearestPoint(yt);
                //this is bad practice, I am reusing memory
                VectorFunctions.projectOrthogonal(g,
                        pnam1.getLatticePoint(), 
                        pnam1.getLatticePoint());
                double D = VectorFunctions.distance_between2
                                    (yp, pnam1.getLatticePoint());
                if(D < Dmin){
                    Dmin = D;
                    System.arraycopy(pnam1.getIndex(), 0, u, 0, u.length);
                }
            }
        //It's An* so run the O(n) An* algorithm
        }else if( m == 1){
            anstar.nearestPoint(y);
            u = anstar.getIndex();
        //It's Z^n so round.  This should never be reached.
        }else{
            for(int i = 0; i < y.length; i++)
                u[i] = Math.round(y[i]);
        }
        
        project(u, v);
        
    }
    
    /** 
     * non static version of project that assumes that the
     * holder contains all the VnmStarSampled's that is needs
     */
    protected void project(double[] x, double[] y){
        if(m > 0){
            pnam1.project(x,y);
            double dot = VectorFunctions.dot(y,g);
            for(int i = 0; i < x.length; i++){
                y[i] = y[i] - dot/gtg * g[i];
            }
        }
        else{
            System.arraycopy(x, 0, y, 0, x.length);
        }
    }
    
    /** 
     * Creates the orthogonal vector. 
     * Assumes that pnam1 is already setup.
     * This is protected for good reason!
     */
    protected void createg(){
        g = new double[n + m];
        if( m > 0 ){
            for(int i = 0; i < n+m; i++)
                g[i] = Math.pow(i+1,m-1);
            pnam1.project(g,g);
        }
    }
    
    /** {@inheritDoc} */
    /*
    @Override
    public double volume(){
        //if this is the Zn lattice
        if(m == 0){
            return 1.0;
        }else{
            //calculate det( I - gg'/g'g )
            double[][] gm = new double[1][n+m];
            System.arraycopy(g, 0, gm[0], 0, n+m);
            Matrix gM = new Matrix(gm);
            Matrix M = Matrix.identity(n+m,n+m).minus(
                    gM.transpose().times(gM).times(1.0/gtg));
            M = M.getMatrix(0, n-1, 0, n+m-1);
            double det = M.times(M.transpose()).det();
            //double det = VectorFunctions.stableDet(M.times(M.transpose()));
            return Math.sqrt(det) * pnam1.volume();
        }
    }
    */
    
    /** 
     * Returns the vector g for this VnmStarSampled where
     * m is the input to the function.
     */
    public double[] getg(int a){
        double[] ret = null;
        if(a < this.m )
            ret = pnam1.getg(a);
        else
            ret = getg();
        return ret;
    }
    
}
