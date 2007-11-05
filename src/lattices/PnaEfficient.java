/*
 * PnaEfficient.java
 *
 * Created on 3 November 2007, 13:48
 */

package lattices;

import java.util.ArrayList;
import simulator.VectorFunctions;

/**
 * This is a version of Pna that avoids allocating and deallocated memory all
 * the time.  This is achieved by precalculating and storing all 
 * Pnb, b<=a algorithms.
 * <p>
 * The biggest gain comes from createg and project not recuring so much.  
 * @author Robby McKilliam
 */
public class PnaEfficient extends Pna implements LatticeNearestPointAlgorithm {
    
    protected PnaEfficient[] holder;
    private double gtg;
    
    /** {@inheritDoc} */
    public PnaEfficient(int a) { 
        super(a);
    }
    
    /** {@inheritDoc}  */
    public PnaEfficient(int a, int n){
        super(a,n);
    }
    
    /** 
     * Creates a PnaEfficient with a holder.  This is protected
     * because is should only be used to create the PnaEfficient
     * objects that are contained in the holder list
     * That list is created when setDimension is called
     */
    protected PnaEfficient(PnaEfficient[] holder, int a, int n) { 
        super(a);
        this.holder = holder;
        this.n =  n;
        u = new double[n + a];
        v = new double[n + a];
        yt = new double[n + a];
        yp = new double[n + a];
        g = createg(n,a);
        gtg = VectorFunctions.sum2(g);
    }
    
    public void setDimension(int n){
        this.n =  n;
        u = new double[n + a];
        v = new double[n + a];
        yt = new double[n + a];
        yp = new double[n + a];
        g = createg(n,a);
        gtg = VectorFunctions.sum2(g);
        holder = new PnaEfficient[a+1];
        holder[a] = this;
        for(int i = 0; i < a; i++)
            holder[i] = new PnaEfficient(holder, i, n + a - i);
    }
    
    public void nearestPoint(double[] y){
        if(u.length != y.length)
            setDimension(y.length-a);
        
        //project point into this space of lattice P_n^a
        project(y, yp);
        
        double Dmin = Double.POSITIVE_INFINITY;
        if(a > 0){       
            double magg = Math.sqrt(gtg);
            double step = 8*magg/Math.pow(n,a);
            for(double s = 0; s < magg; s+=step){
                for(int i = 0; i < y.length; i++)
                    yt[i] = y[i] + s*g[i];
                holder[a-1].nearestPoint(yt);
                //this is bad practice, I am reusing memory
                project(holder[a-1].getIndex(), holder[a-1].getLatticePoint());
                double D = VectorFunctions.distance_between2
                                    (yp, holder[a-1].getLatticePoint());
                if(D < Dmin){
                    Dmin = D;
                    System.arraycopy(holder[a-1].getIndex(), 0, u, 0, u.length);
                }
            }
        }else{
            for(int i = 0; i < y.length; i++)
                u[i] = Math.round(y[i]);
        }
        
        project(u, v);
        
    }
    
    /** 
     * non static version of project that assumes that the
     * holder contains all the Pna's that is needs
     */
    protected void project(double[] x, double[] y){
        if(a > 0){
            holder[a-1].project(x,y);
            double dot = VectorFunctions.dot(y,g);
            for(int i = 0; i < x.length; i++){
                y[i] = y[i] - dot/gtg * g[i];
            }
        }
        else{
            System.arraycopy(x, 0, y, 0, x.length);
        }
    }
    
}
