/*
 * PhinaStarEfficient.java
 *
 * Created on 3 November 2007, 13:48
 */

package lattices;

import simulator.Util;
import Jama.Matrix;
import simulator.VectorFunctions;

/**
 * This is a version of PhinaStar that avoids allocating and deallocated memory all
 * the time.  This is achieved by precalculating and storing all 
 * Pnb, b<=a algorithms.
 * <p>
 * The biggest gain comes from createg and project not recuring so much. 
 * @author Robby McKilliam
 */
public class PhinaStarEfficient extends PhinaStar {
    
    /** Store P_n^(a-1) that is used for recursion. */
    protected PhinaStarEfficient pnam1;
    
    /** 
     * gtg = VectorFunctions.sum2(g)
     * This gets used frequenctly so it is precalculated.
     */
    private double gtg;
    
     /** When a = 1, we can use the O(n) An* algorithm */
    protected Anstar anstar;
    
    public PhinaStarEfficient(int a) { 
        super(a);
    }
    
    public PhinaStarEfficient(int a, int n){
        super(a,n);
    }
    
    @Override
    public void setDimension(int n){
        this.n =  n;
        
        //setup pnam1
        if(a > 0)
            pnam1 = new PhinaStarEfficient(a-1, n+1);
        
        u = new double[n + a];
        v = new double[n + a];
        yt = new double[n + a];
        yp = new double[n + a];
        createg();
        gtg = VectorFunctions.sum2(g);
        if( a == 1 ) {
            anstar = new AnstarBucket(n);
        }
    }
    
    @Override
    public void nearestPoint(double[] y){
        if(u.length != y.length)
            setDimension(y.length-a);
        
        //project point into this space of lattice P_n^a
        project(y, yp);
        
        double Dmin = Double.POSITIVE_INFINITY;
        if(a > 1){       
            //This step length is motivated by the ambiguity of
            //polynomial phase estiamtion.  I have not formally
            //worked it out in terms of lattices.
            double magg = 2*Math.sqrt(gtg)/Util.factorial(a);
            //double step = magg/Math.pow(n,a);
            double step = magg/Math.pow(n,a);
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
        }else if( a == 1){
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
     * holder contains all the PhinaStar's that is needs
     */
    protected void project(double[] x, double[] y){
        if(a > 0){
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
        g = new double[n + a];
        if( a > 0 ){
            for(int i = 0; i < n+a; i++)
                g[i] = Math.pow(i+1,a-1);
            pnam1.project(g,g);
        }
    }
    
    /** {@inheritDoc} */
    /*
    @Override
    public double volume(){
        //if this is the Zn lattice
        if(a == 0){
            return 1.0;
        }else{
            //calculate det( I - gg'/g'g )
            double[][] gm = new double[1][n+a];
            System.arraycopy(g, 0, gm[0], 0, n+a);
            Matrix gM = new Matrix(gm);
            Matrix M = Matrix.identity(n+a,n+a).minus(
                    gM.transpose().times(gM).times(1.0/gtg));
            M = M.getMatrix(0, n-1, 0, n+a-1);
            double det = M.times(M.transpose()).det();
            //double det = VectorFunctions.stableDet(M.times(M.transpose()));
            return Math.sqrt(det) * pnam1.volume();
        }
    }
    */
    
    /** 
     * Returns the vector g for this PhinaStar where
     * a is the input to the function.
     */
    public double[] getg(int a){
        double[] ret = null;
        if(a < this.a )
            ret = pnam1.getg(a);
        else
            ret = getg();
        return ret;
    }
    
    /** {@inheritDoc} */
    @Override
    public double inradius(){
        double r2 = 1.0;
        for(int i = 1; i <= a; i ++){
            double[] tg = getg(i);
            r2 -= tg[0]*tg[0]/VectorFunctions.sum2(tg);
        }
        return Math.sqrt(r2)/2.0;
    }
    
}
