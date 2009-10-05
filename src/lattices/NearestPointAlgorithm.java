/*
 * NearestPointAlgorithmInterface.java
 *
 * Created on 12 August 2007, 20:23
 */

package lattices;

import java.io.Serializable;

/**
 *
 * @author Robby McKilliam
 */
public interface NearestPointAlgorithm extends Serializable{
    
    void nearestPoint(double[] y);
    
    /**Getter for the nearest point. */
    double[] getLatticePoint();
    
    /**Getter for the integer vector. */
    double[] getIndex();
    
}
