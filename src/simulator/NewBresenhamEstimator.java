/*
 * NewBresenhamEstimator.java
 *
 * Created on 11 June 2007, 23:12
 */

package simulator;

/**
 * This using a O(n) algorithm to get the the next
 * Voronoi region in Anstar.  Runs the nearsestPoint
 * O(nlog(n)) search to start with, then O(n) after.
 * It is theoretically only O(n) if you start from the
 * origin, but in practice this is slower.
 * @author Robby McKilliam
 */
public class NewBresenhamEstimator extends Anstar implements PRIEstimator{
    
    public void setSize(int N) {
        //what memory do I need?
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
        return 0.0;
    }

    public double varianceBound(double sigma, double[] k) {
        double[] kappa = new double[k.length];
	Anstar.project(k, kappa);
	double sk = 0;
	for (int i = 0; i < k.length; i++)
	    sk += kappa[i] * kappa[i];
	return sigma * sigma / sk;
    }
    
}
