/*
 * Phin2StarZnLLS.java
 *
 * Created on 12 August 2007, 21:11
 */

package lattices;

import simulator.*;

/**
 * Nearest point algorithm for the Phin2StarZnLLS lattice.
 * Works as a specialised version on the period estimation ZnLLS algorithm.
 * O(n^3log(n)).
 * UNDER CONSTRUCTION BY TIM!
 * 
 * @author Tim Mason and Robby McKilliam
 */
public class Phin2StarZnLLS extends Phin2Star implements NearestPointAlgorithmInterface{
    
    @Override
    public void setDimension(int n){
        this.n = n;
        u = new double[n+2];
        v = new double[n+2];
    }
    
    /** Find the nearest lattice point.  TIM, this is the function
     * you need to fill in! */
    @Override
    public void nearestPoint(double[] y){}
    
    /** {@inheritDoc} */
    @Override
    public double volume(){ return 0;}
    
}
