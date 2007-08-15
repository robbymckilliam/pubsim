/*
 * PeriodogramEstimator.java
 *
 * Created on 12 August 2007, 12:12
 */

package simulator.fes;

/**
 * Periodogram Estimator for frequency.
 * @author Robby
 */
public class PeriodogramEstimator implements FrequencyEstimator{
    
    int n;
    int num_samples;
    
    /**Max number of iterations for the Newton step */
    static final int MAX_ITER = 15;
    
    /**Step variable for the Newton step */
    static final double EPSILON = 1e-10;
    
    public PeriodogramEstimator(){
        num_samples = 100;
    }
    
    /** Contructor that sets the number of samples to be taken of
     * the periodogram.
     */
    public PeriodogramEstimator(int samples){
        num_samples = samples;
    }
    
    /** Set the number of samples */
    public void setSize(int n){
        this.n = n;
    }
    
    static double calculatePeriodogram(double[] real, double[] imag, double f) {
        double sumur = 0, sumui = 0;
        for (int i = 0; i < real.length; i++) {
            double cosf =  Math.cos(-2 * Math.PI * f * i);
            double sinf =  Math.sin(-2 * Math.PI * f * i);
            sumur += real[i]*cosf - imag[i]*sinf;
            sumui += imag[i]*cosf + real[i]*sinf;
        }
        return sumur * sumur + sumui * sumui;
    }
    
    /** Run the periodogram estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag){
        if (n != real.length)
            setSize(real.length);

	// Coarse search

	double maxp = 0;
	double fhat= 0.0;
        double fstep = 0.5/num_samples;
	for (double f = -0.5; f <= 0.5; f += fstep) {
	    double p = calculatePeriodogram(real, imag, f);
	    if (p > maxp) {
		maxp = p;
		fhat = f;
	    }
	}
        
        System.out.println("f = " + fhat);
        
        //Newton Raphson
        //need to fill this in
        
        return fhat;
    }
    
}
