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
public class DensityEstimator extends AbstractRealRandomVariable {

    protected final double[] d;
    protected final RealRandomVariable ker;

    /**
     * Constructor takes an array of d and a kernel function represented
     * by a ContinuousRandomVariable (really just the pdf function is needed).
     */
    public DensityEstimator(final double[] data, RealRandomVariable kernel){
        d = data;
        ker = kernel;
    }

    @Override
    public double pdf(Double x) {
        double pdfsum = 0.0;
        for(int n = 0; n < d.length; n++) pdfsum += ker.pdf(x - d[n]);
        return pdfsum/d.length;
    }

    @Override
    public Double mean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Double variance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
