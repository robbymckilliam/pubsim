/*
 * Phin2StarZnLLS.java
 *
 * Created on 12 August 2007, 21:11
 */

package lattices;

import simulator.*;

/**
 * Nearest point algorithm for the Phin2StarZnLLS lattice.
 * Works as a specialised version on the period estimation ZnLLS algorithm.
 * O(n^3log(n)).
 * UNDER CONSTRUCTION BY TIM!
 * 
 * @author Tim Mason and Robby McKilliam
 */
public class Phin2StarZnLLS extends Phin2Star implements NearestPointAlgorithmInterface{
    
    @Override
    public void setDimension(int n){
        this.n = n;
        u = new double[n+2];
        v = new double[n+2];
    }
    
    /** Find the nearest lattice point.  TIM, this is the function
     * you need to fill in! */
    @Override
    public void nearestPoint(double[] y){}
    
    /**
     * Project a n+2 length vector into Phin2StarZnLLS space.
     * y is output, x is input (x & y can be the same array)
     * <p>
     * PRE: x.length <= y.length
     */
    public static void project(double[] x, double[] y){
        AnstarVaughan.project(x,y);
        double sumn2 = sumg2(x.length);
        double nbar = x.length*(x.length + 1)/2 / x.length;
        double dot = 0.0;
        for(int i = 0; i < x.length; i++)
            dot += y[i]*(i+1-nbar);
        for(int i = 0; i < x.length; i++)
            y[i] = y[i] - dot/sumn2 * (i+1-nbar);
    }
    
    /** {@inheritDoc} */
    @Override
    public double volume(){ return 0;}
    
    /** 
     * Return the magnitude squared of the vector g with n elements.
     * See Chapter 6 of Robby's confirmation report.
     */
    public static double sumg2(int n){
        double f = Math.floor(n/2.0);
        double sum = f*(f + 1)*(2*f + 1)/3.0;
        if(n%2 == 0)
            sum += n/4.0 - f*(f+1);
        return sum;
    }
    
}
