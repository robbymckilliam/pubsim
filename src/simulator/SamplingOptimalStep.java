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
    
    private int num_Steps = 0;
    /**
     * Return the number of steps used by the estimator
     */
    public int numSteps() { return num_Steps; }
    
    /**
     * Calculates the optimal step length for a line
     * through the origin specified by vector z.
     * This computes in o(nlog(n)) time but should take
     * only a small fraction of the time used for the
     * remainder of the search.
     * @param z  A vector specifying the search line.
     * @return The optimal step length. <p>
     */
    protected double calcOptimalStep(double[] z){
        double one_dot_z = VectorFunctions.sum(z)/n;
        double [] dots = new double[z.length];
        
        //could do some presorting as we calculate
        //the dot products to make the sort faster.
        //Keeping it simple for now.
        //int posc = 0, negc = 0;
        for (int i = 0; i <= n;  i++){
           dots[i] =  z[i] - one_dot_z;
        }
        Arrays.sort(dots);
        
        int m = 0;
        double bestcosa = Double.NEGATIVE_INFINITY, 
                cosa = 0.0, 
                sumdots = 0.0,
                blength, 
                bestblength = 0.0;
        for (int i = n; i >= 0; i--){
            m = n - i + 1;
            sumdots += dots[i];
            blength = Math.sqrt(m*n*n + (n+1-m)*m*m)/(n+1);
            cosa = sumdots/blength;
            if(cosa > bestcosa){
                bestcosa = cosa;
                bestblength = blength;
            } else break;
        }
        cosa = cosa / VectorFunctions.magnitude(z);

        return 0.5*bestblength/cosa;
        
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
        
        //System.out.println(lineStep + "\t" + lineLength);
        
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
