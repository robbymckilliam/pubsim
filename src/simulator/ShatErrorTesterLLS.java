/*
 * ShatErrorTesterLLS.java
 *
 * Created on 15 April 2007, 09:30
 */

package simulator;

import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Extends the SamplingEstimator and provides a getter for shat so that it may
 * be compared with the real s. <p>
 * @author Robby McKilliam
 */
public class ShatErrorTesterLLS extends SamplingEstimator implements PRIEstimator {
    
    public ShatErrorTesterLLS(){ NUM_SAMPLES = 3; }
    
    protected double[] bestU;
    /** 
     * Return the integer vector returned from the Anstar algorithm.
     * <p>
     * Recall that u returned from nearestPoint is not unique.  A constant
     * added to each element of u returns will result in the same
     * lattice point.  It is therefore unlikely that u will be identical
     * to our real value of s.  Use the other functions:
     */
    public double[] getU(){
        return bestU;
    }
    
    protected double likelihood;
    /** Return the Maximum likehood value found */
    public double getML(){ return likelihood; } 
    
    /**
     * Use the integer vector, u, returned from nearestPoint and add an integer
     * constant, c, to all elements of u such that the |(u+c)-s| is minimized.
     * Then shat = u+c.
     */
    public double[] getShat(double[] s){
        
        if (s.length != bestU.length) throw new ArrayIndexOutOfBoundsException("bestU and s must be the same length");
        
        double sum = 0;
        for(int i = 0; i < s.length; i++){
            sum += s[i] - bestU[i];
        }
        
        double shift = (double)Math.round( sum/ bestU.length ) ;
        
        double[] shat = new double[bestU.length];
        
        for(int i = 0; i < s.length; i++){
           shat[i] = bestU[i] + shift; 
        }
        
        return shat;
    }
    
    /**
     * Return the magnitude error between s and shat.
     * This is sqrt(s-shat).
     */
    public double getsErrorMagnitude(double[] s){
        double[] shat = getShat(s);
        double error = 0.0;
        for(int i = 0; i < s.length; i++){
           error += Math.pow(shat[i] - s[i], 2); 
        }
        return Math.sqrt(error);
    }
    
    /**
     * Reimplemented setSize to also set the size of bestU array
     */
    public void setSize(int N) {
	setDimension(N-1); // => n = N-1
	zeta = new double[N];
	fzeta = new double[N];
	kappa = new double[N];
        bestU = new double[N];
    }
    
    /**
     * Reimplemented estimate frequency so that the best u gets saved for later
     * copmarison
     */
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
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
	    double L = 0;
	    for (int i = 0; i <= n; i++) {
		//double diff = zeta[i] - (v[i] / f0);
                double diff = fzeta[i] - v[i];
		L += diff * diff;
	    }
            //even and odd check
            boolean found_even = true, 
                    found_odd = true;
            for( int i = 0; i < u.length; i++){
                if ( (Math.round(Math.abs(u[i])) & 1) == 1 ) 
                    found_odd = true;
                else
                    found_even = true;
            }
	    if (L < bestL && found_even && found_odd) {
		bestL = L;
                //System.out.println(bestL);
		fhat = f0;
                bestU = u.clone();
                likelihood = -L;
	    }
	}
	return fhat;
    }
    
}
