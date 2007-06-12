/*
 * SamplingOptimalStep.java
 *
 * Created on 24 April 2007, 13:14
 */

package simulator;

import java.util.Arrays;

/**
 *
 * @author Robby McKilliam
 */
public class SamplingOptimalStep extends ShatErrorTesterLLS {
    
    protected int num_Steps = 0;
    /**
     * Return the number of steps used by the estimator
     */
    public int numSteps() { return num_Steps; }
    
    /**
     * Calculates the optimal step length for a line
     * through the origin specified by vector z.
     * This computes in o(nlog(n)) time but should take
     * only a small fraction of the time used for the
     * remainder of the search. <p>
     * Major modification added on 20/5/07.  This fixes
     * the calculation so that it actually calculated the
     * next permutahedron boundary crossing and not just
     * the relavant vector of closest angle.  The actual
     * computation is very similar for both.
     * @param z  A vector specifying the search line.
     * @return The optimal step length. <p>
     */
    protected double calcOptimalStep(double[] z){
        
        double [] dots = z.clone();
        
        Arrays.sort(dots);
        
        int m;
        double bestk = Double.POSITIVE_INFINITY, 
                k, 
                sumdots = 0.0;
        for (int i = n; i > 0; i--){
            m = n + 1 - i;
            sumdots += dots[i];
            k = (((double)(m*(n-m+1)))/(n+1))/sumdots;
            if(k < bestk){
                bestk = k;
            } else break;
        }
        
        return 0.5 * bestk * VectorFunctions.magnitude(z);
        
    }
    
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
        
        num_Steps = (int) ( lineLength / lineStep);
        
	double fstep = (fmax - fmin) / ( lineLength / lineStep );
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
	    double L = 0;
	    for (int i = 0; i <= n; i++) {
		double diff = zeta[i] - (v[i] / f0);
                //double diff = fzeta[i] - v[i];
		L += diff * diff;
	    }
	    if (L < bestL) {
		bestL = L;
		fhat = f0;
                likelihood = -L;
                //bestU = u.clone();
                //System.out.println(bestL);
	    }
	}
	return fhat;
    }
    
}
