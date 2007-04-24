/*
 * SamplingOptimalStep.java
 *
 * Created on 24 April 2007, 13:14
 */

package simulator;

/**
 *
 * @author Robby McKilliam
 */
public class SamplingOptimalStep extends ShatErrorTesterLLS {
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
        
        double lineLength = 0.0;
        for(int i = 0; i < zeta.length; i++) {
            lineLength += Math.pow( (fmax - fmin)*zeta[i], 2);
        }
        lineLength = Math.sqrt(lineLength);
	double fstep = 0.5 * Math.sqrt((double) n/ ((double)(n+1))) / lineLength;
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
		//double diff = zeta[i] - (v[i] / f0);
                double diff = fzeta[i] - v[i];
		L += diff * diff;
	    }
	    if (L < bestL) {
		bestL = L;
		fhat = f0;
                //System.out.println(bestL);
	    }
	}
	return fhat;
    }
    
}
