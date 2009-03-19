/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.twod;

/**
 * Interface for two dimensional unwrapping algorithms
 * @author Robby McKilliam
 */
public interface TwoDUnwrapperInterface {

    /** Return the unwrapped image */
    public double[][] unwrap(double[][] y);

    /** Return the integers that results from the unwrapping */
    public double[][] unwrapIntegers(double[][] y);

    /** Set the size of the image */
    public void setSize(int M, int N);

}
