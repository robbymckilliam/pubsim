/*
 * BinomialPeriodicityLikelihood.java
 *
 * Created on 20 April 2007, 14:49
 */

package simulator;

/**
 * Modification to remove the effect of harmonics in the
 * liklihood function.  Using the modified likelihood function
 * is likely to have this effect anyway
 * @author Robby McKilliam
 */
public class BinomialPeriodicityLikelihood extends ShatErrorTesterLLS implements PRIEstimator{
    
    public BinomialPeriodicityLikelihood(){ NUM_SAMPLES = 100; }
    
    /**
     * Reimplemented estimateFreq with the addition of the
     * probability the s comes from a non periodic source in
     * the liklihood function.
     */
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	project(y, zeta);
	double bestL = Double.NEGATIVE_INFINITY;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    for (int i = 0; i <= n; i++)
		fzeta[i] = f * zeta[i];
	    nearestPoint(fzeta);
	    double sumv2 = 0, sumvz = 0;
	    for (int i = 0; i <= n; i++) {
		sumv2 += v[i] * v[i];
		sumvz += v[i] * zeta[i];
	    }
	    double f0 = sumv2 / sumvz;
	    double dist = 0;
	    for (int i = 0; i <= n; i++) {
		double diff = zeta[i] - (v[i] / f0);
		dist += diff * diff;
	    }
            
            if(VectorFunctions.increasing(u)){
                
                //create the binomial series vector
                double[] deltasig = new double[(int)(u[u.length-1] - u[0] + 1)];
                double meandeltasig = u.length/deltasig.length;
                for(int i = 0; i < deltasig.length; i++)
                    deltasig[i] = -meandeltasig;
                for(int i = 0; i < u.length; i++)
                    deltasig[(int)(u[i]-u[0])] += 1.0;

               double[] absft2 = VectorFunctions.abs2FT(deltasig);
                
               double val = VectorFunctions.max(absft2)/VectorFunctions.mean(absft2);

                //uses a precalculated mean of 6.5 and std dev of 1.3 and
                //approximated the probability of s using Chebyshev's inequality
                double L = Math.log(1.0/Math.pow( Math.abs(val - 6.5)/1.3, 2 )) - dist;

                if (L > bestL) {
                    bestL = L;
                    //System.out.println("mean(absfft) = " + mean(absft2) + " val = " + val + " dist = " + dist + " f = " + f0 + " ps = " +  Math.log(1.0/Math.pow( Math.abs(val - 6.5)/1.3, 2 )));
                    fhat = f0;
                    likelihood = L;
                }
                
            }
	}
	return fhat;
    }

        
    
}
