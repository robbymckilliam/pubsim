/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

/**
 * Abstract class for any Anstar algorithm
 * @author robertm
 */
public abstract class Anstar extends NearestPointAlgorithm {
    
    public double volume(){
        return Math.sqrt(1.0/(n+1));
    }
    
    public double inradius(){
        return Math.sqrt(1.0 - 1.0/(n+1));
    }
    
    
    /**
     * Project a vector into the zero-mean plane
     * y is output, x is input (x & y can be the same array)
     * <p>
     * Pre: y.length >= x.length
     */
    public static void project(double[] x, double[] y) {
	double xbar = 0.0;
	for (int i = 0; i < x.length; i++)
	    xbar += x[i];
	xbar /= x.length;
	for (int i = 0; i < x.length; i++)
	    y[i] = x[i] - xbar;
    }
    

}
