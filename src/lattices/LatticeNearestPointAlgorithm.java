/*
 * LatticeNearestPointAlgorithm.java
 *
 * Created on 12 August 2007, 20:23
 */

package lattices;

/**
 *
 * @author Robby McKilliam
 */
public interface LatticeNearestPointAlgorithm {
    
    void setDimension(int n);
    void nearestPoint(double[] y);
    
    /**Getter for the nearest point. */
    double[] getLatticePoint();
    
    /**Getter for the integer vector. */
    double[] getIndex();
    
    /** 
     * Return the volume of the fundamental region
     * of the lattice.  This is the square root of 
     * the determinat of the gram matrix
     */
    double volume();
    
}
