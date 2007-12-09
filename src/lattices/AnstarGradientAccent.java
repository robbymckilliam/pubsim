/*
 * AnstarGradientAccent.java
 *
 * Created on 9 December 2007, 12:49
 */

package lattices;

/**
 * Gradient accent version of the Anstar nearest point algorithm.
 * Uses a psuedo-periodogram function that can be shown to be related
 * to the Anstar nearest point algorithm.
 * <p> 
 * Potentially this has O(n) running time.  It depends of 
 * how many iterations are required to acctually accend and whether
 * the number of iterations depends on n.  It would be interesting
 * if similar functions exist for the Pna lattices.
 * @author Robby McKilliam
 */
public class AnstarGradientAccent extends Anstar 
        implements LatticeNearestPointAlgorithm{
    
    public void nearestPoint(double[] y) {
        
    }
    
}
