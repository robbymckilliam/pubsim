package simulator.pes;

// A little programme to plot the objective functions.

// Written by Vaughan Clarkson, 13-Jan-07 (fork from Comparison.java).

import java.util.Random;

import javax.vecmath.GVector;
import lattices.Anstar;
import simulator.*;

class PlotObjective {

    static public void main(String[] argv) {
	SamplingEstimator estimator = new SamplingEstimator();
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
	double sigma = 0.01;

	// Compute the estimate

	double fhat, That;
	for (int i = 0; i < N; i++)
	    y[i] = T * k[i] + sigma * random.nextGaussian();
	fhat = estimator.
	    estimateFreq(y, fmin, fmax);
	That = 1 / fhat;

	// Plot the objective

	double fstep = (fmax - fmin) / 1000;
	for (double f = fmin; f <= fmax; f += fstep)
	    System.out.println("" + f + " "
			       + estimator
			       .calculateObjective(y, f));
	System.out.println("&");
	fstep = (fmax - fmin) / 1000;
	for (double f = fmin; f <= fmax; f += fstep)
	    System.out.println("" + f + " "
			       + (ModifiedPeriodogram
				  .calculateObjective(y, f)
				  / (4 * Math.PI * Math.PI * N)));
	System.out.println("&");
	fstep = (fmax - fmin) / 100;
	for (double f = fmin; f <= fmax; f += fstep)
	    System.out.println("" + f + " "
			       + estimator
			       .calculateObjective(y, f));
	System.out.println("&");
	fstep = (fmax - fmin) / 100;
	for (double f = fmin; f <= fmax; f += fstep) {
	    estimator.calculateObjective(y, f);
	    double sumv2 = 0, sumvz = 0;
	    for (int i = 0; i < N; i++) {
	//	sumv2 += estimator.getLatticePoint()[i] * estimator.getLatticePoint()[i];
	//	sumvz += estimator.getLatticePoint()[i] * estimator.zeta[i];
	    }
	    double f0 = sumv2 / sumvz;
	    System.out.println("" + f0 + " "
			       + estimator.calculateObjective(y, f0));
	}
    }
}
