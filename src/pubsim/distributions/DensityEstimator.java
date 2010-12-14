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
    protected final SingleVariateFunction ker;

    /**
     * Constructor takes an array of d and a kernel function.
     */
    public DensityEstimator(final double[] data, SingleVariateFunction kernel){
        d = data;
        ker = kernel;
    }

    public double pdf(double x) {
        double pdfsum = 0.0;
        for(int n = 0; n < d.length; n++) pdfsum += ker.value(x - d[n]);
        return pdfsum/d.length;
    }

    public double getMean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getVariance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * The very simple rectangular kernel
     */
    public static class RectangularKernel implements SingleVariateFunction {

        protected final double width;

        /** Constructor set the width of the rectangle, height is 1/width */
        public RectangularKernel(double width){
            this.width = width;
        }

        public double value(double x) {
            if(Math.abs(2*x) > width) return 0.0;
            else return 1/width;    
        }
    }

}
