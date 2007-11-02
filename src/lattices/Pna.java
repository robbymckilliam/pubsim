/*
 * Pnx.java
 *
 * Created on 2 November 2007, 13:24
 */

package lattices;

import simulator.VectorFunctions;

/**
 * Class that solves the nearst point algorithm for the family of lattices
 * P_n^alpha.  This is a suboptimal sampling approach.  It is polynomial time 
 * in n but exponential in alpha.
 * @author robertm
 */
public class Pna implements LatticeNearestPointAlgorithm{
    
    protected int a;
    protected double[] u, v, g, yt, yp;
    protected int n;
    
    /** Must set at when constucted */
    public Pna(int a){
        this.a = a;
        setDimension(0);
    }
    
    public void setDimension(int n){
        this.n =  n;
        u = new double[n + a];
        v = new double[n + a];
        yt = new double[n + a];
        yp = new double[n + a];
        g = createg(n,a);
    }
    
    public void nearestPoint(double[] y){
        if(u.length != y.length)
            setDimension(y.length-a);
        
        //project point into this space of lattice P_n^a
        project(y, yp, a);
        
        double Dmin = Double.POSITIVE_INFINITY;
        if(a > 0){       
            double magg = VectorFunctions.magnitude(g);
            double step = magg/Math.pow(n,a);
            for(double s = 0; s < magg; s+=step){
                for(int i = 0; i < y.length; i++)
                    yt[i] = y[i] + s*g[i];
                Pna pna = new Pna(a-1);
                pna.setDimension(n);
                pna.nearestPoint(yt);
                //this is bad practice, I am reusing memory
                project(pna.getIndex(), pna.getLatticePoint(), a);
                double D = VectorFunctions.distance_between2(yp, 
                                                    pna.getLatticePoint());
                if(D < Dmin){
                    Dmin = D;
                    System.arraycopy(pna.getIndex(), 0, u, 0, u.length);
                }
            }
        }else{
            for(int i = 0; i < y.length; i++)
                u[i] = Math.round(y[i]);
        }
        
        project(u, v, a);
        
    }
    
    public double[] getLatticePoint() { return v; }
    
    public double[] getIndex() { return u; }
    
    /** creates the orthogonal vector for Pna.  Allocates memory*/
    protected static double[] createg(int n, int a){
        double[] g = new double[n+a];
        for(int i = 0; i < n+a; i++)
            g[i] = Math.pow(i+1,a-1);      
        
        project(g,g,a-1);

        return g;
    }
    
    /** 
     * Project x into the space of the lattice Pna and return
     * the projection into y.  Requires recursion, runs in O(n a) time
     * PRE: x.length = y.length
     */
    public static void project(double[] x, double[] y, int a){
        if(a > 0){
            project(x,y,a-1);
            double[] g = createg(x.length-a, a);
            double gtg = VectorFunctions.sum2(g);
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
