/*
 * NearestPointAlgorithmInterface.java
 *
 * Created on 12 August 2007, 20:23
 */

package lattices;

/**
 *
 * @author Robby McKilliam
 */
public interface NearestPointAlgorithmInterface {
    
    void setDimension(int n);
    double getDimension();
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

    /**
     * Return the inradius for this lattice
     */
    double inradius();
    
    /*
     * Return the center density:
     * inradius^n / volume;
     */
    double centerDensity();
    
}
