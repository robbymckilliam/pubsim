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
public interface NearestPointAlgorithmInterface extends Lattice {
    
    void nearestPoint(double[] y);
    
    /**Getter for the nearest point. */
    double[] getLatticePoint();
    
    /**Getter for the integer vector. */
    double[] getIndex();
    
}
