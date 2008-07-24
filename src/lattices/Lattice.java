/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;

/**
 * Interface for a Lattice
 * @author Robby McKilliam
 */
public interface Lattice {
    
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
    
    void setDimension(int n);
    
    double getDimension();
    
    /**
     * Return the generator matrix for this latttice
     * @return double[][] containing the generator matrix
     */
    Matrix getGeneratorMatrix();

}
