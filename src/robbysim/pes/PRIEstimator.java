package robbysim.pes;


/**
 * Interface that describes the functions of a PRI estimator.
 * @author Vaughan Clarkson, 15-Jun-05.
 */
public interface PRIEstimator {
    
    /**
     *@since 16-Jun-05.
     */
    public void setSize(int n);

    public double varianceBound(double sigma, double[] s);

    /**
     * Runs the estimator and return the estimated frequency.
     */
    public double estimateFreq(double[] y, double fmin, double fmax);
}
