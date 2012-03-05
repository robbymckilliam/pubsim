/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.twod;

import pubsim.distributions.ContinuousRandomVariable;

/**
 * Interface for generating 2D wrapped data
 * @author Robby McKilliam
 */
public interface WrappedData {

    public double[][] getWrappedData();

    public double[][] getTrueData();

    public double[][] getWrappedIntegers();

    public void generateData();

    public void setSize(int M, int N);

    public void setNoiseGenerator(ContinuousRandomVariable noise);

}
