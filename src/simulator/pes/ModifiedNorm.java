package simulator.pes;

import lattices.Anstar.AnstarVaughan;
import simulator.*;

/**
 * A modification of the Bresenham estimator which samples the line
 * segment rather than tracing through all the Voronoi cell boundaries.
 *<p>
 * Indeed a further modification that uses only the Euclidean distance
 * and not angle (likelihood) to score candidate estimates.
 *<p>
 * @author Vaughan Clarkson, 05-Jan-07 (forking from SamplingEstimator).
 * Modified expression for f0, 06-Jan-07.
 */
public class ModifiedNorm extends AnstarVaughan implements PRIEstimator {

    static final int NUM_SAMPLES = 100;

    double[] zeta, fzeta, kappa;

    public void setSize(int N) {
	setDimension(N-1); // => n = N-1
	zeta = new double[N];
	fzeta = new double[N];
	kappa = new double[N];
    }

    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	project(y, zeta);
	double minDist = Double.POSITIVE_INFINITY;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    for (int i = 0; i <= n; i++)
		fzeta[i] = f * zeta[i];
	    nearestPoint(fzeta);
	    double sumz2 = 0, sumvz = 0;
	    for (int i = 0; i <= n; i++) {
		sumz2 += zeta[i] * zeta[i];
		sumvz += v[i] * zeta[i];
	    }
	    double f0 = sumvz / sumz2;
	    double dist = 0;
	    for (int i = 0; i <= n; i++) {
		double diff = v[i] - (f0 * zeta[i]);
		dist += diff * diff;
	    }
	    if (dist < minDist) {
		minDist = dist;
		fhat = f0;
	    }
	}
	return fhat;
    }

    public double varianceBound(double sigma, double[] k) {
	AnstarVaughan.project(k, kappa);
	double sk = 0;
	for (int i = 0; i < k.length; i++)
	    sk += kappa[i] * kappa[i];
	return sigma * sigma / sk;
    }
}
