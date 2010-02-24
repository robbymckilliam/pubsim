/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import java.io.Serializable;

/**
 * Interface for a Lattice
 * @author Robby McKilliam
 */
public interface Lattice extends Serializable {
    
    /** 
     * Return the volume of the fundamental region
     * of the lattice.  This is the square root of 
     * the determinat of the gram matrix
     */
    double volume();

    /**
     * Return the in radius for this lattice
     */
    double inradius();

    /**
     * Return the convering radius for this lattice
     */
    double coveringRadius();
    
    /*
     * Return the center density:
     * inradius^n / volume;
     */
    double centerDensity();

    /*
     * Return the log2 of the center density:
     * inradius^n / volume;
     */
    double logCenterDensity();

    /*
     * log2 logarithm of the sphere packing density
     */
    double logPackingDensity();

    /*
     * log2 of the volume of the lattice.
     */
    double logVolume();

    /*
     * Sphere packing density.
     */
    double packingDensity();

    /*
     * Coding bound.  This is the probability of error estimate
     * given by Conway and Sloane SPLAG page 71.
     */
    double probCodingError(double S);

     /*
     * The number of short vectors in the lattice.
     */
    long kissingNumber();

    /**
     * Set/reset the dimension of this lattice.
     * There are reasonable arguments for this method being deprecated
     * (i.e. it's mutable).
     */
    void setDimension(int n);
    
    int getDimension();
    
    /**
     * Return the generator matrix for this latttice
     * @return double[][] containing the generator matrix
     */
    Matrix getGeneratorMatrix();

}
