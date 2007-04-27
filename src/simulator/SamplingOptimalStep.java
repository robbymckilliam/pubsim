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
    
     /**
     * Return the min value of a vector
     */
    protected double min(double[] x){
        double out = 0;
        for(int i = 0; i < x.length; i++)
            if(x[i]<out) out = x[i]; 
        return out;
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
        
        for (int i = 0; i <= n; i++)
		fzeta[i] = fmax * zeta[i];
        nearestPoint(fzeta);
        double[] sdiff = u.clone();
        
        for (int i = 0; i <= n; i++)
		fzeta[i] = fmin * zeta[i];
        nearestPoint(fzeta);
        
        for (int i = 0; i <= n; i++)
            sdiff[i] = sdiff[i] - u[i];
        
        double sdiffmin = min(sdiff);
        for (int i = 0; i <= n; i++)
            sdiff[i] = sdiff[i] - sdiffmin;
        
        double num_steps = 0.0;
        for (int i = 0; i <= n; i++)
            num_steps += Math.ceil(sdiff[i]);
	
        double fstep = (fmax - fmin) / num_steps;
        
        System.out.println(num_steps);
        
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
                bestU = u.clone();
                //System.out.println(bestL);
	    }
	}
	return fhat;
    }
    
}
