/*
 * LatticeNearestPointAlgorithm.java
 *
 * Created on 12 August 2007, 20:23
 */

package simulator;

/**
 *
 * @author Robby
 */
public interface LatticeNearestPointAlgorithm {
    
    void setDimension(int n);
    void nearestPoint(double[] y);
    
    /**Getter for the nearest point. */
    double[] getLatticePoint();
    
    /**Getter for the integer vector. */
    double[] getIndex();
    
}
