/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes.crb;

/**
 * Interface for lower bounds for frequency estimation.
 * @author Robby McKilliam
 */
public interface BoundCalculator {
    
    public void setN(int N);
    public void setVariance(double var);
    
}
