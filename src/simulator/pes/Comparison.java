package simulator.pes;

import java.util.Random;
import javax.vecmath.GVector;
import lattices.Anstar;
import simulator.*;

/**
 * Investigate the failure mode observed in the periodogram estimator for N=30.
 * @author Vaughan Clarkson, 08-Jan-07 (fork from Simulator.java)
 * Now for ModifiedPeriodogram, 11-Jan-07.
 * Back to PeriodogramEstimator, 12-Jan-07.
 */
class Comparison {

    static public void main(String[] argv) {
	PRIEstimator estimator = new PeriodogramEstimator();
	double[] k
	    = new double[] {0, 1, 4, 5, 7, 8, 9, 11, 17, 18, 20, 21, 22, 23,
			    24, 27, 29, 30, 31, 33, 34, 39, 41, 42, 49, 52,
			    55, 58, 59, 61};

	double T = 1, fmin = 0.55, fmax = 1 / 0.55;
	int N = k.length;
	estimator.setSize(k.length);
	double[] y = new double[N];
	double[] kappa = new double[N];
	Anstar.project(k, kappa);
	Random random = new Random();
	double sigma = 1e-3;

	// Find a problem example

	double fhat, That;
	int iter = 0;
	do {
	    for (int i = 0; i < N; i++)
		y[i] = T * k[i] + sigma * random.nextGaussian();
	    fhat = estimator.
		estimateFreq(y, fmin, fmax);
	    That = 1 / fhat;
	    iter++;
	} while (Math.abs(That - T) < 1e-4);
	System.err.println("iter = " + iter);

	// Examine the periodogram

	double fstep = (fmax - fmin) / 1000;
	for (double f = fmin; f <= fmax; f += fstep)
	    System.out.println("" + f + " "
			       + PeriodogramEstimator
			       .calculatePeriodogram(y, f));
	System.out.println("&");
	System.out.println("" + fhat + " "
			   + PeriodogramEstimator
			   .calculatePeriodogram(y, fhat));
    }
}
