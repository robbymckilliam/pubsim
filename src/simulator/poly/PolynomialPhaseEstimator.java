/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

/**
 * 
 * @author Robby McKilliam
 */
public interface PolynomialPhaseEstimator{
    
    /** Set the number of samples */
    public void setSize(int n);
    
    /** 
     * Run the estimator on recieved real and imaginary signal.
     * Returns an array of polynomial parameters.
     */
    public double[] estimate(double[] real, double[] imag);

}
