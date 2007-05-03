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
    
    private int num_Steps = 0;
    /**
     * Return the number of steps used by the estimator
     */
    public int numSteps() { return num_Steps; }
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
        
        double lineStep = Math.sqrt((double)n/(double)(n+1) * ( ((double)(n+2))/3.0 - 1)) *0.5;
        double lineLength = 0.0;
        for (int i = 0; i < y.length; i++)
            lineLength += Math.pow((fmax - fmin)*zeta[i],2);
        lineLength = Math.sqrt(lineLength);
        
        //System.out.println(lineStep);
        
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
                bestU = u.clone();
                //System.out.println(bestL);
	    }
	}
	return fhat;
    }
    
}
