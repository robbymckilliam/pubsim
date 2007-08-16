/*
 * Pnone.java
 *
 * Created on 12 August 2007, 21:11
 */

package simulator;

/**
 * Nearest point algorithm for the Pn1 lattice.
 * Works as a specialised version on the period estimation ZnLLS algorithm.
 * O(n^3log(n)).
 * UNDER CONSTRUCTION BY TIM!
 * @author Tim Mason and Robby McKilliam
 */
public class Pnone {
    
    /** Dimension of this lattice */
    protected int n;
    
    /** Storage for the nearest lattice point and
     *  it's integer index */
    protected double[] v, u;
    
    public void setDimension(int n){
        this.n = n;
        u = new double[n+2];
        v = new double[n+2];
    }
    
    public void nearestPoint(double[] y){}
    
    /**Getter for the nearest point. */
    public double[] getLatticePoint(){ return v; }
    
    /**Getter for the interger vector. */
    public double[] getIndex(){ return u; }
    
    /** 
     * Project a n+2 length vector into Pn1 space.
     * y is output, x is input (x & y can be the same array)
     * <p>
     * PRE: x.length <= y.length
     */
    public static void project(double[] x, double[] y){
        Anstar.project(x,y);
        double sumn2 = Math.floor(x.length/2.0)*
                ( Math.floor(x.length/2.0) + 1 )*
                ( 2*Math.floor(x.length/2.0) + 1) / 3.0;
        double nbar = x.length*(x.length + 1)/2 / x.length;
        double dot = 0.0;
        for(int i = 0; i < x.length; i++)
            dot += y[i]*(i+1-nbar);
        for(int i = 0; i < x.length; i++)
            y[i] = y[i] - dot/sumn2 * (i+1-nbar);
    }
    
}
