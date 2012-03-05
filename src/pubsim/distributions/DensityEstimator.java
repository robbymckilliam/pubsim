/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions;

import pubsim.optimisation.SingleVariateFunction;

/**
 * Standard kernel density estimator based on convolution with a
 * kernel function. See:
 *
 * Emanuel Parzen, “On estimation of a probability density function and mode,”
 * Ann. Math. Statist., vol. 33, pp. 1065–1076, 1962.
 * 
 * M. Rosenblatt, “Remarks on some nonparametric estimates of a density
 * function,” Ann. Math. Statist., vol. 27, pp. 832–837, 1956.
 *
 * @author Robby McKilliam
 */
public class DensityEstimator extends AbstractRandomVariable {

    protected final double[] d;
    protected final ContinuousRandomVariable ker;

    /**
     * Constructor takes an array of d and a kernel function represented
     * by a ContinuousRandomVariable (really just the pdf function is needed).
     */
    public DensityEstimator(final double[] data, ContinuousRandomVariable kernel){
        d = data;
        ker = kernel;
    }

    public double pdf(double x) {
        double pdfsum = 0.0;
        for(int n = 0; n < d.length; n++) pdfsum += ker.pdf(x - d[n]);
        return pdfsum/d.length;
    }

    public double getMean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getVariance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
