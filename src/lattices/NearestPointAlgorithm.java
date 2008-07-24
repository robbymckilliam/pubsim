/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

/**
 * Abstract class for Nearest point algorithms.  You are not required
 * to use this but it saves you from writting getters and setters and
 * uses some standard variable names.  Please don't use this abstract class
 * if you don't intend to use the following numenclature!
 * ie.  u is the index of the nearest lattice point.
 *      v is the neares lattice point.
 *      n is the dimension of this lattice.
 * @author Robby McKilliam
 */
public abstract class NearestPointAlgorithm 
    implements NearestPointAlgorithmInterface{
    
    /** The dimension of the lattice */
    protected int n;
    
    /** The nearest lattice point */
    protected double[] v;
    
    /** The integer index that generate the nearest lattice point */
    protected double[] u;
    
    
    /**Getter for the nearest point. */
    @Override
    public double[] getLatticePoint() {return v;}
    
    /**Getter for the interger vector. */
    @Override
    public double[] getIndex() {return u;}
    
    /*
     * Return the center density:
     * inradius^n / volume;
     */
    public double centerDensity(){
        return Math.pow(inradius(), getDimension())/volume();
    }
    
    /** 
     * This assumes that n is the dimension of your lattice! 
     * Don't use this abstract class if you don't intend n to
     * be the dimension of your lattice.
     */
    public double getDimension(){ return n; }

}
