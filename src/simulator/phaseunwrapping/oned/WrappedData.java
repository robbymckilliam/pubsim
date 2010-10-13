/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.oned;

import distributions.RandomVariable;

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

    public void setNoiseGenerator(RandomVariable noise);

}
