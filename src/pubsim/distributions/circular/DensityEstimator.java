/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import pubsim.optimisation.SingleVariateFunction;
import static pubsim.Util.fracpart;

/**
 * Circular kernel density estimator based on periodic convolution with a
 * kernel function.
 * @author Robby McKilliam
 */
public class DensityEstimator extends pubsim.distributions.DensityEstimator {

    /**
     * Constructor takes an array of d and a kernel function.
     */
    public DensityEstimator(final double[] data, SingleVariateFunction kernel){
        super(data, kernel);
    }

    @Override
    public double pdf(double x) {
        double pdfsum = 0.0;
        for(int n = 0; n < d.length; n++)
            pdfsum += ker.value(fracpart(x - d[n]));
        return pdfsum/d.length;
    }

}
