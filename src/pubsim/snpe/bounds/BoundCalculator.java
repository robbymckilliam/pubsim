/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.snpe.bounds;

/**
 *
 * @author Robby McKilliam
 */
public interface BoundCalculator {

    /**
     * Return the clairvoyant CRB for the set parameters
     */
    double getBound();

    /**
     *
     * Set the parameter for the geometrically distributed
     * index differences.
     * @param p the geometric parameter in range [0,1]
     */
    void setGeometicParameter(double p);

    /**
     * Set the data length
     */
    void setLength(int N);

    /**
     * Set the vector of indicies
     */
    void setS(double[] s);
    
    /** Set the true period of the signal */
    void setPeriod(double T);
    
    /** Set the variance of the noise */
    void setVariance(double var);

}
