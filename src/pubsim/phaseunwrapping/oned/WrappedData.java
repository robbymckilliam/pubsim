/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.oned;

import pubsim.distributions.ContinuousRandomVariable;

/**
 *
 * @author Robby McKilliam
 */
public interface WrappedData {

    public double[] getWrappedData();

    public double[] getTrueData();

    public double[] getWrappedIntegers();

    public void generateData();

    public void setSize(int N);

    public int getSize();

    public void setNoiseGenerator(ContinuousRandomVariable noise);

}
