/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

/**
 * Abstract class for Nearest point algorithms.  You are not required
 * to use this but it saves you from writting getters and setters and
 * uses some standard variable names.
 * ie.  u is the index of the nearest lattice point.
 *      v is the neares lattice point.
 *      n is the dimension of this lattice.
 * @author Robby McKilliam
 */
public abstract class NearestPointAlgorithm 
    implements NearestPointAlgorithmInterface{
    
    protected int n;
    protected double[] u, v;
    
    /**Getter for the nearest point. */
    @Override
    public double[] getLatticePoint() {return v;}
    
    /**Getter for the interger vector. */
    @Override
    public double[] getIndex() {return u;}

}
