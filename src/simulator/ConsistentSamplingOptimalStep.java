/*
 * ConsistentSamplingOptimalStep.java
 *
 * Created on 28 May 2007, 12:38
 */

package simulator;

/**
 * This is the SamplingOptimalStep estimator modified so that it is consistent
 * and will potentially have better statistical properties.
 * @author robertm
 */
public class ConsistentSamplingOptimalStep extends SamplingOptimalStep{
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
        
        double lineStep = calcOptimalStep(zeta);
        double lineLength = 0.0;
        for (int i = 0; i < y.length; i++)
            lineLength += Math.pow((fmax - fmin)*zeta[i],2);
        lineLength = Math.sqrt(lineLength);
        
        if (lineStep == 0.0)
            throw new Error("Step Length is 0.0!\n zeta = " + VectorFunctions.print(zeta));
        
        num_Steps = 100;
        //num_Steps = (int) ( lineLength / lineStep);
        
	double fstep = (fmax - fmin) / ( lineLength / lineStep );
	for (double f = fmin; f <= fmax; f += fstep) {
	    for (int i = 0; i <= n; i++)
		fzeta[i] = f * zeta[i];
	    nearestPoint(fzeta);
	    double sumv2 = 0, sumvz = 0;
	    for (int i = 0; i <= n; i++) {
		sumv2 += v[i] * v[i];
                //sumv2 += zeta[i] * zeta[i];
		sumvz += v[i] * zeta[i];
	    }
	    double f0 = sumv2 / sumvz;
            //double f0 = sumvz / sumv2;
	    double L = 0;
	    for (int i = 0; i <= n; i++) {
		double diff = zeta[i] - (v[i] / f0);
                //double diff = fzeta[i] - v[i];
		L += diff * diff * f0 * f0;   //modification for consistency
	    }
	    if (L < bestL) {
		bestL = L;
		fhat = f0;
                likelihood = -L;
                bestU = u.clone();
                //System.out.println(bestL);
	    }
	}
	return fhat;
    }
    
}
