/*
 * BinomialPeriodicityLikelihood.java
 *
 * Created on 20 April 2007, 14:49
 */

package simulator;

/**
 *
 * @author Robby McKilliam
 */
public class BinomialPeriodicityLikelihood extends ShatErrorTesterLLS implements PRIEstimator{
    
    public BinomialPeriodicityLikelihood(){ NUM_SAMPLES = 100; }
    
    /**
     * A slow Fourier Tranform.  It will work on
     * vectors of any length.  Assuming that this
     * estimator performs well, then it would be
     * necessary to write an FFT here so that the
     * estimator runs in o(nlog(n)) and not o(n^2).
     * <p>
     * Given that the input vector @param x is
     * gauranteed to only contain 0's and 1's it may
     * be possible to write and very fast fourier transform.
     */
    protected void slowFT(double[] x, double[] Xi, double[] Xr){
        
        int N = x.length;
        
        if ( Xi.length != N) Xi = new double[N];
        if ( Xi.length != N) Xr = new double[N];
        
        for (int k = 0; k < N; k++){
            for (int m = 0; m < N; m++){
                Xr[k] += x[m] * Math.cos(k*m*2*Math.PI/N);
                Xi[k] += x[m] * Math.sin(k*m*2*Math.PI/N);
            }
        }
    }
    
    /**
     * Return the magnitude squared of the fourier
     * tranform of @param x.
     */
    protected double[] abs2FT(double[] x){
        double[] Xi = new double[x.length],
                 Xr = new double[x.length];
        slowFT(x, Xi, Xr);
        double[] out = new double[x.length];
        for(int i = 0; i < x.length; i++)
            out[i] = Xi[i]*Xi[i] + Xr[i]*Xr[i];
        return out;
    }
    
    /**
     * Return the mean value of a vector
     */
    protected double mean(double[] x){
        double out = 0;
        for(int i = 0; i < x.length; i++)
            out += x[i]; 
        return out/x.length;
    }
    
    /**
     * Return the maximum value of a vector
     */
    protected double max(double[] x){
        double out = 0;
        for(int i = 0; i < x.length; i++)
            if(x[i]>out) out = x[i]; 
        return out;
    }
    
    /**
     * Return true if the vector is increasing
     */
    protected boolean increasing(double[] x){
        for(int i = 0; i < x.length-1; i++)
            if(x[i] > x[i+1]) return false;
        return true;
    }
    
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
            
            if(increasing(u)){
                
                //create the binomial series vector
                double[] deltasig = new double[(int)(u[u.length-1] - u[0] + 1)];
                double meandeltasig = u.length/deltasig.length;
                for(int i = 0; i < deltasig.length; i++)
                    deltasig[i] = -meandeltasig;
                for(int i = 0; i < u.length; i++)
                    deltasig[(int)(u[i]-u[0])] += 1.0;

               double[] absft2 = abs2FT(deltasig);
                
               double val = max(absft2)/mean(absft2);

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
